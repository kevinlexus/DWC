package com.dic.bill.mm;

import com.dic.bill.dto.CountPers;
import com.dic.bill.dto.SocStandart;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Nabor;
import com.ric.cmn.excp.ErrorWhileChrg;

import java.math.BigDecimal;
import java.util.Date;

public interface KartPrMng {

    CountPers getCountPersByDate(Kart kartMain, Nabor nabor, int parVarCntKpr, int parCapCalcKprTp,
                                 Date dt, boolean isMeterExist);

    SocStandart getSocStdtVol(BigDecimal kartArea, Nabor t, CountPers countPers) throws ErrorWhileChrg;
}
