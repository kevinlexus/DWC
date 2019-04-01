package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.UlstDAO;
import com.dic.bill.mm.TestDataBuilder;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class TestDataBuilderImpl implements TestDataBuilder {

    @Autowired
    private KartDAO kartDao;
    @Autowired
    private UlstDAO lstDao;

    @PersistenceContext
    private EntityManager em;

    /**
     * Построитель Лиц.счета для тестов
     * @param lsk - № лиц.счета
     * @return
     */
    /**
     * @param house      - дом
     * @param suffix     - суффикс лицевого счета
     * @param area       - площадь помещения
     * @param persCount  - кол-во проживающих
     * @param isAddPers  - добавлять спроживающих?
     * @param isAddNabor - добавлять наборы услуг?
     * @param statusId   - Id статуса
     * @param psch       - тип счетчика
     */
    @Override
    public Ko buildKartForTest(House house, String suffix, BigDecimal area, int persCount, boolean isAddPers, boolean isAddNabor,
                               int statusId, int psch) {

        // помещение
        Ko ko = new Ko();
        Kart kart = new Kart();
        // УК
        Org uk = em.find(Org.class, 547);
        // тип счета
        Lst tp = lstDao.getByCd("LSK_TP_MAIN");
        kart.setTp(tp);
        // муницип статус
        Status status = em.find(Status.class, statusId);
        // приват статус
        //Status status = em.find(Status.class, 2);

        kart.setKoKw(ko);
        kart.setHouse(house);
        kart.setLsk("ОСН_" + suffix);

        kart.setPsch(psch);
        kart.setSchEl(1);

        kart.setOpl(area);
        kart.setKul("0001");
        kart.setNd("000001");
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
            buildKartPrForTest(kart, persCount);
        }
        if (isAddNabor) {
            // наборы услуг
            buildNaborForTest(kart, 0);
        }

        // счетчики
        buildMeterForTest(kart);
        house.getKart().add(kart);
        em.persist(kart);

        // Лиц.счет РСО
        kart = new Kart();

        // УК
        uk = em.find(Org.class, 874);
        // тип счета
        tp = lstDao.getByCd("LSK_TP_RSO");
        kart.setTp(tp);
        // муницип статус (вообще не должен использоваться никакой!)
        //status = em.find(Status.class, 1);

        kart.setKoKw(ko);
        kart.setHouse(house);
        kart.setLsk("РСО_" + suffix);
        kart.setPsch(psch);
        kart.setOpl(area);
        kart.setKul("0001");
        kart.setNd("000001");
        kart.setNum("0000001");
        kart.setKpr(0);
        kart.setKprOt(0);
        kart.setKprWr(0);
        kart.setUk(uk);
        kart.setMgFrom("201401");
        kart.setMgTo("201412");
        kart.setStatus(status);
        ko.getKart().add(kart);

        if (isAddNabor) {
            // наборы услуг
            buildNaborForTest(kart, 1);
        }

        house.getKart().add(kart);
        em.persist(kart);

        // Лиц.счет Капремонта
        kart = new Kart();

        // УК
        uk = em.find(Org.class, 12);
        // тип счета
        tp = lstDao.getByCd("LSK_TP_ADDIT");
        kart.setTp(tp);
        // муницип статус (вообще не должен использоваться никакой!)
        //status = em.find(Status.class, 1);

        kart.setKoKw(ko);
        kart.setHouse(house);
        kart.setLsk("КАП_" + suffix);
        kart.setPsch(0);
        kart.setOpl(area);
        kart.setKul("0001");
        kart.setNd("000001");
        kart.setNum("0000001");
        kart.setKpr(0);
        kart.setKprOt(0);
        kart.setKprWr(0);
        kart.setUk(uk);
        kart.setMgFrom("201401");
        kart.setMgTo("201412");
        //kart.setStatus(status);
        ko.getKart().add(kart);

        if (isAddNabor) {
            // наборы услуг
            buildNaborForTest(kart, 2);
        }

        house.getKart().add(kart);
        em.persist(kart);
        return ko;
    }


    /**
     * Построитель вводов
     *
     * @param house
     * @param uslId
     * @param distTp
     * @param isChargeInNotHeatingPeriod
     * @param kub
     * @param isUseSch
     */
    @Override
    public void addVvodForTest(House house, String uslId, int distTp,
                               Boolean isChargeInNotHeatingPeriod, BigDecimal kub, Boolean isUseSch) {
        Usl usl = em.find(Usl.class, uslId);
        Vvod vvod = new Vvod();
        vvod.setUsl(usl);
        vvod.setHouse(house);
        // тип распределения
        vvod.setDistTp(distTp);
        // начислять в неотапливаемый период
        vvod.setIsChargeInNotHeatingPeriod(isChargeInNotHeatingPeriod);
        // объем для распределения
        vvod.setKub(kub);
        // использовать ли счетчики?
        vvod.setIsUseSch(isUseSch);
        house.getVvod().add(vvod);
    }

    /**
     * Построитель счетчиков по лиц.счету (квартире)
     *
     * @param kart - лиц.счет
     */
    @Override
    public void buildMeterForTest(Kart kart) {
        Meter meter;
        if (Utl.in(kart.getPsch(), 1, 2)) {
            // х.в. Счетчик 1
            meter = addMeterForTest(kart.getKoKw(), "011", "01.04.2014", "06.04.2014");
            // добавить объем
            //addMeterVolForTest(meter, new BigDecimal("10.567"), "201404");
            addMeterVolForTest(meter, new BigDecimal("5.00"), "201404");

            // х.в. Счетчик 2
            meter = addMeterForTest(kart.getKoKw(), "011", "17.04.2014", "20.04.2014");
            // добавить объем
            //addMeterVolForTest(meter, new BigDecimal("3.11111"), "201404");
            addMeterVolForTest(meter, new BigDecimal("8.00"), "201404");
        }

        if (Utl.in(kart.getPsch(), 1, 3)) {
            // г.в. Счетчик 1
            meter = addMeterForTest(kart.getKoKw(), "015", "01.04.2014", "11.04.2014");
            // добавить объем
            addMeterVolForTest(meter, new BigDecimal("7.21"), "201404");

            // г.в. Счетчик 2
            meter = addMeterForTest(kart.getKoKw(), "015", "05.04.2014", "11.04.2014");
            // добавить объем
            addMeterVolForTest(meter, new BigDecimal("1.10"), "201404");
        }
        if (kart.getSchEl().equals(1)) {
            // эл.эн.
            meter = addMeterForTest(kart.getKoKw(), "038", "01.04.2014", "30.04.2014");
            // добавить объем
            addMeterVolForTest(meter, new BigDecimal("350.89"), "201404");
        }
    }

    /**
     * Добавить объем для тестов
     *
     * @param meter
     * @param vol
     * @param mg
     */
    private void addMeterVolForTest(Meter meter, BigDecimal vol, String mg) {
        ObjPar objPar = new ObjPar();
        objPar.setKo(meter.getKo());
        Lst lst = lstDao.getByCd("ins_vol_sch");
        objPar.setLst(lst);
        objPar.setN1(vol);
        objPar.setMg(mg);
        meter.getObjPar().add(objPar);
    }

    /**
     * Построитель счетчика
     *
     * @param koObj - присоединить к объекту
     * @param uslId - код услуги
     * @param dt1   - начало действия
     * @param dt2   - окончание действия
     */
    @Override
    public Meter addMeterForTest(Ko koObj, String uslId, String dt1, String dt2) {
        Ko ko = new Ko();
        Meter meter = new Meter();
        meter.setDt1(Utl.getDateFromStr(dt1));
        meter.setDt2(Utl.getDateFromStr(dt2));
        meter.setKo(ko);
        meter.setKoObj(koObj);
        Usl usl = em.find(Usl.class, uslId);
        meter.setUsl(usl);
        em.persist(ko);
        em.persist(meter);
        return meter;
    }

    /**
     * Добавить записи начисления
     */
    @Override
    public void addChargeForTest(Kart kart, String uslId, String strSumma) {
        Charge charge = new Charge();
        charge.setKart(kart);

        Usl usl = em.find(Usl.class, uslId);
        BigDecimal summa = new BigDecimal(strSumma);

        charge.setUsl(usl);
        charge.setSumma(summa);

        kart.getCharge().add(charge);

    }

    @Override
    public void addChangeForTest(Kart kart, ChangeDoc changeDoc, int userId, Integer orgId, String uslId,
                                 String mgChange, String mg2, Integer type, Date dt, String strSumma) {
        Usl usl = em.find(Usl.class, uslId);
        Org org = orgId!=null? em.find(Org.class, orgId) : null;
        Tuser user = em.find(Tuser.class, userId);

        Change change = new Change();
        change.setChangeDoc(changeDoc);
        change.setKart(kart);
        change.setUsl(usl);
        change.setOrg(org);
        change.setUser(user);
        change.setDt(dt);
        change.setMgchange(mgChange);
        change.setMg2(mg2);
        change.setSumma(new BigDecimal(strSumma));

        changeDoc.getChange().add(change);
        kart.getChange().add(change);

    }

    /**
     * Добавить записи сальдо
     */
    @Override
    public void addSaldoUslForTest(Kart kart, int orgId, String uslId,
                                   String mg, String strSumma) {
        SaldoUsl saldoUsl = new SaldoUsl();

        saldoUsl.setKart(kart);
        saldoUsl.setMg(mg);

        Usl usl = em.find(Usl.class, uslId);
        Org org = em.find(Org.class, orgId);
        BigDecimal summa = new BigDecimal(strSumma);

        saldoUsl.setUsl(usl);
        saldoUsl.setOrg(org);
        saldoUsl.setSumma(summa);

        kart.getSaldoUsl().add(saldoUsl);
    }


    @Override
    public void buildKartPrForTest(Kart kart, int persCount) {
        KartPr kartPr;

/*		// Антонов (собственник)
		kartPr = addKartPrForTest(kart, 1, 11, "Антонов", "01.01.1913",
				"01.04.2014", "30.05.2014");
		addStatePrForTest(kartPr, 1, "01.04.2014", "30.05.2014");
		kartPr = addKartPrForTest(kart, 1, 11, "Антонов", "01.01.1913",
				"01.04.2014", "30.05.2014");
		addStatePrForTest(kartPr, 1, "01.04.2014", "30.05.2014");
		kartPr = addKartPrForTest(kart, 1, 11, "Антонов", "01.01.1913",
				"01.04.2014", "30.05.2014");
		addStatePrForTest(kartPr, 1, "01.04.2014", "30.05.2014");
*/

        if (persCount >= 1) {
            // Антонов (собственник)
            kartPr = addKartPrForTest(kart, 1, 11, "Антонов", "01.01.1913",
                    "02.04.2014", "30.04.2014");
            addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");
        }

        if (persCount >= 2) {
            // Сидоров
            kartPr = addKartPrForTest(kart, 1, 3, "Сидоров", "01.01.1971",
                    "03.04.2014", "30.04.2014");
            addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");
            // временное отсутствие
            addStatePrForTest(kartPr, 2, "05.04.2014", "09.04.2014");
        }

        if (persCount >= 3) {
            // Тарасов
            kartPr = addKartPrForTest(kart, 1, 3, "Тарасов", "01.01.1912",
                    "02.04.2014", "30.04.2014");
            addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");
        }

        if (persCount == 4) {
            // Федоров
            kartPr = addKartPrForTest(kart, 3, 3, "Федоров", "01.01.1972",
                    "03.04.2014", "30.04.2014");
            // убытие
            addStatePrForTest(kartPr, 4, null, null);
            // временная регистрация
            addStatePrForTest(kartPr, 3, "03.04.2014", "30.04.2014");
        }
    }


    /**
     * Построитель Проживающего для тестов
     */
    @Override
    public KartPr addKartPrForTest(Kart kart, int statusId, int relatId,
                                   String fio, String dtBirdth, String dtReg,
                                   String dtUnreg) {
        KartPr kartPr = new KartPr();
        kartPr.setKart(kart);
        kartPr.setFio(fio);
        kartPr.setDtBirdth(Utl.getDateFromStr(dtBirdth));
        kartPr.setDtReg(Utl.getDateFromStr(dtReg));
        kartPr.setDtUnReg(Utl.getDateFromStr(dtUnreg));
        StatusPr statusPr = em.find(StatusPr.class, statusId);
        kartPr.setStatusPr(statusPr);
        Relation relation = em.find(Relation.class, relatId);
        kartPr.setRelation(relation);
        kart.getKartPr().add(kartPr);
        return kartPr;
    }

    @Override
    public void addStatePrForTest(KartPr kartPr, int statusId,
                                  String dtFrom, String dtTo) {
        StatusPr statusPr = em.find(StatusPr.class, statusId);
        StatePr statePr = new StatePr();
        statePr.setKartPr(kartPr);
        statePr.setStatusPr(statusPr);
        statePr.setDtFrom(dtFrom == null ? null : Utl.getDateFromStr(dtFrom));
        statePr.setDtTo(dtTo == null ? null : Utl.getDateFromStr(dtTo));
        kartPr.getStatePr().add(statePr);
    }

    /**
     * tp 0 - основной счет, 1 - РСО, 2 - капремонт
     *
     * @param kart
     * @param tp
     */
    @Override
    public void buildNaborForTest(Kart kart, int tp) {
        if (tp == 0) {
            // тек.содерж.
            addNaborForTest(kart, 1, "003", new BigDecimal("0.9888"), null,
                    null, null, null);
            // лифт
            addNaborForTest(kart, 1, "005", new BigDecimal("0.6332"), null,
                    null, null, null);
            // дератизация
            addNaborForTest(kart, 1, "029", new BigDecimal("0.481"), null,
                    null, null, null);
            // вывоз мусора
            addNaborForTest(kart, 1, "031", new BigDecimal("0.782"), null,
                    null, null, null);

            // водоотведение
            addNaborForTest(kart, 6, "013", new BigDecimal("1"), new BigDecimal("10.25"),
                    null, null, null);
            // антенна
            addNaborForTest(kart, 1, "042", new BigDecimal("1"), null,
                    null, null, null);

            // кодовый замок
            addNaborForTest(kart, 2, "043", new BigDecimal("1"), null,
                    null, null, null);

            // Эл.энерг. ОДН, распределенный объем во вводах
            addNaborForTest(kart, 5, "058", new BigDecimal("1"), null,
                    null, new BigDecimal("2.395"), null);

            // Поверка ОДПУ
            addNaborForTest(kart, 5, "135", new BigDecimal("2.56"), new BigDecimal("1.34"),
                    null, null, null);

            // Эл.энерг.2
            addNaborForTest(kart, 1, "038", new BigDecimal("1"), null,
                    null, null, null);

            // Прочие услуги, расчитываемые как расценка * норматив * Общ.площадь, только НЕ по муницип фонду
            addNaborForTest(kart, 1, "119", new BigDecimal("1.2"), new BigDecimal("1.3"),
                    null, null, null);

            // Повыш.коэфф к услуге Х.В.
            addNaborForTest(kart, 2, "092", new BigDecimal("1"), new BigDecimal("2.5"),
                    null, null, null);

            // Вывоз мусора - кол-во прожив * норматив (Кис.)
            addNaborForTest(kart, 5, "140", new BigDecimal("1"), null,
                    null, null, null);

            // Очистка выгр.ям (Полыс.)
            addNaborForTest(kart, 5, "141", new BigDecimal("1"), null,
                    null, null, null);

            for (Vvod vvod : kart.getHouse().getVvod()) {
                if (vvod.getUsl().getId().equals("123")) {
                    // Эл.эн. ОДН (вариант с простым распределением по площади)
                    addNaborForTest(kart, 5, "123", new BigDecimal("1"), new BigDecimal("1"),
                            null, null, vvod);
                }
            }
        } else if (tp == 1) {
            // РСО лиц.счет
            addNaborForTest(kart, 3, "063", new BigDecimal("1.5"), new BigDecimal("0"),
                    null, null, null);
            // найм
            addNaborForTest(kart, 1, "026", new BigDecimal("1"), null,
                    null, null, null);

            for (Vvod vvod : kart.getHouse().getVvod()) {
                if (vvod.getUsl().getId().equals("053")) {
                    // Отопление Гкал
                    addNaborForTest(kart, 6, "053", new BigDecimal("1"), new BigDecimal("1"),
                            new BigDecimal("2.70547"), null, vvod);

                } else if (vvod.getUsl().getId().equals("011")) {
                    // Х.в.
                    addNaborForTest(kart, 2, "011", new BigDecimal("1"), new BigDecimal("5.74"),
                            null, null, vvod);
                    // х.в. св.соц.н.
                    //addNaborForTest(kart, 2, "012", new BigDecimal("1.4"), new BigDecimal("0"),
                    //		null, null, null);
                    // ОДН по х.в.
                    addNaborForTest(kart, 3, "056", new BigDecimal("1"), null,
                            null, null, null);
                } else if (vvod.getUsl().getId().equals("015")) {
                    // г.в.
                    addNaborForTest(kart, 4, "015", new BigDecimal("1"), new BigDecimal("5.01"),
                            null, null, vvod);
                    // г.в. св.соц.н.
                    //addNaborForTest(kart, 4, "016", new BigDecimal("1.4"), new BigDecimal("0"),
                    //		null, null, null);
                    // ОДН по г.в.
                    addNaborForTest(kart, 5, "057", new BigDecimal("1.5"), new BigDecimal("0"),
                            null, null, null);

                } else if (vvod.getUsl().getId().equals("099")) {
                    // Х.в. для гвс
                    addNaborForTest(kart, 2, "099", new BigDecimal("1"), new BigDecimal("5.01"),
                            null, null, vvod);
                    // Х.в. для гвс 0 зарег.
                    addNaborForTest(kart, 2, "100", new BigDecimal("1"), null,
                            null, null, null);
                    // Х.в. для гвс 0 зарег.
                    addNaborForTest(kart, 2, "101", new BigDecimal("1"), new BigDecimal("1"),
                            null, null, null);
                } else if (vvod.getUsl().getId().equals("103")) {
                    // Тепл.энерг. для нагрева х.в.
                    addNaborForTest(kart, 2, "103", new BigDecimal("1"), null,
                            null, null, vvod);
                    // Тепл.энерг. для нагрева х.в. 0 зарег.
                    addNaborForTest(kart, 2, "104", new BigDecimal("1"), null,
                            null, null, null);
                }
            }
            // Отопление Гкал 0 зарег.
            addNaborForTest(kart, 6, "054", new BigDecimal("1"), null,
                    null, null, null);

        } else if (tp == 2) {
            // капремонт
            addNaborForTest(kart, 1, "033", new BigDecimal("1"), null,
                    null, null, null);
        }


    }

    /**
     * Добавить в набор услугу - для тестов
     *
     * @param kart
     * @param orgId
     * @param uslId
     * @param koeff
     * @param norm
     * @param vol
     * @param volAdd
     */
    @Override
    public void addNaborForTest(Kart kart, int orgId, String uslId,
                                BigDecimal koeff, BigDecimal norm,
                                BigDecimal vol, BigDecimal volAdd, Vvod vvod) {
        Nabor nabor = new Nabor();
        nabor.setKart(kart);
        nabor.setOrg(em.find(Org.class, orgId));
        nabor.setUsl(em.find(Usl.class, uslId));
        nabor.setKoeff(koeff);
        nabor.setNorm(norm);
        nabor.setVol(vol);
        nabor.setVolAdd(volAdd);
        nabor.setVvod(vvod);
        kart.getNabor().add(nabor);
        if (vvod != null) {
            vvod.getNabor().add(nabor);
        }
    }

}