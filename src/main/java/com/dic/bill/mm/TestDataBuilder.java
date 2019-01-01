package com.dic.bill.mm;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.KartPr;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;

import java.math.BigDecimal;

public interface TestDataBuilder {
    Kart buildKartForTest(String lsk, boolean isAddPers, boolean isAddNabor, boolean isAddMeter);

    void buildMeterForTest(Kart kart);

    Meter addMeterForTest(Ko koObj, String uslId, String dt1, String dt2);

    void buildKartPrForTest(Kart kart);

    KartPr addKartPrForTest(Kart kart, int statusId, int relatId,
                            String fio, String dtBirdth, String dtReg,
                            String dtUnreg);

    void addStatePrForTest(KartPr kartPr, int statusId,
                           String dtFrom, String dtTo);

    void buildNaborForTest(Kart kart);

    void addNaborForTest(Kart kart, int orgId, String uslId,
                         BigDecimal koeff, BigDecimal norm,
                         BigDecimal vol, BigDecimal volAdd);
}
