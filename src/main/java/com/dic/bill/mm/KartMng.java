package com.dic.bill.mm;

import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface KartMng {


	Ko getKoByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;

	boolean getPersCountByDate(Kart kart, Date dt);

	Kart getMainKart(Kart kart);
}
