package com.dic.bill.dto;

import java.math.BigDecimal;

/**
 * DTO для хранения значений цены услуги
 */
public class PriceRec {

    // обычная цена (по соцнорме)
    public BigDecimal price;

    // цена свыше соцнормы
    public BigDecimal priceOverNorm;

    // цена без проживающих
    public BigDecimal priceWoReg;


}
