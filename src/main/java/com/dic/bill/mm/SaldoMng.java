package com.dic.bill.mm;

import com.dic.bill.dto.SumUslOrgDTO;
import com.dic.bill.model.scott.Kart;

import java.math.BigDecimal;
import java.util.List;

public interface SaldoMng {


    List<SumUslOrgDTO> getOutSal(Kart kart, String period, List<SumUslOrgDTO> lstDistPayment, List<SumUslOrgDTO> lstDistControl, boolean isSalIn, boolean isChrg, boolean isChng,
                                 boolean isCorrPay, boolean isPay);

    void groupByUslOrg(List<SumUslOrgDTO> lst, String uslId, Integer orgId, BigDecimal summa);
}
