package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления по лиц.счету
 */
@Getter
@Setter
public class UslVolKart extends UslVolVvod {

    // лиц.счет
    Kart kart;

}
