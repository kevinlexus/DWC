package com.dic.bill.mm;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Nabor;
import com.ric.cmn.excp.ErrorWhileChrg;

import java.util.Date;
import java.util.List;

public interface NaborMng {

    List<Nabor> getValidNabor(Ko ko, Date curDt);

    DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg;
}
