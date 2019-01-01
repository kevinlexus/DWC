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
	 *
	 * @param lsk - лиц.счет
	 * @param isAddPers - добавлять спроживающих?
	 * @param isAddNabor - добавлять наборы услуг?
	 * @param isAddMeter - добавлять счетчики?
	 * @return
	 */
	@Override
	public Kart buildKartForTest(String lsk, boolean isAddPers, boolean isAddNabor, boolean isAddMeter) {
		Ko ko = new Ko();
		Kart kart = new Kart();
		Org org = em.find(Org.class, 547);
		House house = em.find(House.class, 6091);
		// тип счета
		Lst tp = lstDao.getByCd("LSK_TP_MAIN");
		kart.setTp(tp);
		// приват статус
		Status status = em.find(Status.class, 2);

		kart.setKoKw(ko);
		kart.setHouse(house);
		kart.setLsk(lsk);
		kart.setPsch(0);
		kart.setOpl(BigDecimal.valueOf(63.45));
		kart.setKul("0001");
		kart.setNd("000001");
		kart.setNum("0000001");
		kart.setKpr(0);
		kart.setKprOt(0);
		kart.setKprWr(0);
		kart.setUk(org);
		kart.setMgFrom("201401");
		kart.setMgTo("201412");
		kart.setStatus(status);
		//em.persist(ko);

		if (isAddPers) {
			// проживающие
			buildKartPrForTest(kart);
		}
		if (isAddNabor) {
			// наборы услуг
			buildNaborForTest(kart);
		}
		if (isAddMeter) {
			// счетчики
			buildMeterForTest(kart);
		}

		em.persist(kart);

		return kart;
	}


	/**
	 * Построитель счетчиков по лиц.счету (квартире)
	 * @param kart - лиц.счет
	 */
	@Override
	public void buildMeterForTest(Kart kart) {
		// х.в. Счетчик 1
		Meter meter = addMeterForTest(kart.getKoKw(), "011", "05.04.2014", "06.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("10.567"), "201404");

		// х.в. Счетчик 2
		meter = addMeterForTest(kart.getKoKw(), "011", "17.04.2014", "20.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("3.11111"), "201404");

		// г.в. Счетчик 1
		meter = addMeterForTest(kart.getKoKw(), "015", "01.04.2014", "01.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("7.21"), "201404");

		// г.в. Счетчик 2
		meter = addMeterForTest(kart.getKoKw(), "015", "01.04.2014", "11.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("1.10"), "201404");
	}

	/**
     * Добавить объем для тестов
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
	 * @param koObj - присоединить к объекту
	 * @param uslId - код услуги
	 * @param dt1 - начало действия
	 * @param dt2 - окончание действия
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


	@Override
	public void buildKartPrForTest(Kart kart) {
		KartPr kartPr;
		// Антонов
		kartPr = addKartPrForTest(kart, 1,3,"Антонов", "01.01.1973",
				"02.04.2014", "30.04.2014");
		addStatePrForTest(kartPr,1, "02.04.2014", "30.04.2014");

		// Сидоров
		kartPr = addKartPrForTest(kart, 1,3, "Сидоров", "01.01.1971",
				"02.04.2014", "30.04.2014");
		addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");
		// временное отсутствие
		addStatePrForTest(kartPr, 2, "05.04.2014", "09.04.2014");

		// Тарасов
		kartPr = addKartPrForTest(kart, 1,3, "Тарасов", "01.01.1972",
				"02.04.2014", "30.04.2014");
		addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");

		// Федоров
		kartPr = addKartPrForTest(kart, 3,3, "Федоров", "01.01.1962",
				"03.04.2014", "30.04.2014");
		// убытие
		addStatePrForTest(kartPr, 4, null, null);
		// временная регистрация
		addStatePrForTest(kartPr, 3, "03.04.2014", "30.04.2014");
		//addStatePrForTest(kartPr, 1, "02.04.2014", "30.04.2014");
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
		statePr.setDtFrom(dtFrom==null? null: Utl.getDateFromStr(dtFrom));
		statePr.setDtTo(dtTo==null? null: Utl.getDateFromStr(dtTo));
		kartPr.getStatePr().add(statePr);
	}

	@Override
	public void buildNaborForTest(Kart kart) {
		addNaborForTest(kart, 1, "003", BigDecimal.valueOf(0), null,
				BigDecimal.ZERO, BigDecimal.ZERO);
		addNaborForTest(kart, 2, "011", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNaborForTest(kart, 3, "063", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNaborForTest(kart, 4, "015", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNaborForTest(kart, 5, "060", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNaborForTest(kart, 6, "013", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		// Отопление Гкал
		addNaborForTest(kart, 6, "053", BigDecimal.valueOf(1), null,
				BigDecimal.valueOf(2.70547), null);
		// Отопление Гкал 0 зарег.
		addNaborForTest(kart, 6, "054", BigDecimal.valueOf(1), null,
				null, null);

	}

	/**
	 * Добавить в набор услугу - для тестов
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
								BigDecimal vol, BigDecimal volAdd) {
		Nabor nabor = new Nabor();
		nabor.setKart(kart);
		nabor.setOrg(em.find(Org.class, orgId));
		nabor.setUsl(em.find(Usl.class, uslId));
		nabor.setKoeff(koeff);
		nabor.setNorm(norm);
		nabor.setVol(vol);
		nabor.setVolAdd(volAdd);
		kart.getNabor().add(nabor);
	}

}