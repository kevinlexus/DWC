package com.dic.bill.mm.impl;

import com.dic.bill.dao.*;
import com.dic.bill.dto.SumUslOrgDTO;
import com.dic.bill.mm.SaldoMng;
import com.dic.bill.model.scott.Kart;
import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SaldoMngImpl implements SaldoMng {

    private final
    ChargeDAO chargeDAO;
    private final
    AchargeDAO achargeDAO;
    private final
    ChangeDAO changeDAO;
    private final
    SaldoUslDAO saldoUslDAO;
    private final
    CorrectPayDAO correctPayDAO;
    private final
    KwtpDayDAO kwtpDayDAO;

    @PersistenceContext
    private EntityManager em;

    public SaldoMngImpl(ChargeDAO chargeDAO, AchargeDAO achargeDAO, ChangeDAO changeDAO,
                        SaldoUslDAO saldoUslDAO, CorrectPayDAO correctPayDAO, KwtpDayDAO kwtpDayDAO) {
        this.chargeDAO = chargeDAO;
        this.achargeDAO = achargeDAO;
        this.changeDAO = changeDAO;
        this.saldoUslDAO = saldoUslDAO;
        this.correctPayDAO = correctPayDAO;
        this.kwtpDayDAO = kwtpDayDAO;
    }


    /**
     * Получить исходящее сальдо, учитывая разные финансовые составляющие:
     *
     * @param kart             - лиц.счет
     * @param period           - текущий период
     * @param lstDistPayment   - уже распределенная, сохраненная оплата
     * @param lstDistControl   - распределенная оплата для контроля исх кред.сал.
     * @param isSalIn          - учесть входящее сальдо
     * @param isChrg           - учесть начисление
     * @param isChrgPrevPeriod - учесть начисление предыдущего периода
     * @param isChng           - учесть перерасчеты
     * @param isCorrPay        - учесть корректировки оплаты
     * @param isPay            - учесть оплату
     * @param prevPeriod       - предыдущий период (работает совместно с isChrgPrevPeriod)
     */
    @Override
    public List<SumUslOrgDTO> getOutSal(Kart kart, String period,
                                        List<SumUslOrgDTO> lstDistPayment,
                                        List<SumUslOrgDTO> lstDistControl,
                                        boolean isSalIn, boolean isChrg, boolean isChrgPrevPeriod, boolean isChng,
                                        boolean isCorrPay, boolean isPay, String prevPeriod) throws WrongParam {
        if (isChrg && isChrgPrevPeriod) {
            throw new WrongParam("ОШИБКА! Не допустимо использовать isChrg=true и isChrgPrevPeriod=true одновременно");
        } else if (isChrgPrevPeriod && prevPeriod == null) {
            throw new WrongParam("ОШИБКА! Не указан предыдущий период начисления - prevPeriod");
        } else if (!isChrgPrevPeriod && prevPeriod != null) {
            throw new WrongParam("ОШИБКА! Указание предыдущего период начисления - prevPeriod - не имеет смысла");
        }

        List<SumUslOrgDTO> lst = new ArrayList<>();
        // уже распределенная, сохраненная оплата
        if (lstDistPayment != null) {
            lstDistPayment.forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
        }
        // распределенная оплата для контроля исх кред.сал.
        if (lstDistControl != null) {
            lstDistControl.forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
        }
        if (isSalIn) {
            // вх.сальдо
            saldoUslDAO.getSaldoUslByLsk(kart.getLsk(), period).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
        }
        if (isChrg) {
            // начисление
            chargeDAO.getChargeByLskGrouped(kart.getLsk()).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
        }
        if (isChrgPrevPeriod) {
            // начисление предыдущего периода
            achargeDAO.getAchargeByLskPeriodGrouped(kart.getLsk(), Integer.valueOf(prevPeriod)).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
        }
        if (isChng) {
            // перерасчеты
            changeDAO.getChangeByLskGrouped(kart.getLsk()).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
        }
        if (isCorrPay) {
            // корректировки оплатой
            correctPayDAO.getCorrectPayByLskGrouped(kart.getLsk(), period).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
        }
        if (isPay) {
            // оплата
            kwtpDayDAO.getKwtpDayByLskGrouped(kart.getLsk(), 1).forEach(t ->
                    groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
        }
        return lst;
    }

    /**
     * Сгруппировать коллекцию с другой коллекцией
     *
     * @param lst   - исходная коллекция, подлежащая модификации
     * @param lstCorr - корректировка
     */
    @Override
    public void groupByLstUslOrg(List<SumUslOrgDTO> lst, List<SumUslOrgDTO> lstCorr) {
        lstCorr.forEach(t-> {
            groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma());
        });
    }

    /**
     * Сгруппировать коллекцию по услуге, организации
     *
     * @param lst   - исходная коллекция
     * @param t - строка усл, орг, сумма
     */
    @Override
    public void groupByLstUslOrg(List<SumUslOrgDTO> lst, SumUslOrgDTO t) {
        groupByLstUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma());
    }

    /**
     * Сгруппировать коллекцию по услуге, организации
     *
     * @param lst   - исходная коллекция
     * @param uslId - услуга
     * @param orgId - организация
     * @param summa - сумма
     */
    @Override
    public void groupByLstUslOrg(List<SumUslOrgDTO> lst, String uslId, Integer orgId, BigDecimal summa) {
        if (summa.compareTo(BigDecimal.ZERO) !=0) {
            SumUslOrgDTO foundElem = lst.stream().filter(t -> t.getUslId().equals(uslId) && t.getOrgId().equals(orgId))
                    .findFirst().orElse(null);
            if (foundElem != null) {
                foundElem.setBdForDist(foundElem.getBdForDist().add(summa));
            } else {
                lst.add(new SumUslOrgDTO(uslId, orgId, summa));
            }
        }
    }


}