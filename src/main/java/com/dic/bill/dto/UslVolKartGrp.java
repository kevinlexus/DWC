package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;

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
}
