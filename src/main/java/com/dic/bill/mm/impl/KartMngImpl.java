package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class KartMngImpl implements KartMng {

	@Autowired
	private KartDAO kartDao;

	@PersistenceContext
	private EntityManager em;

	/**
	 * Получить Ko квартиры по параметрам
	 * @param kul - код улицы
	 * @param nd - № дома
	 * @param kw - № квартиры
	 * @return
	 */
	@Override
	public Ko getKoByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId {
		List<Kart> lst = kartDao.findByKulNdKw(kul, nd, kw);
		Ko ko = null;
		for (Kart kart : lst) {
			if (kart.getKoKw().getId() == null) {
				throw new EmptyId("ОШИБКА! Обнаружен пустой KLSK_ID по лиц.счету: lsk"+kart.getLsk());
			} else if (ko == null) {
				ko = kart.getKoKw();
			} else if (!ko.equals(kart.getKoKw())) {
				throw new DifferentKlskBySingleAdress("ОШИБКА! Обнаружен разный KLSK_ID на один адрес: kul="
						+kul+", nd="+nd+", kw="+kw);
			}
		}
		return ko;
	}

	/**
	 * Получить кол-во проживающих в лиц.счете
	 * @param kart - лиц.счет
	 * @param dt - рассчитываемая дата
	 * @return
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



	/**
	 * Построитель Лиц.счета для тестов
	 * @param lsk - № лиц.счета
	 * @return
	 */
	@Override
	public Kart buildKartForTest(String lsk) {
		Ko ko = new Ko();
		Kart kart = new Kart();
		Org org = em.find(Org.class, 547);
		House house = em.find(House.class, 6091);
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

		// проживающие
		buildKartPrForTest(kart);
		// наборы услуг
		buildNaborForTest(kart);

		return kart;
	}


	@Override
	public void buildKartPrForTest(Kart kart) {
		KartPr kartPr = addKartPrForTest(kart, 1,3,"Антонов", "01.01.1973",
				"01.04.2014", "20.04.2014");
		addStatusPrForTest(kartPr,1, "01.04.2014", "30.04.2014");

		kartPr = addKartPrForTest(kart, 1,3, "Сидоров", "01.01.1971",
				"01.04.2014", "25.04.2014");
		addStatusPrForTest(kartPr, 1, "01.04.2014", "30.04.2014");
		// временное отсутствие
		addStatusPrForTest(kartPr, 2, "05.04.2014", "10.04.2014");

		kartPr = addKartPrForTest(kart, 1,3, "Тарасов", "01.01.1972",
				"01.04.2014", "30.04.2014");
		addStatusPrForTest(kartPr, 1, "01.04.2014", "30.04.2014");
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
	public void addStatusPrForTest(KartPr kartPr, int statusId,
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
		addNabor(kart, 1, "003", BigDecimal.valueOf(0), null,
				BigDecimal.ZERO, BigDecimal.ZERO);
		addNabor(kart, 2, "011", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNabor(kart, 3, "063", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNabor(kart, 4, "015", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNabor(kart, 5, "060", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		addNabor(kart, 6, "013", BigDecimal.valueOf(1), BigDecimal.valueOf(1),
		BigDecimal.ZERO, BigDecimal.ZERO);
		// Отопление Гкал
		addNabor(kart, 6, "053", BigDecimal.valueOf(1), null,
				BigDecimal.valueOf(2.70547), null);
		// Отопление Гкал 0 зарег.
		addNabor(kart, 6, "054", BigDecimal.valueOf(1), null,
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
	public void addNabor(Kart kart, int orgId, String uslId,
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