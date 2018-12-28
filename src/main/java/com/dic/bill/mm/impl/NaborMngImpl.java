package com.dic.bill.mm.impl;

import com.dic.bill.dao.AddrTpDAO;
import com.dic.bill.dao.KoDAO;
import com.dic.bill.dao.LstDAO;
import com.dic.bill.dto.PriceRec;
import com.dic.bill.mm.LstMng;
import com.dic.bill.mm.NaborMng;
import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Nabor;
import com.dic.bill.model.scott.Usl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NaborMngImpl implements NaborMng {


	/**
	 * Получить действующий набор услуг по данной дате
	 * @param kart - лиц.счет
	 * @param curDt - текущая дата (на будущее, для вкл./выкл. услуги в течении месяца)
	 */
	@Override
	public List<Nabor> getValidNabor(Kart kart, Date curDt) {
		List<Nabor> lstNabor = new ArrayList<>(10);
		for (Nabor t : kart.getNabor()) {
			if (t.isValid()) {
				// добавить действующую услугу
				lstNabor.add(t);
			}
		}
		return lstNabor;
	}

	/**
	 * Получить расценки по услуге
	 * @param lst - список услуг в лиц.счете
	 * @param usl - выбранная главная услуга
	 * @return
	 */
	@Override
	public PriceRec getPrices(List<Nabor> lst, Usl usl) {


	}


}