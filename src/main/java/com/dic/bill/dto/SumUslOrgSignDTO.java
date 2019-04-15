package com.dic.bill.dto;

import com.ric.cmn.DistributableBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO для хранения сгруппированных сумм по услуге и организации со знаком
 */
@Getter @Setter
@AllArgsConstructor
public class SumUslOrgSignDTO extends SumUslOrgDTO {

    // знак
    private Integer sign;

}
