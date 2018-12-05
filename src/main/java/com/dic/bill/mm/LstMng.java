package com.dic.bill.mm;

import java.util.List;

import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst;
import com.dic.bill.model.scott.Ko;

public interface LstMng {

	public Lst getByCD(String cd);
	public List<Lst> getByTp(String cdTp);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);
	public List<AddrTp> getAddrTpByTp(Integer tp);
	public AddrTp getAddrTpByCD(String cd);
}
