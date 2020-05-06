package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.KartDetailDAO;
import com.dic.bill.dao.OrgDAO;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import com.ric.cmn.excp.WrongValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KartMngImpl implements KartMng {

    private final KartDAO kartDao;
    private final KartDetailDAO kartDetailDAO;
    private final OrgDAO orgDao;

    @PersistenceContext
    private EntityManager em;

    public KartMngImpl(KartDAO kartDao, KartDetailDAO kartDetailDAO, OrgDAO orgDao) {
        this.kartDao = kartDao;
        this.kartDetailDAO = kartDetailDAO;
        this.orgDao = orgDao;
    }

    /**
     * Получить Ko фин.лиц.счета по параметрам
     *
     * @param kul - код улицы
     * @param nd  - № дома
     * @param kw  - № помещения
     * @return - Ko фин.лиц.счета
     */
    @Override
    public Ko getKoPremiseByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId {
        List<Kart> lst = kartDao.findByKulNdKw(kul, nd, kw);
        Ko ko = null;
        for (Kart kart : lst) {
            if (kart.getKoPremise() == null) {
                log.error("ОШИБКА! Обнаружен пустой KLSK_PREMISE по лиц.счету: lsk" + kart.getLsk());
                throw new EmptyId("ОШИБКА! Обнаружен пустой KLSK_PREMISE по лиц.счету: lsk" + kart.getLsk());
            } else if (ko == null) {
                ko = kart.getKoPremise();
            } else if (!ko.equals(kart.getKoPremise())) {
                log.error("ОШИБКА! Обнаружен разный KLSK_PREMISE на один адрес: kul="
                        + kul + ", nd=" + nd + ", kw=" + kw);
                throw new DifferentKlskBySingleAdress("ОШИБКА! Обнаружен разный KLSK_PREMISE на один адрес: kul="
                        + kul + ", nd=" + nd + ", kw=" + kw);
            }
        }
        return ko;
    }

    /**
     * Получить все помещения по дому
     *
     * @param house - дом
     */
    @Override
    public List<Ko> getKoByHouse(House house) {
        return house.getKart().stream().map(Kart::getKoKw).distinct().collect(Collectors.toList());
    }

    /**
     * Получить помещения по вводу
     *
     * @param vvod - ввод
     */
    @Override
    public List<Ko> getKoByVvod(Vvod vvod) {
        return vvod.getNabor().stream().map(t -> t.getKart().getKoKw()).distinct().collect(Collectors.toList());
    }

    /**
     * Получить кол-во проживающих в лиц.счете
     *
     * @param kart - лиц.счет
     * @param dt   - рассчитываемая дата
     */
    @Override
    public boolean getPersCountByDate(Kart kart, Date dt) {
        log.info("*** kart.lsk={}", kart.getLsk());
        for (KartPr kartPr : kart.getKartPr()) {
            log.info("*** kartPr.id={}, kartPr.fio={}", kartPr.getId(), kartPr.getFio());
            for (StatePr statePr : kartPr.getStatePr()) {
                log.info("*** statePr.id={}, statePr.dtFrom={}, statePr.dtTo={}",
                        statePr.getId(), statePr.getDtFrom(), statePr.getDtTo());
            }
        }
        return true;
    }

    /*
     */

    /**
     * Получить основной лиц.счет (родительский)
     * обычно для счетов РСО, капремонта требуется основной лиц.счет,
     * для получения информации о кол-ве проживающих, прочих параметрах, привязанных к основному лиц.счету
     *
     * @param kart - текущий лиц.счет
     * @return - возвращает String lsk, потому что нельзя кэшировать в потоке managed entity
     */
    @Override
    @Cacheable(cacheNames = "KartMng.getKartMainLsk", key = "{#kart.getLsk()}")
    public String getKartMainLsk(Kart kart) {
        for (Kart t : kart.getKoKw().getKart()) {
            if (t.isActual()) {
                if (t.getTp().getCd().equals("LSK_TP_MAIN")) {
                    return t.getLsk();
                }
            }
        }
        // не найден основной лиц.счет, вернуть текущий
        return kart.getLsk();
    }

    /**
     * Возвращает состояние лиц.счета на указанную дату
     *
     * @param kart - лиц.счет
     * @param dt   - дата
     */
    @Override
    public Optional<StateSch> getKartStateByDate(Kart kart, Date dt) {
        for (StateSch stateSch : kart.getStateSch()) {
            if (Utl.between(dt, stateSch.getDt1(), stateSch.getDt2())) {
                return Optional.of(stateSch);
            }
        }
        return Optional.empty();
    }


    /**
     * Сохранить в Kart короткое описание Лиц.счета и услуг
     *
     * @param ko - объект Ko
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public void saveShortKartDescription(Ko ko) {
        ko.getKart().forEach(t -> {
            String uslNameShort = generateUslNameShort(t, 0, 5, ",");
            if (t.getUslNameShort() == null ||
                    !t.getUslNameShort().equals(uslNameShort)) {
                t.setUslNameShort(uslNameShort);
            }
        });

    }

    /**
     * Сформировать список укороченных наименований услуг по фин.лиц.счету
     *
     * @param kart      - лиц.сч.
     * @param var       - использовать: 0-по USL.NM_SHORT, 1-по USL.NM2, 2-по USL.ID (код)
     * @param maxWords  - макс.кол-во наименований услуг с списке
     * @param delimiter - разделитель
     */
    @Override
    public String generateUslNameShort(Kart kart, int var, int maxWords, String delimiter) {
        StringBuilder uslNameShort = new StringBuilder();

        // получить действующие, главные услуги, по которым есть короткие наименования
        List<Nabor> lst = kart.getNabor().stream()
                .filter(t -> t.isValid(false) && t.getUsl().isMain() &&
                        (var == 0 && t.getUsl().getNameShort() != null ||
                                var == 1 && t.getUsl().getNm2() != null ||
                                var == 2 && t.getUsl().getId() != null)
                )
                .sorted(Comparator.comparing(t -> t.getUsl().getNpp())).collect(Collectors.toList());

        int i = 1;

        for (Nabor nabor : lst) {
            String localDelimiter = "";
            if (i < lst.size() && i < maxWords) {
                localDelimiter = delimiter;
            }
            String fld = null;
            if (var == 0) {
                fld = nabor.getUsl().getNameShort().trim();
            } else if (var == 1) {
                fld = nabor.getUsl().getNm2().trim();
            } else if (var == 2) {
                fld = nabor.getUsl().getId();
            }
            uslNameShort.append(fld).append(localDelimiter);
            // ограничить макс кол-во слов
            if (i == maxWords) {
                break;
            }
            i++;
        }

        if (uslNameShort.length() == 0) {
            // не было установлено короткое наименование услуг
            if (kart.isActual()) {
                // установить тип лиц.счета
                if (var == 2) {
                    uslNameShort = new StringBuilder(kart.getTp().getCd());
                } else {
                    uslNameShort = new StringBuilder(kart.getTp().getName().substring(0, 3));
                }
            } else {
                // закрытый лиц.счет
                if (var == 2) {
                    uslNameShort = new StringBuilder("CLSD");
                } else {
                    uslNameShort = new StringBuilder("Долг ЖКУ");
                }
            }
        }
        return uslNameShort.toString();
    }

    /**
     * Получить адрес с названием горда по лиц.счету
     *
     * @param kart - лиц.счет
     */
    @Override
    public String getAdrWithCity(Kart kart) {
        return orgDao.getByOrgTp("Город").getName() + ", " + kart.getAdr();
    }

    /**
     * Обновить порядок следования адресов
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class)
    public void updateKartDetailOrd1() throws WrongValue {
        log.info("Начало обновления порядка следования адресов Kart");

        kartDetailDAO.updateOrd1ToNull();
        int i = 0;
        List<Kart> lstKart = kartDao.findAll().stream()
                .sorted(Comparator.comparing((Kart o1) -> o1.getSpul().getName())
                        .thenComparing(t -> t.getNdDigit().equals("") ? 0 : Integer.parseInt(t.getNdDigit()))
                        .thenComparing(Kart::getNdIndex)
                        .thenComparing(t -> t.getNumDigit().equals("") ? 0 : Integer.parseInt(t.getNumDigit()))
                        .thenComparing(Kart::getNumIndex)
                        .thenComparing(t -> !t.isActual())
                ).collect(Collectors.toList());
        for (Kart kart : lstKart) {
            i++;
            // обновление порядка адресов
            if (kart.getKartDetail() == null) {
                throw new WrongValue(
                        "ОШИБКА! Не существует записи SCOTT.KART_DETAIL для лиц.счета lsk=" +
                                kart.getLsk());
            }
            kart.getKartDetail().setOrd1(i);

            // обновление главного лиц.счета по K_LSK_ID (Актуальный, желательно LSK_TP_MAIN, далее по lsk)
            Optional<Kart> kartMain = kart.getKoKw().getKart().stream().min(Comparator.comparing((Kart o1) -> !o1.isActual())
                    .thenComparing(t -> !t.getTp().getCd().equals("LSK_TP_MAIN"))
                    .thenComparing(t -> !t.getTp().getCd().equals("LSK_TP_RSO"))
                    .thenComparing(Kart::getLsk));
            kartMain.ifPresent(t -> t.getKartDetail().setIsMain(true));

            // обновление главного лиц.счета по FK_KLSK_PREMISE (Актуальный, желательно LSK_TP_MAIN, далее по lsk)
            kartMain = kart.getKoPremise().getKartByPremise().stream().min(Comparator.comparing((Kart o1) -> !o1.isActual())
                    .thenComparing(t -> !t.getTp().getCd().equals("LSK_TP_MAIN"))
                    .thenComparing(t -> !t.getTp().getCd().equals("LSK_TP_RSO"))
                    .thenComparing(Kart::getLsk));
            kartMain.ifPresent(t -> t.getKartDetail().setIsMainInPremise(true));
        }

        log.info("Окончание обновления порядка следования адресов Kart");
    }

    /**
     * Является ли нанимателем или имеется ли в лиц.счете действующая услуга "найм"
     *
     * @param kart - лиц.счет
     */
    @Override
    public boolean getIsRenter(Kart kart) {
        return kart.getNabor().stream()
                .anyMatch(t -> t.isValid(true) && t.getUsl().isMain()
                        && t.getUsl().getCd().equals("найм"));
    }
}