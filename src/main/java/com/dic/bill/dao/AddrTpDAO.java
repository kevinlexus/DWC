package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.bs.AddrTp;


public interface AddrTpDAO {

	public List<AddrTp> getByTp(Integer tp);
	public AddrTp getByCd(String cd);

}
