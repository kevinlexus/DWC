package com.dic.bill.dto;


import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи оплаты по внешнему лиц.счету
 * @author - Lev
 * @ver 1.00
 */
public interface KartExtPaymentRec {
    Integer getId();
    Date getDt();
    String getExtLsk();
    BigDecimal getSumma();
}
