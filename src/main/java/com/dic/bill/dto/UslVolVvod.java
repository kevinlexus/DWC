package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления по вводу
 */
public class UslVolVvod extends UslVol {

    // наличие счетчика
    public boolean isMeter;
    // пустая квартира?
    public boolean isEmpty;
    // жилое помещение?
    public boolean isResidental;

}
