package com.dic.bill.dao;

import java.util.Collection;
import java.util.List;

import com.dic.bill.model.scott.Anabor;
import com.dic.bill.model.scott.DebUsl;


public interface AnaborDAO {

	public List<Anabor> getAll();
	public List<Anabor> getByLsk(String lsk);
	public List<Anabor> getByLskPeriod(String lsk, Integer period);
	
}
