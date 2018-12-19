package com.dic.bill.mm;

import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface KartMng {


	Ko getKoByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;

	boolean getPersCountByDate(Kart kart, Date dt);

    Kart buildKartForTest(String lsk);

    void buildKartPrForTest(Kart kart);

    KartPr addKartPrForTest(Kart kart, int statusId, int relatId,
                            String fio, String dtBirdth, String dtReg,
                            String dtUnreg);

    void addStatusPrForTest(KartPr kartPr, int statusId,
                            String dtFrom, String dtTo);

    void buildNaborForTest(Kart kart);

    void addNabor(Kart kart, int orgId, String uslId,
                  BigDecimal koeff, BigDecimal norm,
                  BigDecimal vol, BigDecimal volAdd);
}
