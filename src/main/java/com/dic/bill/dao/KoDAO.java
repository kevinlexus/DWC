package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.oralv.Ko;


public interface KoDAO {

	public Ko getByKlsk(Integer klsk);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);

}
