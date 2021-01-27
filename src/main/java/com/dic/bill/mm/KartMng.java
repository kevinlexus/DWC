package com.dic.bill.mm;

import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import com.ric.cmn.excp.WrongParam;
import com.ric.cmn.excp.WrongValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface KartMng {


	Ko getKoPremiseByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;

    List<Ko> getKoByHouse(House house);

    List<Ko> getKoByVvod(Vvod vvod);

    boolean getPersCountByDate(Kart kart, Date dt);

    String getKartMainLsk(Kart kart);

    Kart getKartMain(Kart kart);

    Optional<StateSch> getKartStateByDate(Kart kart, Date dt);

    void saveShortKartDescription(Ko ko);

    String generateUslNameShort(Kart kart, int var, int maxWords, String delimiter);

    String getAdrWithCity(Kart kart);

    void updateKartDetailOrd1() throws WrongValue;

    boolean getIsRenter(Kart kart);

    Ko buildKart(String houseGUID, BigDecimal area, int persCount, boolean isAddPers, boolean isAddNabor,
                 int statusId, int psch, int ukId, String lskTp) throws WrongParam;

    Kart createKart(String lsk, Integer var, String lskTp, String reu, String kw,
                    Integer houseId, Long klskId, Long klskPremise,
                    String fam, String im, String ot) throws WrongParam;

    void checkStateSch(Kart kart, Date curDt, int psch);
}
