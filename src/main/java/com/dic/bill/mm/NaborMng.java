package com.dic.bill.mm;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Nabor;

import java.util.Date;
import java.util.List;

public interface NaborMng {

    List<Nabor> getValidNabor(Kart kart, Date curDt);

    DetailUslPrice getDetailUslPrice(List<Nabor> lst, Nabor nabor);
}
