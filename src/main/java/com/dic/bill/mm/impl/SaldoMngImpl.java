package com.dic.bill.mm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.mm.SaldoMng;

@Service
public class SaldoMngImpl implements SaldoMng {

	@Autowired
	SaldoUslDAO saldoUslDAO;
	@Autowired
	ChargePayDAO chargePayDAO;

	/**
	 * Распределить сальдо по периодам задолжности
	 */
	public void distSalByChPay() {
		String lsk = null;
		chargePayDAO.getAllOrd().stream().forEach(t-> {
			
			if (!t.getLsk().equals(lsk)) {
				// Новый лиц.счет
				String period = t.getPeriod();
			} else {
				// Продолжить распределение старого
				
			}
			
			//saldoUslDAO.get
			
		});
	}


}