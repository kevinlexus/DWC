package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления по вводу
 */
@Getter
@Setter
public class UslVolVvod extends UslVol {

    // наличие счетчика
    boolean isMeter;
    // пустая квартира?
    boolean isEmpty;

}
