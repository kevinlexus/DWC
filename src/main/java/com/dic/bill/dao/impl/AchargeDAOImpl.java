package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AchargeDAO;
import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.Anabor;
import com.dic.bill.model.scott.ChargePay;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.SaldoUsl;


@Repository
public class AchargeDAOImpl implements AchargeDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы по lsk
     * @param - lsk - лиц.счет
     */
    public List<Acharge> getByLsk(String lsk) {
		Query query =em.createQuery("from Acharge t where t.lsk=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

    /**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
    public List<Acharge> getByLskPeriod(String lsk, Integer period) {
		Query query =em.createQuery("from Acharge t where t.lsk=:lsk and "
				+ " (t.mgFrom >=:period or :period between t.mgFrom and t.mgTo)");
		query.setParameter("period", period);
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}
    
    /**
     * Получить все элементы Kart, >= заданного лс
     * @param firstLsk - заданный лс
     */
    public List<Kart> getAfterLsk(String firstLsk) {
    	Query query =em.createQuery("select distinct t from Kart t join Acharge a "
    			+ " with a.lsk=t.lsk where t.lsk >= :lsk order by t.lsk");
		//Query query =em.createQuery("from Kart t where t.lsk = :lsk order by t.lsk");
		query.setParameter("lsk", firstLsk);
		return query.getResultList();
	}

}
