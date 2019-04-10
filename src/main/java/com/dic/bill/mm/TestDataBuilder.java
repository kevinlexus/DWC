package com.dic.bill.mm;

import com.dic.bill.model.scott.*;

import java.math.BigDecimal;

public interface TestDataBuilder {
    Ko buildKartForTest(House house, String suffix, BigDecimal area, int persCount, boolean isAddPers,
                        boolean isAddNabor, int statusId, int psch);

    void addVvodForTest(House house, String uslId, int distTp, Boolean isChargeInNotHeatingPeriod, BigDecimal kub, Boolean isUseSch);

    void buildMeterForTest(Kart kart);

    Meter addMeterForTest(Ko koObj, String uslId, String dt1, String dt2);

    void addChargeForTest(Kart kart, String uslId, String strSumma);

    ChangeDoc buildChangeDocForTest(String strDt, String mgChange);

    void addChangeForTest(Kart kart, ChangeDoc changeDoc, int userId, String uslId, Integer orgId,
                          String mgChange, String mg2, Integer type, String strDt, String strSumma);

    void addCorrectPayForTest(Kart kart, ChangeDoc changeDoc, int userId, String uslId, Integer orgId,
                              String dopl, String mg, String strDt, Integer var, String strSumma);

    void buildSaldoUslForTest(Kart kart, int orgId, String uslId,
                              String mg, String strSumma);

    Kwtp buildKwtpForTest(Kart kart, String dopl, String strDt, String strDtInk, int nink,
                          String nkom, String numDoc, String oper,
                          String strSumma, String strPenya);

    KwtpMg addKwtpMgForTest(Kwtp kwtp, String dopl, String strSumma, String strPenya);

    void addKwtpDayForTest(KwtpMg kwtpMg, int tp, String uslId, int orgId, String strSumma);

    void buildKartPrForTest(Kart kart, int persCount);

    KartPr addKartPrForTest(Kart kart, int statusId, int relatId,
                            String fio, String dtBirdth, String dtReg,
                            String dtUnreg);

    void addStatePrForTest(KartPr kartPr, int statusId,
                           String dtFrom, String dtTo);

    void buildNaborForTest(Kart kart, int tp);

    void addNaborForTest(Kart kart, String uslId, int orgId,
                         BigDecimal koeff, BigDecimal norm,
                         BigDecimal vol, BigDecimal volAdd,
                         Vvod vvod);
}
