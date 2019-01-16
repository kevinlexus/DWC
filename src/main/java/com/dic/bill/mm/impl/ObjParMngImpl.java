package com.dic.bill.mm.impl;

import com.dic.bill.dao.ObjParDAO;
import com.dic.bill.dao.UlstDAO;
import com.dic.bill.mm.ObjParMng;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Lst;
import com.dic.bill.model.scott.ObjPar;
import com.ric.cmn.excp.WrongGetMethod;
import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class ObjParMngImpl implements ObjParMng {

    @Autowired
    private UlstDAO ulstDAO;

    @Autowired
    private ObjParDAO objParDAO;

    /**
     * Получить значение параметра типа BigDecimal объекта по CD свойства
     *
     * @param ko - Объект Ko
     * @param cd - CD параметра
     * @return
     */
    @Override
    public BigDecimal getBd(Ko ko, String cd) throws WrongParam, WrongGetMethod {
        Lst lst = ulstDAO.getByCd(cd);
        if (lst == null) {
            throw new WrongParam("ОШИБКА! Несуществующий параметр CD=" + cd);
        } else if (!lst.getValTp().equals("NM")) {
            throw new WrongGetMethod("ОШИБКА! Попытка получить значение параметра " + cd + " не являющегося типом NM!");
        }

        ObjPar objPar = objParDAO.getByKlskCd(ko.getId(), cd);
        if (objPar != null) {
            return objPar.getN1();
        } else {
            return null;
        }
    }

    /**
     * Получить значение параметра типа String объекта по CD свойства
     *
     * @param ko - Объект Ko
     * @param cd - CD параметра
     * @return
     */
    @Override
    public String getStr(Ko ko, String cd) throws WrongParam, WrongGetMethod {
        Lst lst = ulstDAO.getByCd(cd);
        if (lst == null) {
            throw new WrongParam("ОШИБКА! Несуществующий параметр CD=" + cd);
        } else if (!lst.getValTp().equals("ST")) {
            throw new WrongGetMethod("ОШИБКА! Попытка получить значение параметра " + cd + " не являющегося типом NM!");
        }

        ObjPar objPar = objParDAO.getByKlskCd(ko.getId(), cd);
        if (objPar != null) {
            return objPar.getS1();
        } else {
            return null;
        }
    }

    /**
     * Получить значение параметра типа Date объекта по CD свойства
     *
     * @param ko - Объект Ko
     * @param cd - CD параметра
     * @return
     */
    @Override
    public Date getDate(Ko ko, String cd) throws WrongParam, WrongGetMethod {
        Lst lst = ulstDAO.getByCd(cd);
        if (lst == null) {
            throw new WrongParam("ОШИБКА! Несуществующий параметр CD=" + cd);
        } else if (!lst.getValTp().equals("DT")) {
            throw new WrongGetMethod("ОШИБКА! Попытка получить значение параметра " + cd + " не являющегося типом NM!");
        }

        ObjPar objPar = objParDAO.getByKlskCd(ko.getId(), cd);
        if (objPar != null) {
            return objPar.getD1();
        } else {
            return null;
        }
    }
}