package com.dic.bill.mm;

import com.dic.bill.model.scott.Ko;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;

public interface KartMng {


	Ko getKoByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;
}
