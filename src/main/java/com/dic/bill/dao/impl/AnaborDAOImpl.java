package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AnaborDAO;
import com.dic.bill.model.scott.Anabor;
import com.dic.bill.model.scott.DebUsl;


@Repository
public class AnaborDAOImpl implements AnaborDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы DebUsl
     */
    public List<Anabor> getAll() {
		Query query =em.createQuery("from Anabor t");
		return query.getResultList();
	}


}
