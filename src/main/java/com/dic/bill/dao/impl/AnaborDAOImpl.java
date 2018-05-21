package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AnaborDAO;
import com.dic.bill.model.scott.Anabor;
import com.dic.bill.model.scott.Kart;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
public class AnaborDAOImpl implements AnaborDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы Anabor
     */
    @Override
	public List<Anabor> getAll() {
		Query query =em.createQuery("from Anabor t");
		return query.getResultList();
	}

    /**
     * Получить все элементы Anabor по лиц.счету
     * @param lsk - лиц. счет
     */
    @Override
	public List<Anabor> getByLsk(String lsk) {
		log.trace("1.7");
		Query query =em.createQuery("from Anabor t where t.kart.id=:lsk");
		log.trace("1.8");
		query.setParameter("lsk", lsk);
		log.trace("1.9");
		return query.getResultList();
	}

    /**
     * Получить все элементы Anabor по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
    @Override
	public List<Anabor> getByLskPeriod(String lsk, Integer period) {
		Query query =em.createQuery("from Anabor t where t.kart.id=:lsk and "
				+ " (t.mgFrom >=:period or :period between t.mgFrom and t.mgTo)");
		query.setParameter("period", period);
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

    /**
     * Получить все элементы Kart, >= заданного лс
     * @param firstLsk - заданный лс
     */
    @Override
	public List<Kart> getAfterLsk(String firstLsk) {
    	Query query =em.createQuery("select distinct t from Kart t join Anabor a "
    			+ " with a.kart.id=t.id where t.id >= :lsk order by t.id");
		//Query query =em.createQuery("from Kart t where t.lsk = :lsk order by t.lsk");
		query.setParameter("lsk", firstLsk);
		return query.getResultList();
	}

}
