package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.DistributableBigDecimal;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для записи начисления в C_CHARGE
 */
public class UslVolCharge {

    // лиц.счет
    public Kart kart;
    // услуга детализированная (например х.в., х.в.свыше.с.н., х.в. 0 зарег.)
    public Usl usl;
    // объем, округленный до 5 знаков
    public BigDecimal vol = BigDecimal.ZERO;
    // цена
    public BigDecimal price = BigDecimal.ZERO;
    // общая площадь
    public BigDecimal area = BigDecimal.ZERO;

    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;
    // кол-во временно зарегистрированных
    public BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    public BigDecimal kprOt = BigDecimal.ZERO;

}
