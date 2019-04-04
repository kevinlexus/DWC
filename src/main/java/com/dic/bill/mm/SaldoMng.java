package com.dic.bill.mm;

import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.model.scott.Kart;

import java.util.List;

public interface SaldoMng {


	public void distSalByChPay();

    List<SumUslOrgRec> getOutSal(Kart kart, String period, boolean isSalIn, boolean isChrg, boolean isChng,
                                 boolean isCorrPay, boolean isPay);
}
