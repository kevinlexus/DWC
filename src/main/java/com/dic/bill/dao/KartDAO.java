package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.scott.Kart;


public interface KartDAO {


	public List<Kart> getAll();
    public Kart getByLsk(String lsk);
    public Kart getKwByNum(Integer klskId, String num);
	List<Kart> getListLsk(String lskFrom, String lskTo);
    List<Kart> findByKulNdKw(String s, String s1, String s2);
}
