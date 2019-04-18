package com.dic.bill.mm;

import com.dic.bill.dto.SumUslOrgDTO;
import com.dic.bill.model.scott.Kart;
import com.ric.cmn.excp.WrongParam;

import java.math.BigDecimal;
import java.util.List;

public interface SaldoMng {


    List<SumUslOrgDTO> getOutSal(Kart kart, String period, List<SumUslOrgDTO> lstDistPayment, List<SumUslOrgDTO> lstDistControl, boolean isSalIn, boolean isChrg, boolean isChrgPrevPeriod, boolean isChng,
                                 boolean isCorrPay, boolean isPay, String prevPeriod) throws WrongParam;

    void groupByUslOrg(List<SumUslOrgDTO> lst, String uslId, Integer orgId, BigDecimal summa);
}
