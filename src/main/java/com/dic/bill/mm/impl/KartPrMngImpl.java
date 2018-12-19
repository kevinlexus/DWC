package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.StatesPrDAO;
import com.dic.bill.dto.CountPers;
import com.dic.bill.mm.KartMng;
import com.dic.bill.mm.KartPrMng;
import com.dic.bill.mm.SprParamMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import com.ric.cmn.excp.WrongParam;
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
public class KartPrMngImpl implements KartPrMng {

	@Autowired
	private StatesPrDAO statesPrDao;
	@Autowired
	private SprParamMng sprParamMng;

	@PersistenceContext
	private EntityManager em;


	/**
	 * Получить кол-во проживающих по лиц.счету и услуге, на дату
	 * @param kart - лиц.счет
	 * @param lstNabor - набор услуг
	 * @param dt - дата выборки
	 * @return
	 */
	@Override
	public void getCountPersByDate(CountPers countPers, Kart kart, List<Nabor> lstNabor, Date dt) throws WrongParam {
		// параметр подсчета кол-во проживающих (0-для кис, 1-Полыс., 1 - для ТСЖ (пока, может поправить)
		Double parVarCntKpr = sprParamMng.getN1("VAR_CNT_KPR");

		for (KartPr p : kart.getKartPr()) {

			// получить статусы
			int status = 0;
			int statusTemp = 0;
			for (StatePr t : lstState) {
				if (t.getStatusPr().getTp().getCd().equals("PROP")) {
					status=t.getStatusPr().getId();
				} else if (t.getStatusPr().getTp().getCd().equals("PROP")) {
					statusTemp=t.getStatusPr().getId();
				}
			}

			// перебрать действующие услуги
			for (Nabor nabor : lstNabor) {
				Usl usl = nabor.getUsl();

				if (parVarCntKpr.equals(0D)) {
					// Киселёвск
					countPers.setCount();
				}

			}

		}
	}

//	private CountPers

}