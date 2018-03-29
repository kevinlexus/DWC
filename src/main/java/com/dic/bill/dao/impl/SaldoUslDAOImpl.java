package com.dic.bill.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.model.scott.ChargePay;
import com.dic.bill.model.scott.SaldoUsl;
import com.ric.bill.model.exs.Eolink;


/**
 * DAO сущности SaldoUsl
 * @author Leo
 *
 */
@Repository
public class SaldoUslDAOImpl implements SaldoUslDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы SaldoUsl по lsk
     * @param - lsk - лиц.счет
     */
    @Override
    public List<SaldoUsl> getByLsk(String lsk) {
		Query query =em.createQuery("from SaldoUsl t where t.lsk=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}

	/**
	 * Получить совокупное сальдо по лицевому счету
	 * @param lsk лицевой счет
	 * @param period - период 
	 * @return
	 */
    @Override
    public BigDecimal getAmntByLsk(String lsk, String period) {
		Query query =em.createQuery("select sum(t.summa) from SaldoUsl t "
				+ "where t.lsk=:lsk and t.mg=:period");
		query.setParameter("lsk", lsk);
		query.setParameter("period", period);
		try {
			return (BigDecimal) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

    
}
