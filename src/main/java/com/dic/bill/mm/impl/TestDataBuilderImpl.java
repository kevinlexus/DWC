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
	 *   @param house - дом
	 * @param suffix - суффикс лицевого счета
	 * @param area - площадь квартиры
	 * @param persCount - кол-во проживающих
	 * @param isAddPers - добавлять спроживающих?
	 * @param isAddNabor - добавлять наборы услуг?
	 * @param isAddMeter - добавлять счетчики?    @return
	 * @param statusId - Id статуса
	 * */
	@Override
	public Ko buildKartForTest(House house, String suffix, BigDecimal area, int persCount, boolean isAddPers, boolean isAddNabor,
							   boolean isAddMeter, int statusId) {

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
		kart.setLsk("ОСН_"+suffix);
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
		if (isAddMeter) {
			// счетчики
			buildMeterForTest(kart);
		}

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
		kart.setLsk("РСО_"+suffix);
		kart.setPsch(0);
		//kart.setOpl(BigDecimal.valueOf(63.45));
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
		kart.setLsk("КАП_"+suffix);
		kart.setPsch(0);
		//kart.setOpl(BigDecimal.valueOf(63.45));
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
	 * @param kart - лиц.счет
	 */
	@Override
	public void buildMeterForTest(Kart kart) {
		// х.в. Счетчик 1
		Meter meter = addMeterForTest(kart.getKoKw(), "011", "01.04.2014", "06.05.2014");
		// добавить объем
		//addMeterVolForTest(meter, new BigDecimal("10.567"), "201404");
		addMeterVolForTest(meter, new BigDecimal("5.00"), "201404");

		// х.в. Счетчик 2
		meter = addMeterForTest(kart.getKoKw(), "011", "17.03.2014", "20.05.2014");
		// добавить объем
		//addMeterVolForTest(meter, new BigDecimal("3.11111"), "201404");
		addMeterVolForTest(meter, new BigDecimal("8.00"), "201404");

		// г.в. Счетчик 1
		meter = addMeterForTest(kart.getKoKw(), "015", "01.04.2014", "11.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("7.21"), "201404");

		// г.в. Счетчик 2
		meter = addMeterForTest(kart.getKoKw(), "015", "01.04.2014", "11.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("1.10"), "201404");

		// эл.эн.
		meter = addMeterForTest(kart.getKoKw(), "038", "01.04.2014", "30.04.2014");
		// добавить объем
		addMeterVolForTest(meter, new BigDecimal("350.89"), "201404");
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
	public void buildKartPrForTest(Kart kart, int persCount) {
		KartPr kartPr;
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
			kartPr = addKartPrForTest(kart, 3,3, "Федоров", "01.01.1972",
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
		statePr.setDtFrom(dtFrom==null? null: Utl.getDateFromStr(dtFrom));
		statePr.setDtTo(dtTo==null? null: Utl.getDateFromStr(dtTo));
		kartPr.getStatePr().add(statePr);
	}

	/**
	 * tp 0 - основной счет, 1 - РСО, 2 - капремонт
	 * @param kart
	 * @param tp
	 */
	@Override
	public void buildNaborForTest(Kart kart, int tp) {
		if (tp==0) {
			// основной лиц.счет
			addNaborForTest(kart, 1, "003", BigDecimal.valueOf(0.9888), null,
					null, null, null);
			// водоотведение
			addNaborForTest(kart, 6, "013", BigDecimal.valueOf(1), BigDecimal.valueOf(10.25),
					null, null, null);
			// антенна
			addNaborForTest(kart, 1, "042", BigDecimal.valueOf(1), null,
					null, null, null);

			// кодовый замок
			addNaborForTest(kart, 2, "043", BigDecimal.valueOf(1), null,
					null, null, null);

			// Эл.энерг. ОДН, распределенный объем во вводах
			addNaborForTest(kart, 5, "058", BigDecimal.valueOf(1), null,
					null, BigDecimal.valueOf(2.395), null);

			// Поверка ОДПУ
			addNaborForTest(kart, 5, "135", BigDecimal.valueOf(2.56), BigDecimal.valueOf(1.34),
					null, null, null);

			// Эл.энерг.2
			addNaborForTest(kart, 1, "038", BigDecimal.valueOf(1), null,
					null, null, null);

			// Прочие услуги, расчитываемые как расценка * норматив * Общ.площадь, только НЕ по муницип фонду
			addNaborForTest(kart, 1, "119", BigDecimal.valueOf(1.2), BigDecimal.valueOf(1.3),
					null, null, null);

			// Повыш.коэфф к услуге Х.В.
			addNaborForTest(kart, 2, "092", BigDecimal.valueOf(1), BigDecimal.valueOf(2.5),
					null, null, null);

			// Вывоз мусора - кол-во прожив * норматив (Кис.)
			addNaborForTest(kart, 5, "140", BigDecimal.valueOf(1), null,
					null, null, null);

			// Очистка выгр.ям (Полыс.)
			addNaborForTest(kart, 5, "141", BigDecimal.valueOf(1), null,
					null, null, null);

		} else if (tp==1) {
			// РСО лиц.счет
			addNaborForTest(kart, 3, "063", BigDecimal.valueOf(1.5), BigDecimal.valueOf(0),
					null, null, null);
			// г.в.
			addNaborForTest(kart, 4, "015", BigDecimal.valueOf(1), BigDecimal.valueOf(3.72),
					null, null, null);
			addNaborForTest(kart, 4, "016", BigDecimal.valueOf(1.4), BigDecimal.valueOf(0),
					null, null, null);
			addNaborForTest(kart, 5, "060", BigDecimal.valueOf(1.5), BigDecimal.valueOf(0),
					null, null, null);
			// найм
			addNaborForTest(kart, 1, "026", BigDecimal.valueOf(1), null,
					null, null, null);

			for (Vvod vvod : kart.getHouse().getVvod()) {
				if (vvod.getUsl().getId().equals("053")) {
					// Отопление Гкал
					addNaborForTest(kart, 6, "053", BigDecimal.valueOf(1), null,
							BigDecimal.valueOf(2.70547), null, vvod);

					/* проверял очистку объемов ОДН можно удалить!
					ChargePrep chargePrep = new ChargePrep();
					chargePrep.setKart(kart);
					chargePrep.setUsl(vvod.getUsl());
					chargePrep.setVol(new BigDecimal("555555.555"));
					chargePrep.setTp(4);
					kart.getChargePrep().add(chargePrep);
					*/

				} else if (vvod.getUsl().getId().equals("011")) {
					// х.в.
					addNaborForTest(kart, 2, "011", BigDecimal.valueOf(1), BigDecimal.valueOf(5.74),
							null, null, vvod);
					addNaborForTest(kart, 2, "012", BigDecimal.valueOf(1.4), BigDecimal.valueOf(0),
							null, null, null);
					// одн по х.в.
					addNaborForTest(kart, 3, "056", BigDecimal.valueOf(1), null,
							null, null, null);

					/* проверял очистку объемов ОДН можно удалить!
					Charge charge = new Charge();
					charge.setKart(kart);
					charge.setUsl(vvod.getUsl().getUslChild());
					charge.setType(5);
					charge.setSumma(new BigDecimal("7777777.77"));
					kart.getCharge().add(charge);
					*/
				}
			}
			// Отопление Гкал 0 зарег.
			addNaborForTest(kart, 6, "054", BigDecimal.valueOf(1), null,
					null, null, null);

		} else if (tp==2) {
			// капремонт
			addNaborForTest(kart, 1, "033", BigDecimal.valueOf(1), null,
					null, null, null);
		}


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
		if (vvod!=null) {
			vvod.getNabor().add(nabor);
		}
	}

}