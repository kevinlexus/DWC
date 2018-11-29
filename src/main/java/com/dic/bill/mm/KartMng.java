package com.dic.bill.mm;

import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst;
import com.dic.bill.model.scott.Ko;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;

import java.util.List;

public interface KartMng {


	Ko getKlskByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;
}
