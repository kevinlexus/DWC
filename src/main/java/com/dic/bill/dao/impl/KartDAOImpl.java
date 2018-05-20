package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.model.scott.Kart;


@Repository("dicKartDAO") // поименовал, иначе конфликтует с существующим в com.ric.bill.dao
public class KartDAOImpl implements KartDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы Kart
     */
    @Override
    public List<Kart> getAll() {
		Query query =em.createQuery("from Kart t order by t.lsk");
		return query.getResultList();
	}

    /**
     * Получить все элементы Kart, >= заданного лс
     * @param firstLsk - заданный лс
     */
    @Override
    public List<Kart> getAfterLsk(String firstLsk) {
    	Query query =em.createQuery("from Kart t where t.lsk >= :lsk order by t.lsk");
		//Query query =em.createQuery("from Kart t where t.lsk = :lsk order by t.lsk");
		query.setParameter("lsk", firstLsk);
		return query.getResultList();
	}


	/**
	 * Найти лицевой счет по номеру и по klsk дома
	 * @param klskId - Klsk дома
	 * @param kw - номер помещения
	 * @return
	 */
    @Override
    public Kart getKwByNum(Integer klskId, String num) {
		Query query =em.createQuery("from Kart t where t.house.ko.id = :klsk and t.kw=:num");
		query.setParameter("num", num);
		try {
			return (Kart) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }

}
