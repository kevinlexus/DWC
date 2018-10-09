package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.AchargePrep;
import com.dic.bill.model.scott.Kart;


public interface AchargePrepDAO {


	public List<AchargePrep> getByLsk(String lsk);
	public List<AchargePrep> getByLskPeriod(String lsk, Integer period);
	public List<Kart> getAfterLsk(String firstLsk);
}
