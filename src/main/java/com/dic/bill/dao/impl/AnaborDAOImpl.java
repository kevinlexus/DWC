package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AnaborDAO;
import com.dic.bill.model.scott.Anabor;


@Repository
public class AnaborDAOImpl implements AnaborDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы Anabor
     */
    public List<Anabor> getAll() {
		Query query =em.createQuery("from Anabor t");
		return query.getResultList();
	}

    /**
     * Получить все элементы Anabor по лиц.счету
     * @param lsk - лиц. счет
     */
    public List<Anabor> getByLsk(String lsk) {
		Query query =em.createQuery("from Anabor t where t.lsk=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

    /**
     * Получить все элементы Anabor по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
    public List<Anabor> getByLskPeriod(String lsk, Integer period) {
		Query query =em.createQuery("from Anabor t where t.lsk>=:lsk and "
				+ " (t.mgFrom >=:period or :period between t.mgFrom and t.mgTo)");
		query.setParameter("period", period);
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}
}
