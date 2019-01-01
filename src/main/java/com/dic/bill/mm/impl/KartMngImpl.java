package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.UlstDAO;
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
	@Autowired
	private UlstDAO lstDao;

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
	 * Получить основной лиц.счет (родительский)
	 * обычно для счетов РСО, капремонта требуется основной лиц.счет,
	 * для получения информации о кол-ве проживающих, прочих параметрах, приявязанных к основному лиц.счету
	 * @param kart - текущий лиц.счет
	 * @return
	 */
	@Override
	public Kart getMainKart(Kart kart) {
		if (kart.getParentKart() != null) {
			// родительский указан явно
			return kart.getParentKart();
		} else {
			for (Kart t : kart.getKoKw().getKart()) {
				if (t.isActual() && t.getTp().getCd().equals("LSK_TP_MAIN")) {
					return t;
				}
			}
		}
		// не найден основной лиц.счет
		return null;
	}


}