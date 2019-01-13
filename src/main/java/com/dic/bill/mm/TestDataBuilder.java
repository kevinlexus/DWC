package com.dic.bill.mm;

import com.dic.bill.model.scott.*;

import java.math.BigDecimal;

public interface TestDataBuilder {
    Ko buildKartForTest(House house, String suffix, BigDecimal area, int persCount, boolean isAddPers,
                        boolean isAddNabor, boolean isAddMeter, int statusId);

    void addVvodForTest(House house, String uslId, int distTp, Boolean isChargeInNotHeatingPeriod);

    void buildMeterForTest(Kart kart);

    Meter addMeterForTest(Ko koObj, String uslId, String dt1, String dt2);

    void buildKartPrForTest(Kart kart, int persCount);

    KartPr addKartPrForTest(Kart kart, int statusId, int relatId,
                            String fio, String dtBirdth, String dtReg,
                            String dtUnreg);

    void addStatePrForTest(KartPr kartPr, int statusId,
                           String dtFrom, String dtTo);

    void buildNaborForTest(Kart kart, int tp);

    void addNaborForTest(Kart kart, int orgId, String uslId,
                         BigDecimal koeff, BigDecimal norm,
                         BigDecimal vol, BigDecimal volAdd,
                         Vvod vvod);
}
