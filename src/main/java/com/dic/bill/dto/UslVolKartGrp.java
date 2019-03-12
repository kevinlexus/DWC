package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для хранения сгруппированных объемов по лиц.счету
 */
@Getter
@Setter
public class UslVolKartGrp extends UslVol {

    // лиц.счет
    Kart kart;
    // наличие в текущем периоде счетчика по услуге (даже если один день был)
    boolean isExistMeterCurrPeriod = false;
    // наличие в текущем периоде проживающих по услуге (даже если один день был)
    boolean isExistPersCurrPeriod = false;

}
