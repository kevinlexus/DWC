package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import com.ric.cmn.DistributableBigDecimal;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения объемов для расчета начисления
 */
@Getter @Setter
public class UslVol implements DistributableBigDecimal {

    // услуга основная (например х.в.)
    Usl usl;

    // объем, округленный до 5 знаков
    BigDecimal vol = BigDecimal.ZERO;

    // общая площадь
    BigDecimal area = BigDecimal.ZERO;

    // кол-во проживающих (для определения соц.нормы) (максимальное)
    BigDecimal kprNorm = BigDecimal.ZERO;

    // жилое помещение?
    boolean isResidental;

    @Override
    public BigDecimal getBdForDist() {
        return this.vol;
    }

    @Override
    public void setBdForDist(BigDecimal bd) {
        this.vol = bd;
    }
}
