package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.bs.Lst2;


public interface LstDAO {

	public Lst2 getByCD(String cd);

	public List<Lst2> getByTp(String cdTp);

}
