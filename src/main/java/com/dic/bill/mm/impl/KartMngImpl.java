package com.dic.bill.mm.impl;

import com.dic.bill.dao.*;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import com.ric.cmn.excp.WrongParam;
import com.ric.cmn.excp.WrongValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KartMngImpl implements KartMng {

    private final KartDAO kartDao;
    private final KartDetailDAO kartDetailDAO;
    private final OrgDAO orgDao;
    private final UlstDAO ulstDAO;
    private final HouseDAO houseDAO;

    @PersistenceContext
    private EntityManager em;

    public KartMngImpl(KartDAO kartDAO, KartDetailDAO kartDetailDAO, OrgDAO orgDao, UlstDAO ulstDAO, HouseDAO houseDAO) {
        this.kartDao = kartDAO;
        this.kartDetailDAO = kartDetailDAO;
        this.orgDao = orgDao;
        this.ulstDAO = ulstDAO;
        this.houseDAO = houseDAO;
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
     * Получить основной лиц.счет, без кэша, вернуть сущность (используется в выгрузке долгов Сбер по ЕЛС)
     *
     * @param kart - текущий лиц.счет
     * @return - основной лиц.счет
     */
    @Override
    public Kart getKartMain(Kart kart) {
        for (Kart t : kart.getKoKw().getKart()) {
            if (t.isActual()) {
                if (t.getTp().getCd().equals("LSK_TP_MAIN")) {
                    return t;
                }
            }
        }
        // не найден основной лиц.счет, вернуть текущий
        return kart;
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
            // fixme работает очень неэффективно переделать бы на сохранение по 1 помещению ред.13.10.20
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Ko buildKart(String houseGUID, BigDecimal area, int persCount, boolean isAddPers, boolean isAddNabor,
                        int statusId, int psch, int ukId, String lskTp) throws WrongParam {
        // помещение
        Ko ko = new Ko();
        Kart kart = new Kart();
        // УК
        Org uk = em.find(Org.class, ukId);
        // тип счета
        Lst tp = ulstDAO.getByCd(lskTp);
        kart.setTp(tp);
        // статус
        Status status = em.find(Status.class, statusId);

        kart.setKoKw(ko);

        Optional<House> houseOpt = houseDAO.findByGuid(houseGUID);
        if (!houseOpt.isPresent()) {
            throw new WrongParam("Не найден дом по GUID=" + houseGUID);
        } else {
            kart.setKul(houseOpt.get().getKul());
            kart.setHouse(houseOpt.get());
            kart.setNd(houseOpt.get().getNd());
        }

        // fixme вызов p_houses.kart_lsk_group_add

        // fixme kart.setLsk(получить новый lsk);

        kart.setPsch(psch);
        kart.setSchEl(0);

        kart.setOpl(area);
        kart.setNum("0000001");
        kart.setKpr(0);
        kart.setKprOt(0);
        kart.setKprWr(0);
        kart.setUk(uk);
        kart.setMgFrom("201401");
        kart.setMgTo("201412");
        kart.setStatus(status);
        ko.getKart().add(kart);

        if (isAddPers) {
            // проживающие
            // fixme buildKartPrForTest(kart, persCount);
        }
        if (isAddNabor) {
            // наборы услуг
            // fixme buildNaborForTest(kart, 0);
        }

        // счетчики
        // fixme buildMeterForTest(kart);
        // fixme house.getKart().add(kart);
        em.persist(kart); // note Используй crud.save

        // fixme
        return null;
    }

    /**
     * Создать лицевой счет
     *
     * @param lskSrc      лиц.счет для копирования
     * @param lskTp       тип
     * @param reu         код УК
     * @param kw          № помещения
     * @param houseId     Id дома
     * @param klskId      klsk фин.лиц.сч. (если null, будет создан новый)
     * @param klskPremise klsk помещения (если null, будет создано новое)
     * @param fam         фамилия
     * @param im          имя
     * @param ot          отчество владельца
     * @return № созданного лиц.счета
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Kart createKart(String lskSrc, Integer var, String lskTp, String reu, String kw,
                           Integer houseId, Long klskId, Long klskPremise,
                           String fam, String im, String ot) throws WrongParam {
        StoredProcedureQuery qr;
        qr = em.createStoredProcedureQuery("scott.p_houses.kart_lsk_add");
        qr.registerStoredProcedureParameter("p_lsk_tp", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_lsk_src", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_lsk_new", String.class, ParameterMode.INOUT);
        qr.registerStoredProcedureParameter("p_var", Integer.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_kw", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_reu", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_house", Integer.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_result", Integer.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_klsk_dst", Long.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_klsk_premise_dst", Long.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_fam", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_im", String.class, ParameterMode.IN);
        qr.registerStoredProcedureParameter("p_ot", String.class, ParameterMode.IN);
        qr.setParameter("p_lsk_tp", lskTp);
        qr.setParameter("p_lsk_src", lskSrc);
        qr.setParameter("p_var", var); // 0 - скопировать лиц.счет с существующего, 3 - создать новый лиц.сч.
        qr.setParameter("p_kw", kw);
        qr.setParameter("p_reu", reu);
        qr.setParameter("p_house", houseId);
        qr.setParameter("p_klsk_dst", klskId);
        qr.setParameter("p_klsk_premise_dst", klskPremise);
        qr.setParameter("p_fam", fam);
        qr.setParameter("p_im", im);
        qr.setParameter("p_ot", ot);
        String lsk = qr.getOutputParameterValue("p_lsk_new").toString().trim();
        //Optional<Kart> kartOpt = kartDAO.findById(lsk);
        Kart kart = em.find(Kart.class, lsk);
        if (kart != null) {
            return kart;
        } else {
            throw new WrongParam("Не найден созданный KART по лиц счету lsk=" + lsk);
        }

    }

    /**
     * Установить признак актуальности лиц.счета
     *
     * @param kart лиц.счет
     * @param psch признак (0,1-открыт, 8 - старый фонд (закрыт), 9 - закрыт по другим причинам)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStateSch(Kart kart, Date curDt, int psch) {
        kart.setPsch(0);
        ArrayList<StateSch> removeLst = new ArrayList<>();
        ArrayList<StateSch> addLst = new ArrayList<>();
        for (StateSch st : kart.getStateSch()) {
            Date firstDtMonth = Utl.getFirstDate(curDt);
            Date lastDtMonth = Utl.getLastDate(curDt);
            Date lastDtPreviosMonth = Utl.getLastDate(Utl.addMonths(curDt, -1));

            if ((Utl.between(st.getDt1(), firstDtMonth, lastDtMonth) ||
                    Utl.between(st.getDt2(), firstDtMonth, lastDtMonth))) {
                if (!st.getFkStatus().equals(psch)) {
                    // ограничить предыдущ. периодом или удалить периоды с несоответ.признаком
                    if (st.getDt1() == null) {
                        st.setDt2(lastDtPreviosMonth);
                    } else {
                        removeLst.add(st);
                        StateSch stateSch = new StateSch();
                        stateSch.setKart(kart);
                        stateSch.setFkStatus(psch);
                        stateSch.setDt1(firstDtMonth);
                        stateSch.setDt2(null);
                        addLst.add(stateSch);
                    }
                } else {
                    // установить корректные периоды действия статуса
                    if (st.getDt1() != null && !st.getDt1().equals(firstDtMonth)) {
                        st.setDt1(firstDtMonth);
                    }
                    if (st.getDt2() != null) {
                        st.setDt2(null);
                    }
                }
            }
        }
        kart.getStateSch().removeAll(removeLst);
        kart.getStateSch().addAll(addLst);
    }
}