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
import com.dic.bill.model.scott.ChargePay;
import com.dic.bill.model.scott.SaldoUsl;


@Repository
public class AchargeDAOImpl implements AchargeDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы SaldoUsl по lsk
     * @param - lsk - лиц.счет
     */
    public List<Acharge> getByLsk(String lsk) {
		Query query =em.createQuery("from Acharge t where t.lsk=:lsk");
		query.setParameter("lsk", lsk);
		return query.getResultList();
	}


}
