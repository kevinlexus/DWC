package com.dic.bill.mm;

import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface KartMng {


	Ko getKoPremiseByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId;

    List<Ko> getKoByHouse(House house);

    List<Ko> getKoByVvod(Vvod vvod);

    boolean getPersCountByDate(Kart kart, Date dt);

    String getKartMainLsk(Kart kart);

    Optional<StateSch> getKartStateByDate(Kart kart, Date dt);

    void saveShortKartDescription(Ko ko);

    String generateUslNameShort(Kart kart, int var, int maxWords, String delimiter);

    String getAdr(Kart kart);

    String getAdrWithUk(Kart kart);
}
