package com.dic.bill.mm.impl;

import com.dic.bill.dao.AddrTpDAO;
import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.KoDAO;
import com.dic.bill.dao.LstDAO;
import com.dic.bill.mm.KartMng;
import com.dic.bill.mm.LstMng;
import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Kart;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KartMngImpl implements KartMng {

	@Autowired
	private KartDAO kartDao;


	/**
	 * Получить Ko квартиры по параметрам
	 * @param kul - код улицы
	 * @param nd - № дома
	 * @param kw - № квартиры
	 * @return
	 */
	@Override
	public Ko getKlskByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId {
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
}