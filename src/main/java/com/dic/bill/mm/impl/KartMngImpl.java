package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KartMngImpl implements KartMng {

    @Autowired
    private KartDAO kartDao;

    @PersistenceContext
    private EntityManager em;

    /**
     * Получить Ko помещения по параметрам
     *
     * @param kul - код улицы
     * @param nd  - № дома
     * @param kw  - № помещения
     * @return
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
            if (t.getUslNameShort() == null ||
                    !t.getUslNameShort().equals(generateUslNameShort(t, 0, 5, ","))) {
                t.setUslNameShort(t.getTp().getName().substring(0, 3));
            }
        });

    }

    /**
     * Сформировать список укороченных наименований услуг по фин.лиц.счету
     * @param kart     - лиц.сч.
     * @param var      - использовать: 0-по USL.NM_SHORT, 1-по USL.NM2, 2-по USL.ID (код)
     * @param maxWords - макс.кол-во наименований услуг с списке
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
     * Получить адрес по лиц.счету
     *
     * @param kart - лиц.счет
     */
    @Override
    public String getAdr(Kart kart) {
        return kart.getSpul().getName() + ", д." + kart.getNdTrimmed() +
                (kart.getNumTrimmed().length()>0?(", кв."+kart.getNumTrimmed()):"");
    }

    /**
     * Получить адрес с УК по лиц.счету
     *
     * @param kart - лиц.счет
     */
    @Override
    public String getAdrWithUk(Kart kart) {
        return kart.getUk().getName() + ", " + getAdr(kart);
    }

    /**
     * Получить ЕЛС ГИС ЖКХ фин.лиц.счета
     * @param ko
     */
/*
    @Override
    public Kart getActualKartBy(Ko ko) {
        for (Kart kart : ko.getKart()) {

            Eolink eolink = kart.getEolink();


        }
    }
*/

}