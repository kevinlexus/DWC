package com.dic.bill.mm.impl;

import com.dic.bill.dao.SprParamDAO;
import com.dic.bill.dao.TaskDAO;
import com.dic.bill.mm.SprParamMng;
import com.dic.bill.mm.TaskMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Task;
import com.dic.bill.model.scott.SprParam;
import com.dic.bill.model.scott.Vvod;
import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Сервис обслуживания справочника параметров
 * @version 1.0
 */
@Service
@Slf4j
public class SprParamMngImpl implements SprParamMng {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SprParamDAO sprParamDao;

	/**
	 * Получить параметр типа Double
	 * @param cd - CD параметра
	 * @return
	 */
	@Override
	public Double getN1(String cd) throws WrongParam {
		SprParam par = sprParamDao.getByCD(cd, 0);
		if (par !=null) {
			return par.getN1();
		} else {
			throw new WrongParam("Несуществующий параметр в справочнике SPR_PARAMS: CD="+cd+" cdTp="+0);
		}
	}

	/**
	 * Получить параметр типа String
	 * @param cd - CD параметра
	 * @return
	 */
	@Override
	public String getS1(String cd) throws WrongParam {
		SprParam par = sprParamDao.getByCD(cd, 1);
		if (par !=null) {
			return par.getS1();
		} else {
			throw new WrongParam("Несуществующий параметр в справочнике SPR_PARAMS: CD="+cd+" cdTp="+1);
		}
	}

	/**
	 * Получить параметр типа Date
	 * @param cd - CD параметра
	 * @return
	 */
	@Override
	public Date getD1(String cd) throws WrongParam {
		SprParam par = sprParamDao.getByCD(cd, 2);
		if (par !=null) {
			return par.getD1();
		} else {
			throw new WrongParam("Несуществующий параметр в справочнике SPR_PARAMS: CD="+cd+" cdTp="+2);
		}
	}

	/**
	 * Получить параметр типа Boolean
	 * @param cd - CD параметра
	 * @return
	 */
	@Override
	public Boolean getBool(String cd) throws WrongParam {
		SprParam par = sprParamDao.getByCD(cd, 0);
		if (par !=null) {
			if (par.getN1()==null) {
				return null;
			} else if (par.getN1().equals(1D)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new WrongParam("Несуществующий параметр в справочнике SPR_PARAMS: CD="+cd+" cdTp="+0);
		}
	}
}