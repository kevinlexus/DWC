package com.dic.bill.mm.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.PdocDAO;
import com.dic.bill.mm.PdocMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Pdoc;


@Service
@Slf4j
public class PdocMngImpl implements PdocMng {

	@Autowired
	private PdocDAO pdocDao;

	/**
	 * Получить список незагруженных ПД в ГИС по Дому, по всем помещениям
	 * отсортированно по номеру документа в биллинге
	 * @param houseEol - дом
	 * @param dt - дата ПД
	 * @return
	 */
	@Override
	public List<Pdoc> getPdocForLoadByHouse(Eolink houseEol, Date dt) {

		List<Pdoc> lst = pdocDao.getForLoadByHouseWithEntry(houseEol.getId(), dt);

		lst.addAll(pdocDao.getForLoadByHouseWOEntry(houseEol.getId(), dt));

		return lst;
	}

	/**
	 * Получить список незагруженных ПД в ГИС по Дому и по УК, по всем помещениям
	 * отсортированно по номеру документа в биллинге
	 * @param houseEol - дом
	 * @param uk - владеющая лиц.счетами по ПД УК (РСО)
	 * @param dt - дата ПД
	 * @return
	 */
	@Override
	public List<Pdoc> getPdocForLoadByHouse(Eolink houseEol, Eolink uk, Date dt) {

		List<Pdoc> lst = new ArrayList<>();
		lst.addAll(pdocDao.getForLoadByHouseWithEntry(houseEol.getId(), uk.getId(), dt));
		lst.addAll(pdocDao.getForLoadByHouseWOEntry(houseEol.getId(), dt));

		return lst;
	}

}