package com.dic.bill.mm;

import com.dic.bill.model.exs.Eolink;

public interface EolinkToEolinkMng {

	public boolean saveParentChild(Eolink parent, Eolink child, String tp);

}