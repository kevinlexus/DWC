package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения объемов по счетчикам
 */
public class UslMeterDateVol {

    // услуга
    public Usl usl;
    // дата
    public Date dt;
    // объем, округленный до 5 знаков после запятой
    public BigDecimal vol;

    public UslMeterDateVol(Usl usl, Date dt, BigDecimal vol) {
        this.usl = usl;
        this.dt = dt;
        this.vol = vol;
    }
}
