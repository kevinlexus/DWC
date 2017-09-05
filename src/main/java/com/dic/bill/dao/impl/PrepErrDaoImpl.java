package com.dic.bill.dao.impl;

import java.util.List;
import org.hibernate.Session;

import com.dic.bill.model.scott.PrepErr;
import com.dic.bill.utils.DSess;

/**
 * DAO класс по старой схеме
 * @author lev
 *
 */
public class PrepErrDaoImpl {

  private Session sess;
  private boolean selfSess; //собственная ли сессия
	
  //конструктор с переданной сессией
  public PrepErrDaoImpl(Session sess) {
    this.sess=sess;
    selfSess=false;
    }

  //конструктор с собственной сессией
  public PrepErrDaoImpl() {
	this.sess =new DSess(true).sess;
    selfSess=true;
	}

public List<PrepErr> findAll() {

	@SuppressWarnings("unchecked")
	List<PrepErr> result = (List<PrepErr>) sess.createQuery("select t from PrepErr t order by t.id").list();
	if (selfSess) {this.sess.close();}; //закрыть сессию, если надо

	return result;
}


}
