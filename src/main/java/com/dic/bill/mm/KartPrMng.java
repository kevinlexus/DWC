package com.dic.bill.mm;

import com.dic.bill.dto.CountPers;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.excp.WrongParam;

import java.util.Date;

public interface KartPrMng {

    void getCountPersByDate(CountPers countPers, Kart kart, Usl usl, Date dt) throws WrongParam;
}
