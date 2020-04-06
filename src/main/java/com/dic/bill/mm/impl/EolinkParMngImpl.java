package com.dic.bill.mm.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dic.bill.dao.EolinkParDAO;
import com.ric.cmn.excp.WrongGetMethod;
import com.dic.bill.mm.EolinkParMng;
import com.dic.bill.mm.ParMng;
import com.dic.bill.model.bs.Par;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.EolinkPar;


@Service
@Slf4j
public class EolinkParMngImpl implements EolinkParMng {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    @Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private EolinkParDAO eolinkParDao;



	/**
	 * получить значение параметра типа Boolean связанного объекта по CD свойства
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	public Boolean getBool(Eolink eolink, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("BL") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				if (ap.getN1() != null) {
					if (ap.getN1() == 1) {
						return true;
					} else {
						return false;
					}
				} else {
					return null;
				}
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа Boolean связанного объекта по CD свойства
	 * в случае отсутствия - создать
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @param val - значение
	 * @throws WrongGetMethod
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setBool(Eolink eolink, String parCd, Boolean val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("BL") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			Double val1 = null;
			if (val != null) {
				if (val) {
					val1 = 1D;
				} else {
					val1 = 0D;
				}
			}

			if (ap!= null) {
				// сохранить значение
				ap.setN1(val1);
			} else {
				// создать значение
				ap = new EolinkPar(eolink, par, val1, null, null);
				em.persist(ap); // note Используй crud.save
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
	}

	/**
	 * получить значение параметра типа Double связанного объекта по CD свойства
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	public Double getDbl(Eolink eolink, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				return ap.getN1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа Double связанного объекта по CD свойства
	 * в случае отсутствия - создать
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @param val - значение
	 * @throws WrongGetMethod
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setDbl(Eolink eolink, String parCd, Double val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setN1(val);
			} else {
				// создать значение
				ap = new EolinkPar(eolink, par, val, null, null);
				em.persist(ap); // note Используй crud.save
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
	}


	/**
	 * получить значение параметра типа String связанного объекта по CD свойства
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	public String getStr(Eolink eolink, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				return ap.getS1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа String связанного объекта по CD свойства
	 * в случае отсутствия - создать
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @param val - значение
	 * @throws WrongGetMethod
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setStr(Eolink eolink, String parCd, String val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setS1(val);
			} else {
				// создать значение
				ap = new EolinkPar(eolink, par, null, val, null);
				em.persist(ap); // note Используй crud.save
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
	}

	/**
	 * получить значение параметра типа Date связанного объекта по CD свойства
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	public Date getDate(Eolink eolink, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				return ap.getD1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа Date связанного объекта по CD свойства
	 * в случае отсутствия - создать
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @param val - значение
	 * @throws WrongGetMethod
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setDate(Eolink eolink, String parCd, Date val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT") && par.getDataTp().equals("SI")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolink, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setD1(val);
			} else {
				// создать значение
				ap = new EolinkPar(eolink, par, null, null, val);
				em.persist(ap); // note Используй crud.save
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
	}

}