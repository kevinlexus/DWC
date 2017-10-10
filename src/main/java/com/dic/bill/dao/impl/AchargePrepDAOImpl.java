package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AchargePrepDAO;
import com.dic.bill.model.scott.AchargePrep;


@Repository
public class AchargePrepDAOImpl implements AchargePrepDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы по lsk
     * @param - lsk - лиц.счет
     */
    public List<AchargePrep> getByLsk(String lsk) {
		Query query =em.createQuery("from AchargePrep t where t.lsk=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

    /**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
    public List<AchargePrep> getByLskPeriod(String lsk, Integer period) {
		Query query =em.createQuery("from AchargePrep t where t.lsk>=:lsk and "
				+ " (t.mgFrom >=:period or :period between t.mgFrom and t.mgTo)");
		query.setParameter("period", period);
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}
    

}
