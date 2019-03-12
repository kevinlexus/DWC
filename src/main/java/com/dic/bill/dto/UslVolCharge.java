package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для записи начисления в C_CHARGE
 */
@Getter @Setter
class UslVolCharge extends UslVolKart {

    // цена
    BigDecimal price = BigDecimal.ZERO;
    // кол-во проживающих
    BigDecimal kpr = BigDecimal.ZERO;
    // кол-во временно зарегистрированных
    BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    BigDecimal kprOt = BigDecimal.ZERO;

}
