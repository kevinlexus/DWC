package com.dic.bill.mm;

import java.util.Date;

import com.dic.bill.Storable;
import com.ric.cmn.excp.EmptyStorable;
import com.ric.cmn.excp.WrongSetMethod;
import com.dic.bill.model.bs.Par;

public interface ParMng {

	boolean isExByCd(int rqn, String cd);
	//public boolean checkPar(int id, String cd, String dataTp);
	public Par getByCD(int rqn, String cd);
/*
	public Boolean getBool(int rqn, Storable st, String cd) throws EmptyStorable;
	public Boolean getBool(int rqn, Storable st, String cd, Date genDt) throws EmptyStorable;
	public Double getDbl(int rqn, Storable st, String cd, Date dt1, String chng) throws EmptyStorable;
	public Double getDbl(int rqn, Storable st, String cd) throws EmptyStorable;
	public String getStr(int rqn, Storable st, String cd, Date dt1) throws EmptyStorable;
	public String getStr(int rqn, Storable st, String cd) throws EmptyStorable;
	public Date getDate(int rqn, Storable st, String cd) throws EmptyStorable;
	public void setDate(int rqn, Storable st, String cd, Date dt) throws EmptyStorable, WrongSetMethod;
*/
}