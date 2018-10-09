package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.EolinkToEolink;

public interface EolinkToEolinkDAO {

	public List<Eolink> getLinkedEolink(Eolink eolink);
    public List<Eolink> getParentEolink(Eolink eolink, String tp);
    public List<EolinkToEolink> getEolinkToEolink(Eolink parent, Eolink child, String tp);

}
