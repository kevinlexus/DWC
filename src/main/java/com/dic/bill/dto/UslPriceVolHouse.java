package com.dic.bill.dto;

import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения параметров для расчета начисления по дому:
 * Фактическая услуга, цена, тип объема и т.п.
 */
public class UslPriceVolHouse {

    // услуга основная (например х.в.)
    public Usl usl;
    // наличие счетчика
    public boolean isCounter;
    // пустая квартира?
    public boolean isEmpty;


    // кол-во лиц.счетов
    public int cntLsk = 0;
    // объем
    public BigDecimal vol = BigDecimal.ZERO;
    // объем свыше соц.нормы
    public BigDecimal volOverSoc = BigDecimal.ZERO;
    // объем без проживающих
    public BigDecimal volEmpty = BigDecimal.ZERO;

    // общая площадь
    public BigDecimal area = BigDecimal.ZERO;
    // общая площадь свыше соц.нормы
    public BigDecimal areaOverSoc = BigDecimal.ZERO;
    // общая площадь без проживающих
    public BigDecimal areaEmpty = BigDecimal.ZERO;

    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;
    // кол-во временно зарегистрированных
    public BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    public BigDecimal kprOt = BigDecimal.ZERO;

    public static final class UslPriceVolHouseBuilder {
        // услуга основная (например х.в.)
        public Usl usl;
        // наличие счетчика
        public boolean isCounter;
        // пустая квартира?
        public boolean isEmpty;
        // объем
        public BigDecimal vol = BigDecimal.ZERO;
        // объем свыше соц.нормы
        public BigDecimal volOverSoc = BigDecimal.ZERO;
        // объем без проживающих
        public BigDecimal volEmpty = BigDecimal.ZERO;
        // общая площадь
        public BigDecimal area = BigDecimal.ZERO;
        // общая площадь свыше соц.нормы
        public BigDecimal areaOverSoc = BigDecimal.ZERO;
        // общая площадь без проживающих
        public BigDecimal areaEmpty = BigDecimal.ZERO;
        // кол-во проживающих
        public BigDecimal kpr = BigDecimal.ZERO;
        // кол-во временно зарегистрированных
        public BigDecimal kprWr = BigDecimal.ZERO;
        // кол-во временно отсутствующих
        public BigDecimal kprOt = BigDecimal.ZERO;

    }
}