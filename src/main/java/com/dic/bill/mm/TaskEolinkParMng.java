package com.dic.bill.mm;

import java.util.Date;

import com.ric.cmn.excp.WrongGetMethod;
import com.dic.bill.model.exs.Task;

public interface TaskEolinkParMng {
	public Double getDbl(Task task, String parCd) throws WrongGetMethod;
	public String getStr(Task task, String parCd) throws WrongGetMethod;
	public Date getDate(Task task, String parCd) throws WrongGetMethod;
	public Boolean getBool(Task task, String parCd) throws WrongGetMethod;
	public void acceptPar(Task task);
}
