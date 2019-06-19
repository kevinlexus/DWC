package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dic.bill.model.bs.Lst2;
import org.springframework.stereotype.Repository;

import com.dic.bill.dao.LstDAO;


/**
 * DAO сущности com.dic.bill.model.bs.Lst2. Так же существует UlstDAO другой сущности com.dic.bill.model.scott.Lst2
 *
 */
@Repository
public class LstDAOImpl implements LstDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Найти элемент списка по CD
	 */
	@Override
	public Lst2 getByCD(String cd) {
		Query query =em.createQuery("from com.dic.bill.model.bs.Lst2 t where t.cd in (:cd)");
		query.setParameter("cd", cd);
		return (Lst2) query.getSingleResult();
	}

	@Override
	public List<Lst2> getByTp(String cdTp) {
		Query query =em.createQuery("select t from com.dic.bill.model.bs.Lst2 t join t.lstTp tp where tp.cd in (:cdTp) order by t.name");
		query.setParameter("cdTp", cdTp);
		return query.getResultList();
	}

}
