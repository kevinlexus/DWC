package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.Kart;


public interface KartDAO {

	
	public List<Kart> getAll();
	public List<Kart> getAfterLsk(String firstLsk);
	
}
