package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения сгруппированных по диапазонам дат параметров для расчета начисления по лиц.счету:
 * Фактическая услуга, цена, тип объема и т.п.
 */
public class UslPriceVolKartDt extends UslPriceVolKartBase {

    // начало периода
    public Date dtFrom;
    // окончание периода
    public Date dtTo;

}
