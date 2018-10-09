package com.dic.bill.dao;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.EolinkPar;


public interface EolinkParDAO {


	public EolinkPar getEolinkPar(Eolink eolink, String parCd);

}
