package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Базовый класс DTO для хранения параметров для расчета начисления по лиц.счету:
 * Фактическая услуга, цена, тип объема и т.п.
 */
@Getter @Setter
abstract class UslPriceVolKartBase extends UslVolKart {

    // организация
    Org org;

    // услуга свыше соц.нормы
    Usl uslOverSoc;
    // услуга по 0 зарег.
    Usl uslEmpt;

    // соц.норма
    BigDecimal socStdt = BigDecimal.ZERO;

    // цена
    BigDecimal price = BigDecimal.ZERO;
    // цена свыше соц.нормы
    BigDecimal priceOverSoc = BigDecimal.ZERO;
    // цена без проживающих
    BigDecimal priceEmpty = BigDecimal.ZERO;

    // объем свыше соц.нормы
    BigDecimal volOverSoc = BigDecimal.ZERO;

    // общая площадь свыше соц.нормы
    BigDecimal areaOverSoc = BigDecimal.ZERO;

    // кол-во проживающих
    BigDecimal kpr = BigDecimal.ZERO;
    // кол-во временно зарегистрированных
    BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    BigDecimal kprOt = BigDecimal.ZERO;

}
