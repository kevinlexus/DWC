package com.dic.bill.dao;

import com.dic.bill.model.exs.Eolink;

import java.util.List;

public interface EolinkDAO {

    Eolink getEolinkByGuid(String guid);

    List<Eolink> getChildByTp(Eolink parent, String tp);

    Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
                                 String kw, String entry, String tp);

    List<Eolink> getValsNotSaved();

    List<Eolink> getUk();

    List<Eolink> getHouse();

    List<Eolink> getEolinkByTpWoTaskTp(String eolTp, String actTp, String parentCD);

    Eolink getHouseByKulNd(String kul, String nd);

    Eolink getEolinkUkByReu(String reu);


}
