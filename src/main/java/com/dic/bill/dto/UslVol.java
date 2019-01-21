package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.BigDecimalDistributable;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления
 */
public class UslVol implements BigDecimalDistributable {

    // услуга основная (например х.в.)
    public Usl usl;

    // объем
    public BigDecimal vol = BigDecimal.ZERO;

    // общая площадь
    public BigDecimal area = BigDecimal.ZERO;

    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;


    @Override
    public BigDecimal getBdForDist() {
        return this.vol;
    }

    @Override
    public void setBdForDist(BigDecimal bd) {
        this.vol = bd;
    }
}
