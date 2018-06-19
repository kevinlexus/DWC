package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.model.scott.Kart;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository("dicKartDAO") // поименовал, иначе конфликтует с существующим в com.ric.bill.dao
public class KartDAOImpl implements KartDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить все элементы Kart
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<Kart> getAll() {
		Query query =em.createQuery("from Kart t order by t.id");
		return query.getResultList();
	}

    /**
     * Получить все элементы Kart, по диапазону лс
	 * @param lskFrom - начальный лиц.счет
	 * @param lskTo - конечный лиц.счет
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<Kart> getRangeLsk(String lskFrom, String lskTo) {
    	Query query =em.createQuery("from Kart t "
    			+ "where t.id between :lskFrom and :lskTo "
    			+ "order by t.id");
		query.setParameter("lskFrom", lskFrom);
		query.setParameter("lskTo", lskTo);
		return query.getResultList();
	}

    /**
     * Получить элемент Kart, по заданному лс
     * @param firstLsk - заданный лс
     */
    @Override
    public Kart getByLsk(String lsk) {
    	Query query =em.createQuery("from Kart t where t.id = :lsk");
		query.setParameter("lsk", lsk);
		return (Kart) query.getSingleResult();
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
