package com.dic.bill.mm;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Task;
import com.ric.cmn.excp.WrongParam;

import java.util.Date;

public interface SprParamMng {

	Double getN1(String cd) throws WrongParam;

	String getS1(String cd) throws WrongParam;

	Date getD1(String cd) throws WrongParam;

	Boolean getBool(String cd) throws WrongParam;
}