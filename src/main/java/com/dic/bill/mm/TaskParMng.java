package com.dic.bill.mm;

import java.util.Date;

import com.ric.cmn.excp.WrongGetMethod;
import com.dic.bill.model.exs.Task;

public interface TaskParMng {

	public Boolean getBool(Task task, String parCd) throws WrongGetMethod;
	public void setBool(Task task, String parCd, Boolean val) throws WrongGetMethod;
	public Double getDbl(Task task, String parCd) throws WrongGetMethod;
	public Double setDbl(Task task, String parCd, Double val) throws WrongGetMethod;
	public String getStr(Task task, String parCd) throws WrongGetMethod;
	public Double setStr(Task task, String parCd, String val) throws WrongGetMethod;
	public Date getDate(Task task, String parCd) throws WrongGetMethod;
	public Double setDate(Task task, String parCd, Date val) throws WrongGetMethod;

}