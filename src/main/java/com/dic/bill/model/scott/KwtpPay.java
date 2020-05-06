package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;

public interface KwtpPay {
    Integer getId();
    Kart getKart();
    Date getDt();
    BigDecimal getSumma();
}
