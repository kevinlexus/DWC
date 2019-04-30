package com.dic.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для хранения сгруппированных сумм по услуге и организации со знаком
 */
@Getter @Setter
@AllArgsConstructor
public class SumUslOrgSignDTO extends SumUslOrgDTO {

    // знак
    private Integer sign;

}
