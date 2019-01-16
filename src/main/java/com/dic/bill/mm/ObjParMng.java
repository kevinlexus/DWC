package com.dic.bill.mm;

import com.dic.bill.model.scott.Ko;
import com.ric.cmn.excp.WrongGetMethod;
import com.ric.cmn.excp.WrongParam;

import java.math.BigDecimal;
import java.util.Date;

public interface ObjParMng {
    BigDecimal getBd(Ko ko, String cd) throws WrongParam, WrongGetMethod;

    String getStr(Ko ko, String cd) throws WrongParam, WrongGetMethod;

    Date getDate(Ko ko, String cd) throws WrongParam, WrongGetMethod;
}
