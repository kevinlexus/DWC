package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.BigDecimalDistributable;
import com.ric.cmn.Utl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения объемов для расчета начисления по лиц.счету
 */
public class UslVolKart extends UslVolVvod {

    // лиц.счет
    public Kart kart;
}
