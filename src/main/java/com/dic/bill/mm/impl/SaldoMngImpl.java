package com.dic.bill.mm.impl;

import com.dic.bill.dao.*;
import com.dic.bill.dto.SumUslOrgDTO;
import com.dic.bill.model.scott.Kart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.mm.SaldoMng;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SaldoMngImpl implements SaldoMng {

	@Autowired
	ChargePayDAO chargePayDAO;
	@Autowired
	ChargeDAO chargeDAO;
	@Autowired
	ChangeDAO changeDAO;
	@Autowired
	SaldoUslDAO saldoUslDAO;
	@Autowired
	CorrectPayDAO correctPayDAO;
	@Autowired
	KwtpDayDAO kwtpDayDAO;

	@PersistenceContext
	private EntityManager em;


	/**
	 * Получить исходящее сальдо, учитывая разные финансовые составляющие:
	 * @param kart - лиц.счет
	 * @param period - текущий период
	 * @param lstDistPayment - уже распределенная оплата
	 * @param isSalIn - учесть входящее сальдо
	 * @param isChrg - учесть начисление
	 * @param isChng - учесть перерасчеты
	 * @param isCorrPay - учесть корректировки оплаты
	 * @param isPay - учесть оплату
	 */
	@Override
	public List<SumUslOrgDTO> getOutSal(Kart kart, String period, List<SumUslOrgDTO> lstDistPayment,
										boolean isSalIn, boolean isChrg, boolean isChng,
										boolean isCorrPay, boolean isPay) {
		List<SumUslOrgDTO> lst = new ArrayList<>();
		// уже распределенная оплата
		if (lstDistPayment != null) {
			lstDistPayment.forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
		}
		if (isSalIn) {
			// вх.сальдо
			saldoUslDAO.getSaldoUslByLsk(kart.getLsk(), period).forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
		}
		if (isChrg) {
			// начисление
			chargeDAO.getChargeByLskGrouped(kart.getLsk()).forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
		}
		if (isChng) {
			// перерасчеты
			changeDAO.getChangeByLskGrouped(kart.getLsk()).forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma()));
		}
		if (isCorrPay) {
			// корректировки оплатой
			correctPayDAO.getCorrectPayByLskGrouped(kart.getLsk(), period).forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
		}
		if (isPay) {
			// оплата
			kwtpDayDAO.getKwtpDayByLskGrouped(kart.getLsk(), 1).forEach(t->
					groupByUslOrg(lst, t.getUslId(), t.getOrgId(), t.getSumma().negate()));
		}
		return lst;
	}

	/**
	 * Сгруппировать коллекцию по услуге, организации
	 * @param lst - исходная коллекция
	 * @param uslId - услуга
	 * @param orgId - организация
	 * @param summa - сумма
	 */
	@Override
	public void groupByUslOrg(List<SumUslOrgDTO> lst, String uslId, Integer orgId, BigDecimal summa) {
		SumUslOrgDTO foundElem = lst.stream().filter(t -> t.getUslId().equals(uslId) && t.getOrgId().equals(orgId))
				.findFirst().orElse(null);
		if (foundElem != null) {
			foundElem.setBdForDist(foundElem.getBdForDist().add(summa));
		} else {
			lst.add(new SumUslOrgDTO(uslId, orgId, summa));
		}
	}

}