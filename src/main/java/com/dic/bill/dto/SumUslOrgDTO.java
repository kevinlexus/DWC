package com.dic.bill.dto;

import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.DistributableBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения сгруппированных сумм по услуге и организации
 */
@Getter @Setter
@AllArgsConstructor
public class SumUslOrgDTO implements DistributableBigDecimal {

    private String uslId;
    private Integer orgId;
    private BigDecimal summa;

    @Override
    public BigDecimal getBdForDist() {
        return this.summa;
    }

    @Override
    public void setBdForDist(BigDecimal bd) {
        this.summa = bd;
    }


}
