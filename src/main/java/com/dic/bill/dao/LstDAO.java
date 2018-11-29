package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.bs.Lst;


public interface LstDAO {

	public Lst getByCD(String cd);

	public List<Lst> getByTp(String cdTp);

}
