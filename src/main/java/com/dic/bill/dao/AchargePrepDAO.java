package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.AchargePrep;


public interface AchargePrepDAO {

	
	public List<AchargePrep> getByLsk(String lsk);
	public List<AchargePrep> getByLskPeriod(String lsk, Integer period);
}
