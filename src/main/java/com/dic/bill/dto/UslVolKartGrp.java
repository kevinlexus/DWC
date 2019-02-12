package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;

import java.math.BigDecimal;

/**
 * DTO для хранения сгруппированных объемов по лиц.счету
 */
public class UslVolKartGrp extends UslVol {

    // лиц.счет
    public Kart kart;
    // наличие в текущем периоде счетчика по услуге (даже если один день был)
    public boolean isExistMeterCurrPeriod = false;
    // наличие в текущем периоде проживающих по услуге (даже если один день был)
    public boolean isExistPersCurrPeriod = false;
    // детализированный объем, до 20 знаков после запятой, для округления
    //public BigDecimal volDet = BigDecimal.ZERO;
    // жилое помещение?
    public boolean isResidental;

}
