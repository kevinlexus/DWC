package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.Kart;


public interface KartDAO {


	public List<Kart> getAll();
    public Kart getByLsk(String lsk);
    public Kart getKwByNum(Integer klskId, String num);
	List<Kart> getRangeLsk(String lskFrom, String lskTo);
}
