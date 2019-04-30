package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.SprProcPay;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Итоги распределения
 * @version 1.00
 */
@Getter
@Setter
public class Amount {
    // Cтрока платежа:
    // лиц.счет
    private Kart kart;
    // id строки платежа по периодам
    private int kwtpMgId;
    // № п.п. строки распределения в KWTP_DAY_LOG
    private int npp = 1;
    // сумма оплаты
    private BigDecimal summa = BigDecimal.ZERO;
    // сумма оплаченной пени
    private BigDecimal penya = BigDecimal.ZERO;

    // сумма оплаты - для контроля
    private BigDecimal summaControl= BigDecimal.ZERO;
    // сумма оплаченной пени - для контроля
    private BigDecimal penyaControl = BigDecimal.ZERO;

    // период платежа
    private String dopl;
    // инкассация
    private int nink;
    // № комп.
    private String nkom;
    // код операции
    private String oper;
    // дата платежа
    private Date dtek;
    // дата инкассации
    private Date datInk;

    // Прочие параметры:
    // входящее, общее сальдо
    private List<SumUslOrgDTO> inSal;
    // итог по общ.сал.
    private BigDecimal amntInSal = BigDecimal.ZERO;
    // итог задолженность периода C_KWTP_MG.DOPL (на момент распределения по периоду в C_GET_PAY строка 580)
    private BigDecimal amntDebtDopl = BigDecimal.ZERO;
    // итог по начислению предыдущего периода
    private BigDecimal amntChrgPrevPeriod = BigDecimal.ZERO;

    // список закрытых орг.
    private List<SprProcPay> lstSprProcPay;
    // уже распределенная оплата
    private List<SumUslOrgDTO> lstDistPayment = new ArrayList<>();
    // уже распределенная пеня
    private List<SumUslOrgDTO> lstDistPenya = new ArrayList<>();
    // уже распределенная оплата - для контроля
    private List<SumUslOrgDTO> lstDistControl = new ArrayList<>();

    // существует ли сумма для распределения?
    public boolean isExistSumma() {
        return this.getSumma().compareTo(BigDecimal.ZERO)!=0;
    }
    // существует ли пеня для распределения?
    public boolean isExistPenya() {
        return this.getPenya().compareTo(BigDecimal.ZERO)!=0;
    }
}
