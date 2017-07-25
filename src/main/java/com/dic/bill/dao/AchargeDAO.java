package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.SaldoUsl;


public interface AchargeDAO {

	
	public List<Acharge> getByLsk(String lsk);
	
}
