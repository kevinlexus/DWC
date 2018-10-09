package com.dic.bill.mm;

import java.util.Date;
import java.util.List;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Pdoc;

public interface PdocMng {

	public List<Pdoc> getPdocForLoadByHouse(Eolink houseEol, Date dt);

}