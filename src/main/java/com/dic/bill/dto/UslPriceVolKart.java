package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения параметров для расчета начисления по лицюсчету:
 * Фактическая услуга, цена, тип объема и т.п.
 */
public class UslPriceVolKart extends UslVolKart {

    // дата начала
    public Date dtFrom;
    // дата окончания
    public Date dtTo;
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
        // жилая квартира?
        public boolean isResidental;
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
        // общая площадь
        public BigDecimal area = BigDecimal.ZERO;
        // общая площадь свыше соц.нормы
        public BigDecimal areaOverSoc = BigDecimal.ZERO;
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

        public UslPriceVolBuilder withIsResidental(boolean isResidental) {
            this.isResidental = isResidental;
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

        public UslPriceVolBuilder withArea(BigDecimal area) {
            this.area = area;
            return this;
        }

        public UslPriceVolBuilder withAreaOverSoc(BigDecimal areaOverSoc) {
            this.areaOverSoc = areaOverSoc;
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

        public UslPriceVolKart build() {
            UslPriceVolKart uslPriceVolKart = new UslPriceVolKart();
            uslPriceVolKart.kart = this.kart;
            uslPriceVolKart.usl = this.usl;
            uslPriceVolKart.org = this.org;
            uslPriceVolKart.socStdt = this.socStdt;
            uslPriceVolKart.price = this.price;
            uslPriceVolKart.priceOverSoc = this.priceOverSoc;
            uslPriceVolKart.priceEmpty = this.priceEmpty;
            uslPriceVolKart.isEmpty = this.isEmpty;
            uslPriceVolKart.isMeter = this.isCounter;
            uslPriceVolKart.isResidental = this.isResidental;
            uslPriceVolKart.dtTo = this.dtTo;
            uslPriceVolKart.dtFrom = this.dtFrom;

            // объемы (передаются уже в долях на 1 день)
            uslPriceVolKart.vol = this.vol;
            uslPriceVolKart.volOverSoc = this.volOverSoc;

            // получить долю в 1 день, от значения
            uslPriceVolKart.area = Utl.nvl(this.area, BigDecimal.ZERO).multiply(this.partDayMonth);
            uslPriceVolKart.areaOverSoc = Utl.nvl(this.areaOverSoc, BigDecimal.ZERO).multiply(this.partDayMonth);

            uslPriceVolKart.kpr = BigDecimal.valueOf(this.kpr).multiply(this.partDayMonth);
            uslPriceVolKart.kprOt = BigDecimal.valueOf(this.kprOt).multiply(this.partDayMonth);
            uslPriceVolKart.kprWr = BigDecimal.valueOf(this.kprWr).multiply(this.partDayMonth);


            return uslPriceVolKart;
        }
    }
}
