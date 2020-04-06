package com.dic.bill.mm.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.EolinkParDAO;
import com.dic.bill.dao.TaskParDAO;
import com.ric.cmn.excp.WrongGetMethod;
import com.dic.bill.mm.ParMng;
import com.dic.bill.mm.TaskEolinkParMng;
import com.dic.bill.model.bs.Par;
import com.dic.bill.model.exs.EolinkPar;
import com.dic.bill.model.exs.Task;
import com.dic.bill.model.exs.TaskPar;

import lombok.extern.slf4j.Slf4j;


/**
 * Сервис совмещенного получения параметра. Поиска сперва в eolinkxpar, потом в taskxpar
 * @author lev
 *
 */
@Service
@Slf4j
public class TaskEolinkParMngImpl implements TaskEolinkParMng {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private TaskParDAO taskParDao;
	@Autowired
	private EolinkParDAO eolinkParDao;

	/**
	 * получить значение параметра типа Double задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	@Override
	public Double getDbl(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getN1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getN1();
			}

		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}


	/**
	 * получить значение параметра типа String задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	@Override
	public String getStr(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getS1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getS1();
			}

		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа Date задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	@Override
	public Date getDate(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getD1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getD1();
			}

		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа Boolean задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod
	 */
	@Override
	public Boolean getBool(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("BL")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null && epar.getN1()!=null) {
					if (epar.getN1() == 1D) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				// найдено в taskParDao
				if (tpar.getN1() == 1D) {
					return true;
				} else {
					return false;
				}
			}

		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}


	/**
	 * Переписать значения параметров из Task в Eolink, по завершению отправки в ГИС
	 */
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void acceptPar(Task task) {
		log.trace("Перемещение параметров по task.id={}, task.getTaskPar().size()={}", task.getId(), task.getTaskPar().size());
		//task.getTaskPar().stream().forEach(d-> {
		//});

		task.getTaskPar().stream().forEach(t-> {
			EolinkPar ep = task.getEolink().getEolinkPar().stream().filter(e-> e.getPar().equals(t.getPar())).findAny().orElse(null);
			/*if (ep!=null) {
				log.info("id={} tp={}", t.getId(), ep.getPar().getTp());
			}*/

			if (ep==null) {
				//Параметра нет, создать
				ep = new EolinkPar(task.getEolink(), t.getPar(), t.getN1(), t.getS1(), t.getD1());
				em.persist(ep); // note Используй crud.save
			} else {
				//Параметр есть, и изменился, обновить
				if (ep.getPar().getTp().equals("NM")) {
					Double eVal = ep.getN1();
					Double tVal = t.getN1();

					if (eVal!=null && tVal!=null && !eVal.equals(tVal) ||
							eVal!=null && tVal==null || eVal==null && tVal!=null) {
						ep.setN1(t.getN1());
					}
				} else if (ep.getPar().getTp().equals("BL")) {
					Double eVal = ep.getN1();
					Double tVal = t.getN1();

					if (eVal!=null && tVal!=null && !eVal.equals(tVal) ||
							eVal!=null && tVal==null || eVal==null && tVal!=null) {
						ep.setN1(t.getN1());
					}
				} else if (ep.getPar().getTp().equals("ST")) {
					String eVal = ep.getS1();
					String tVal = t.getS1();

					if (eVal!=null && tVal!=null && !eVal.equals(tVal) ||
							eVal!=null && tVal==null || eVal==null && tVal!=null) {
						ep.setS1(t.getS1());
					}
				} else if (ep.getPar().getTp().equals("DT")) {
					Date eVal = ep.getD1();
					Date tVal = t.getD1();

					if (eVal!=null && tVal!=null && !eVal.equals(tVal) ||
							eVal!=null && tVal==null || eVal==null && tVal!=null) {
						ep.setD1(t.getD1());
					}
				}
			}

		});

	}

}