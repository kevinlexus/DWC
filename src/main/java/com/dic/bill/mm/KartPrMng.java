package com.dic.bill.mm;

import com.dic.bill.dto.CountPers;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.StatePr;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.excp.WrongParam;

import java.util.Date;
import java.util.List;

public interface KartPrMng {

    CountPers getCountPersByDate(Kart kart, Double parVarCntKpr, List<StatePr> lstState,
                            Usl usl, Date dt);
}
