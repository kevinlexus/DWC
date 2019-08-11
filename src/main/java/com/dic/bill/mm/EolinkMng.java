package com.dic.bill.mm;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Kart;

import java.util.List;

public interface EolinkMng {

	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
			String kw, String entry, String tp);
	public void setChildActive(Eolink eolink, String tp, Integer status);

	List<Eolink> getLskEolByHouseEol(Integer eolHouseId, Integer eolUkId);
	List<Kart> getKartNotExistsInEolink(Integer eolHouseId, Integer eolUkId);
}