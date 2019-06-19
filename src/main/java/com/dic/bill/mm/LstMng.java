package com.dic.bill.mm;

import java.util.List;

import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst2;
import com.dic.bill.model.scott.Ko;

public interface LstMng {

	public Lst2 getByCD(String cd);
	public List<Lst2> getByTp(String cdTp);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);
	public List<AddrTp> getAddrTpByTp(Integer tp);
	public AddrTp getAddrTpByCD(String cd);
}
