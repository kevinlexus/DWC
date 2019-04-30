package com.dic.bill.dto;

import com.ric.cmn.DistributableBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO для хранения сгруппированных сумм по услуге и организации
 */
@Getter
@Setter
@AllArgsConstructor
public class SumUslOrgDTO implements DistributableBigDecimal {

    // Id услуги
    private String uslId;
    // Id организации
    private Integer orgId;
    // сумма
    private BigDecimal summa;

    SumUslOrgDTO() {

    }

    @Override
    public BigDecimal getBdForDist() {
        return this.summa;
    }

    @Override
    public void setBdForDist(BigDecimal bd) {
        this.summa = bd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SumUslOrgDTO that = (SumUslOrgDTO) o;
        return Objects.equals(getUslId(), that.getUslId()) &&
                Objects.equals(getOrgId(), that.getOrgId()) &&
                Objects.equals(getSumma(), that.getSumma());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUslId(), getOrgId(), getSumma());
    }
}
