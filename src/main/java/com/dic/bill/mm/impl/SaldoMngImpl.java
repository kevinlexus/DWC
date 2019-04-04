package com.dic.bill.mm.impl;

import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.model.scott.Kart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.mm.SaldoMng;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class SaldoMngImpl implements SaldoMng {

	@Autowired
	SaldoUslDAO saldoUslDAO;
	@Autowired
	ChargePayDAO chargePayDAO;

	/**
	 * Распределить сальдо по периодам задолженности
	 */
	@Override
	public void distSalByChPay() {
		log.info("Начало распределения сальдо!");
		String lsk = null;
		chargePayDAO.getAllOrd().stream().forEach(t-> {

			if (!t.getKart().getLsk().equals(lsk)) {
				// Новый лиц.счет
				String period = t.getPeriod();
				log.info("Новый лиц.счет={}", t.getKart().getLsk());
			} else {
				// Продолжить распределение старого
			}
		});
		log.info("Окончание распределения сальдо!");

	}


	/**
	 * Получить исходящее сальдо, учитывая разные финансовые составляющие:
	 * @param kart - лиц.счет
	 * @param period - текущий период
	 * @param isSalIn - учесть входящее сальдо
	 * @param isChrg - учесть начисление
	 * @param isChng - учесть перерасчеты
	 * @param isCorrPay - учесть корректировки оплаты
	 * @param isPay - учесть оплату
	 * @return
	 */
	public List<SumUslOrgRec> getOutSal(Kart kart, String period, boolean isSalIn, boolean isChrg, boolean isChng,
										boolean isCorrPay, boolean isPay) {

		List<SumUslOrgRec> lstSalIn = saldoUslDAO.getSaldoUslByLsk(kart.getLsk(), period);
		return lstSalIn;
	}


}