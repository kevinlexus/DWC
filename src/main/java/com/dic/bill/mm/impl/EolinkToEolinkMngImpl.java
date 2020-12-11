package com.dic.bill.mm.impl;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dic.bill.dao.EolinkToEolinkDAO;
import com.dic.bill.mm.EolinkToEolinkMng;
import com.dic.bill.mm.LstMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.EolinkToEolink;


@Service
@Slf4j
public class EolinkToEolinkMngImpl implements EolinkToEolinkMng {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
	@Autowired
	private EolinkToEolinkDAO eolToEolDao;
	@Autowired
	private LstMng lstMng;

	/**
	 * Сохранить отношение родительской записи к дочерней, по типу
	 * @author lev
	 * @param parent - родительская сущность
	 * @param child  - дочерняя сущность
	 * @param tp     - тип
	 */
	@Override
	@Transactional
	public boolean saveParentChild(Eolink parent, Eolink child, String tp) {
		if (eolToEolDao.getEolinkToEolink(parent, child, tp) != null) {
			return false;
		} else {
			EolinkToEolink eolToEol = new EolinkToEolink(parent, child, lstMng.getByCD(tp));
			em.persist(eolToEol);
			return true;
		}

	}



}