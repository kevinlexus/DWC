package com.dic.bill.mm;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.ErrorWhileChrg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface NaborMng {

    List<Nabor> getValidNabor(Ko ko, Date curDt);

    Integer getCached(String str1, Integer int2, Date dt3);

    Vvod getVvod(List<Nabor> lstNabor, Usl usl);

    Integer getVvodDistTp(List<Nabor> lstNabor, Usl usl);

    DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg;

    Nabor createNabor(Kart kart, Usl usl, Org org,
                      BigDecimal koeff, BigDecimal norm,
                      BigDecimal vol, BigDecimal volAdd, Vvod vvod);
}
