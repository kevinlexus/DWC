package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.LstDAO;
import com.dic.bill.model.bs.Lst;


@Repository
public class LstDAOImpl implements LstDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Найти элемент списка по CD
	 */
	//@Cacheable("LstDAOImpl.getByCD")
	@Override
	public synchronized Lst getByCD(String cd) {
		Query query =em.createQuery("from com.dic.bill.model.bs.Lst t where t.cd in (:cd)");
		query.setParameter("cd", cd);
		return (Lst) query.getSingleResult();
	}

	@Override
	public List<Lst> getByTp(String cdTp) {
		Query query =em.createQuery("select t from com.dic.bill.model.bs.Lst t join t.lstTp tp where tp.cd in (:cdTp) order by t.name");
		query.setParameter("cdTp", cdTp);
		return query.getResultList();
	}

}
