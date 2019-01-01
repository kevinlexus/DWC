package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO для хранения параметров для расчета начисления:
 * Фактическая услуга, цена, тип объема и т.п.
 */
public class UslPriceVol {

    // дата начала
    public Date dtFrom;
    // дата окончания
    public Date dtTo;
    // услуга основная (например х.в.)
    //public Usl usl;
    // услуга фактическая (например х.в. свыше с.н./ без прожив.)
    public Usl uslFact;
    // тип объема
    public int typeVol;
    // цена
    public BigDecimal price = BigDecimal.ZERO;
    // объем
    public BigDecimal vol = BigDecimal.ZERO;
    // общая площадь
    public BigDecimal area = BigDecimal.ZERO;


    public static final class UslPriceVolBuilder {
        // дата начала
        public Date dtFrom;
        // дата окончания
        public Date dtTo;
        // услуга основная (например х.в.)
        //public Usl usl;
        // услуга фактическая (например х.в. свыше с.н./ без прожив.)
        public Usl uslFact;
        // тип объема
        public int typeVol;
        // цена
        public BigDecimal price = BigDecimal.ZERO;
        // объем
        public BigDecimal vol = BigDecimal.ZERO;
        // общая площадь
        public BigDecimal area = BigDecimal.ZERO;
        // доля дня в месяце
        public BigDecimal partDayMonth = BigDecimal.ZERO;

        private UslPriceVolBuilder() {
        }

        public static UslPriceVolBuilder anUslPriceVol() {
            return new UslPriceVolBuilder();
        }

        public UslPriceVolBuilder withDtFrom(Date dtFrom) {
            this.dtFrom = dtFrom;
            return this;
        }

        public UslPriceVolBuilder withDtTo(Date dtTo) {
            this.dtTo = dtTo;
            return this;
        }

        public UslPriceVolBuilder withUslFact(Usl uslFact) {
            this.uslFact = uslFact;
            return this;
        }

        public UslPriceVolBuilder withTypeVol(int typeVol) {
            this.typeVol = typeVol;
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

        public UslPriceVolBuilder withArea(BigDecimal area) {
            this.area = area;
            return this;
        }


        public UslPriceVolBuilder withPartDayMonth(BigDecimal partDayMonth) {
            this.partDayMonth = partDayMonth;
            return this;
        }


        public UslPriceVol build() {
            UslPriceVol uslPriceVol = new UslPriceVol();
            uslPriceVol.dtTo = this.dtTo;
            uslPriceVol.dtFrom = this.dtFrom;
            uslPriceVol.typeVol = this.typeVol;
            uslPriceVol.uslFact = this.uslFact;
            uslPriceVol.price = this.price;
            uslPriceVol.vol = this.vol;
            // получить долю от значения
            uslPriceVol.area = Utl.nvl(this.area, BigDecimal.ZERO).multiply(this.partDayMonth);
            return uslPriceVol;
        }
    }
}
