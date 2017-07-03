package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.model.scott.ChargePay;


@Repository
public class ChargePayDAOImpl implements ChargePayDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы ChargePay, отсортированные по lsk
     */
    public List<ChargePay> getAllOrd() {
		Query query =em.createQuery("from ChargePay t order by t.lsk");
		return query.getResultList();
	}


}
