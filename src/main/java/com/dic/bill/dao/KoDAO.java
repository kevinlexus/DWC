package com.dic.bill.dao;

import com.dic.bill.model.scott.Ko;

import java.util.List;



public interface KoDAO {

	public Ko getByKlsk(Integer klsk);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);

}
