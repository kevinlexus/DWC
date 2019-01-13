package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления по вводу
 */
public class UslVolVvod {

    // услуга основная (например х.в.)
    public Usl usl;
    // наличие счетчика
    public boolean isCounter;
    // пустая квартира?
    public boolean isEmpty;
    // жилое помещение?
    public boolean isResidental;

    // объем
    public BigDecimal vol = BigDecimal.ZERO;

    // общая площадь
    public BigDecimal area = BigDecimal.ZERO;

    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;

}
