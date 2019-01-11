package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения параметров для расчета начисления по квартире:
 * Фактическая услуга, цена, тип объема и т.п.
 */
public class UslPriceVol {

    // лиц.счет
    public Kart kart;
    // дата начала
    public Date dtFrom;
    // дата окончания
    public Date dtTo;
    // услуга основная (например х.в.)
    public Usl usl;
    // организация
    public Org org;
    // наличие счетчика
    public boolean isCounter;
    // пустая квартира?
    public boolean isEmpty;
    // соц.норма
    public BigDecimal socStdt = BigDecimal.ZERO;

    // цена
    public BigDecimal price = BigDecimal.ZERO;
    // цена свыше соц.нормы
    public BigDecimal priceOverSoc = BigDecimal.ZERO;
    // цена без проживающих
    public BigDecimal priceEmpty = BigDecimal.ZERO;

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

    public static final class UslPriceVolBuilder {
        // лиц.счет
        public Kart kart;
        // дата начала
        public Date dtFrom;
        // дата окончания
        public Date dtTo;
        // услуга основная (например х.в.)
        public Usl usl;
        // организация
        public Org org;
        // наличие счетчика
        public boolean isCounter;
        // пустая квартира?
        public boolean isEmpty;
        // соц.норма
        public BigDecimal socStdt = BigDecimal.ZERO;
        // цена
        public BigDecimal price = BigDecimal.ZERO;
        // цена свыше соц.нормы
        public BigDecimal priceOverSoc = BigDecimal.ZERO;
        // цена без проживающих
        public BigDecimal priceEmpty = BigDecimal.ZERO;
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
        public int kpr = 0;
        // кол-во временно зарегистрированных
        public int kprWr = 0;
        // кол-во временно отсутствующих
        public int kprOt = 0;
        // доля дня в месяце
        public BigDecimal partDayMonth = BigDecimal.ZERO;

        private UslPriceVolBuilder() {
        }

        public static UslPriceVolBuilder anUslPriceVol() {
            return new UslPriceVolBuilder();
        }

        public UslPriceVolBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public UslPriceVolBuilder withDtFrom(Date dtFrom) {
            this.dtFrom = dtFrom;
            return this;
        }

        public UslPriceVolBuilder withDtTo(Date dtTo) {
            this.dtTo = dtTo;
            return this;
        }

        public UslPriceVolBuilder withUsl(Usl usl) {
            this.usl = usl;
            return this;
        }

        public UslPriceVolBuilder withOrg(Org org) {
            this.org = org;
            return this;
        }

        public UslPriceVolBuilder withIsCounter(boolean isCounter) {
            this.isCounter = isCounter;
            return this;
        }

        public UslPriceVolBuilder withIsEmpty(boolean isEmpty) {
            this.isEmpty = isEmpty;
            return this;
        }

        public UslPriceVolBuilder withSocStdt(BigDecimal socStdt) {
            this.socStdt = socStdt;
            return this;
        }

        public UslPriceVolBuilder withPriceOverSoc(BigDecimal priceOverSoc) {
            this.priceOverSoc = priceOverSoc;
            return this;
        }

        public UslPriceVolBuilder withPriceEmpty(BigDecimal priceEmpty) {
            this.priceEmpty = priceEmpty;
            return this;
        }

        public UslPriceVolBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public UslPriceVolBuilder withVol(BigDecimal vol) {
            this.vol = vol;
            return this;
        }

        public UslPriceVolBuilder withVolOverSoc(BigDecimal volOverSoc) {
            this.volOverSoc = volOverSoc;
            return this;
        }

        public UslPriceVolBuilder withVolEmpty(BigDecimal volEmpty) {
            this.volEmpty = volEmpty;
            return this;
        }

        public UslPriceVolBuilder withArea(BigDecimal area) {
            this.area = area;
            return this;
        }

        public UslPriceVolBuilder withAreaOverSoc(BigDecimal areaOverSoc) {
            this.areaOverSoc = areaOverSoc;
            return this;
        }

        public UslPriceVolBuilder withAreaEmpty(BigDecimal areaEmpty) {
            this.areaEmpty = areaEmpty;
            return this;
        }

        public UslPriceVolBuilder withKpr(int kpr) {
            this.kpr = kpr;
            return this;
        }

        public UslPriceVolBuilder withKprWr(int kprWr) {
            this.kprWr = kprWr;
            return this;
        }

        public UslPriceVolBuilder withKprOt(int kprOt) {
            this.kprOt = kprOt;
            return this;
        }

        public UslPriceVolBuilder withPartDayMonth(BigDecimal partDayMonth) {
            this.partDayMonth = partDayMonth;
            return this;
        }

        public UslPriceVol build() {
            UslPriceVol uslPriceVol = new UslPriceVol();
            uslPriceVol.kart = this.kart;
            uslPriceVol.usl = this.usl;
            uslPriceVol.org = this.org;
            uslPriceVol.socStdt = this.socStdt;
            uslPriceVol.price = this.price;
            uslPriceVol.priceOverSoc = this.priceOverSoc;
            uslPriceVol.priceEmpty = this.priceEmpty;
            uslPriceVol.isEmpty = this.isEmpty;
            uslPriceVol.isCounter = this.isCounter;
            uslPriceVol.dtTo = this.dtTo;
            uslPriceVol.dtFrom = this.dtFrom;

            // объемы (передаются уже в долях на 1 день)
            uslPriceVol.vol = this.vol;
            uslPriceVol.volOverSoc = this.volOverSoc;
            uslPriceVol.volEmpty = this.volEmpty;

            // получить долю в 1 день, от значения
            uslPriceVol.area = Utl.nvl(this.area, BigDecimal.ZERO).multiply(this.partDayMonth);
            uslPriceVol.areaOverSoc = Utl.nvl(this.areaOverSoc, BigDecimal.ZERO).multiply(this.partDayMonth);
            uslPriceVol.areaEmpty = Utl.nvl(this.areaEmpty, BigDecimal.ZERO).multiply(this.partDayMonth);

            uslPriceVol.kpr = BigDecimal.valueOf(this.kpr).multiply(this.partDayMonth);
            uslPriceVol.kprOt = BigDecimal.valueOf(this.kprOt).multiply(this.partDayMonth);
            uslPriceVol.kprWr = BigDecimal.valueOf(this.kprWr).multiply(this.partDayMonth);


            return uslPriceVol;
        }
    }
}
