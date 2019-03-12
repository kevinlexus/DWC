package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления по лиц.счету
 */
public class UslVolKart extends UslVol {

    // наличие счетчика
    public boolean isMeter;
    // пустая квартира?
    public boolean isEmpty;
    // жилое помещение?
    public boolean isResidental;
    // лиц.счет
    public Kart kart;

    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;

}
