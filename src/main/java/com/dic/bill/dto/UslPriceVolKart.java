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
 * DTO для хранения параметров для расчета начисления по лиц.счету:
 * Фактическая услуга, цена, тип объема и т.п.
 */
@Getter
@Setter
public class UslPriceVolKart extends UslPriceVolKartBase {

    // дата расчета
    Date dt;

    public static final class UslPriceVolBuilder {
        // лиц.счет
        private Kart kart;
        // дата расчета
        private Date dt;
        // услуга основная (например х.в.)
        private Usl usl;
        // услуга свыше соц.норм.
        private Usl uslOverSoc;
        // услуга по 0 зарег.
        private Usl uslEmpt;
        // организация
        private Org org;
        // наличие счетчика
        private boolean isMeter;
        // пустая квартира?
        private boolean isEmpty;
        // жилая квартира?
        private boolean isResidental;
        // соц.норма
        private BigDecimal socStdt = BigDecimal.ZERO;
        // цена
        private BigDecimal price = BigDecimal.ZERO;
        // цена свыше соц.нормы
        private BigDecimal priceOverSoc = BigDecimal.ZERO;
        // цена без проживающих
        private BigDecimal priceEmpty = BigDecimal.ZERO;
        // объем
        private BigDecimal vol = BigDecimal.ZERO;
        // объем свыше соц.нормы
        private BigDecimal volOverSoc = BigDecimal.ZERO;
        // общая площадь
        private BigDecimal area = BigDecimal.ZERO;
        // общая площадь свыше соц.нормы
        private BigDecimal areaOverSoc = BigDecimal.ZERO;
        // кол-во проживающих
        private int kpr = 0;
        // кол-во временно зарегистрированных
        private int kprWr = 0;
        // кол-во временно отсутствующих
        private int kprOt = 0;
        // макс.кол-во проживающих (кол-во прож. для определения соц.нормы) (для сохранения в C_CHARGE)
        private int kprNorm = 0;
        // доля дня в месяце
        private BigDecimal partDayMonth = BigDecimal.ZERO;

        private UslPriceVolBuilder() {
        }

        public static UslPriceVolBuilder anUslPriceVol() {
            return new UslPriceVolBuilder();
        }

        public UslPriceVolBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public UslPriceVolBuilder withDt(Date dt) {
            this.dt = dt;
            return this;
        }

        public UslPriceVolBuilder withUsl(Usl usl) {
            this.usl = usl;
            return this;
        }

        public UslPriceVolBuilder withUslOverSoc(Usl uslOverSoc) {
            this.uslOverSoc = uslOverSoc;
            return this;
        }
        public UslPriceVolBuilder withUslEmpt(Usl uslEmpt) {
            this.uslEmpt = uslEmpt;
            return this;
        }

        public UslPriceVolBuilder withOrg(Org org) {
            this.org = org;
            return this;
        }

        public UslPriceVolBuilder withIsMeter(boolean isMeter) {
            this.isMeter = isMeter;
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

        public UslPriceVolBuilder withKprNorm(int kprNorm) {
            this.kprNorm = kprNorm;
            return this;
        }

        public UslPriceVolBuilder withPartDayMonth(BigDecimal partDayMonth) {
            this.partDayMonth = partDayMonth;
            return this;
        }

        /**
         * Builder строить руками!
         * @return
         */
        public UslPriceVolKart build() {
            UslPriceVolKart uslPriceVolKart = new UslPriceVolKart();
            uslPriceVolKart.kart = this.kart;
            uslPriceVolKart.usl = this.usl;
            uslPriceVolKart.uslOverSoc = this.uslOverSoc;
            uslPriceVolKart.uslEmpt = this.uslEmpt;
            uslPriceVolKart.org = this.org;
            uslPriceVolKart.socStdt = this.socStdt;
            uslPriceVolKart.price = this.price;
            uslPriceVolKart.priceOverSoc = this.priceOverSoc;
            uslPriceVolKart.priceEmpty = this.priceEmpty;
            uslPriceVolKart.isEmpty = this.isEmpty;
            uslPriceVolKart.isMeter = this.isMeter;
            uslPriceVolKart.isResidental = this.isResidental;
            uslPriceVolKart.dt = this.dt;

            // объемы (передаются уже в долях на 1 день)
            uslPriceVolKart.vol = this.vol;
            uslPriceVolKart.volOverSoc = this.volOverSoc;

            // получить долю в 1 день, от значения
            uslPriceVolKart.area = Utl.nvl(this.area, BigDecimal.ZERO).multiply(this.partDayMonth);
            uslPriceVolKart.areaOverSoc = Utl.nvl(this.areaOverSoc, BigDecimal.ZERO).multiply(this.partDayMonth);

            uslPriceVolKart.kpr = BigDecimal.valueOf(this.kpr).multiply(this.partDayMonth);
            uslPriceVolKart.kprOt = BigDecimal.valueOf(this.kprOt).multiply(this.partDayMonth);
            uslPriceVolKart.kprWr = BigDecimal.valueOf(this.kprWr).multiply(this.partDayMonth);
            uslPriceVolKart.kprNorm = BigDecimal.valueOf(this.kprNorm).multiply(this.partDayMonth);

            return uslPriceVolKart;
        }
    }
}
