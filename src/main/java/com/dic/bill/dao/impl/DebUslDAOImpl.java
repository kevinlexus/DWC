package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.DebUslDAO;
import com.dic.bill.model.scott.DebUsl;


@Repository
public class DebUslDAOImpl implements DebUslDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы DebUsl
     */
    public List<DebUsl> getAll() {
		Query query =em.createQuery("from DebUsl t");
		return query.getResultList();
	}


}
