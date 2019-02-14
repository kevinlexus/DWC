package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;

/**
 * DTO для хранения значений цены услуги
 */
public class DetailUslPrice {


    // услуга свыше соц.нормы
    public Usl uslOverSoc;
    // услуга по 0 зарег.
    public Usl uslEmpt;

    // обычная цена (по соцнорме)
    public BigDecimal price = BigDecimal.ZERO;
    // цена свыше соцнормы
    public BigDecimal priceOverSoc = BigDecimal.ZERO;
    // цена без проживающих
    public BigDecimal priceEmpt = BigDecimal.ZERO;

}
