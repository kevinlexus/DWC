package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AchargePrepDAO;
import com.dic.bill.model.scott.AchargePrep;
import com.dic.bill.model.scott.Kart;


@Repository
public class AchargePrepDAOImpl implements AchargePrepDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы по lsk
     * @param - lsk - лиц.счет
     */
    @Override
	public List<AchargePrep> getByLsk(String lsk) {
		Query query =em.createQuery("from AchargePrep t where t.kart.id=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

    /**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
    @Override
	public List<AchargePrep> getByLskPeriod(String lsk, Integer period) {
		Query query =em.createQuery("from AchargePrep t where t.kart.id=:lsk and "
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
    	Query query =em.createQuery("select distinct t from AchargePrep a join a.kart t "
    			+ " where t.id >= :lsk order by t.id");
		//Query query =em.createQuery("from Kart t where t.lsk = :lsk order by t.lsk");
		query.setParameter("lsk", firstLsk);
		return query.getResultList();
	}

}
