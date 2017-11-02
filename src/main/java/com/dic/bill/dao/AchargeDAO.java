package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.SaldoUsl;


public interface AchargeDAO {

	
	public List<Acharge> getByLsk(String lsk);
	public List<Acharge> getByLskPeriod(String lsk, Integer period);
	public List<Kart> getAfterLsk(String firstLsk);

}
