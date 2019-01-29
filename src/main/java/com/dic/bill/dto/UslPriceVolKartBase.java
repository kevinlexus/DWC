package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Базовый класс DTO для хранения параметров для расчета начисления по лиц.счету:
 * Фактическая услуга, цена, тип объема и т.п.
 */
abstract class UslPriceVolKartBase extends UslVolKart {

    // организация
    public Org org;

    // соц.норма
    public BigDecimal socStdt = BigDecimal.ZERO;

    // цена
    public BigDecimal price = BigDecimal.ZERO;
    // цена свыше соц.нормы
    public BigDecimal priceOverSoc = BigDecimal.ZERO;
    // цена без проживающих
    public BigDecimal priceEmpty = BigDecimal.ZERO;

    // объем свыше соц.нормы
    public BigDecimal volOverSoc = BigDecimal.ZERO;

    // общая площадь свыше соц.нормы
    public BigDecimal areaOverSoc = BigDecimal.ZERO;

    // кол-во временно зарегистрированных
    public BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    public BigDecimal kprOt = BigDecimal.ZERO;

}
