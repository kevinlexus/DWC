package com.dic.bill.mm;

import com.dic.bill.model.scott.Nabor;
import com.ric.cmn.excp.ErrorWhileChrg;

import java.math.BigDecimal;

public interface PriceMng {

    BigDecimal multiplyPrice(Nabor nabor, int priceColumn) throws ErrorWhileChrg;

}