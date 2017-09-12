package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.AchargeDAO;
import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.ChargePay;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.SaldoUsl;


@Repository
public class KartDAOImpl implements KartDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы Kart
     */
    public List<Kart> getAll() {
		Query query =em.createQuery("from Kart t order by t.lsk");
		return query.getResultList();
	}

}
