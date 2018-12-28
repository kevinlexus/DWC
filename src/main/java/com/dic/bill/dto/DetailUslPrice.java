package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;

/**
 * DTO для хранения значений цены услуги
 */
public class DetailUslPrice {

    // обычная цена (по соцнорме)
    public BigDecimal price = BigDecimal.ZERO;

    // услуга свыше соцнормы
    public Usl uslOverNorm = null;
    // цена свыше соцнормы
    public BigDecimal priceOverNorm = BigDecimal.ZERO;

    // услуга без проживающих
    public Usl uslEmpt = null;
    // цена без проживающих
    public BigDecimal priceEmpt = BigDecimal.ZERO;

}
