package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.SaldoUsl;


public interface SaldoUslDAO {

	
	public List<SaldoUsl> getByLsk(String lsk);
	
}
