package com.dic.bill.mm;

import com.dic.bill.model.exs.Eolink;

public interface EolinkMng {

	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
			String kw, String entry, String tp);
	public void setChildActive(Eolink eolink, String tp, Integer status);

}