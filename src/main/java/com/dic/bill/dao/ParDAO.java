package com.dic.bill.dao;

import com.dic.bill.model.bs.Par;


public interface ParDAO {


	public Par getByCd(int rqn, String cd);

	public boolean checkPar(int rqn, int id, String cd, String dataTp);
}
