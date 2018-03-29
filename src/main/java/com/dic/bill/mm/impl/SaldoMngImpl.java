package com.dic.bill.mm.impl;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.ChargePayDAO;
import com.dic.bill.dao.SaldoUslDAO;
import com.dic.bill.mm.SaldoMng;

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
	public void distSalByChPay() {
		log.info("Начало распределения сальдо!");
		String lsk = null;
		chargePayDAO.getAllOrd().stream().forEach(t-> {
			
			if (!t.getLsk().equals(lsk)) {
				// Новый лиц.счет
				String period = t.getPeriod();
				log.info("Новый лиц.счет={}", t.getLsk());
				//System.out.println("############################## CHECK2="+t.getLsk());
			} else {
				// Продолжить распределение старого
				
			}
			
			//saldoUslDAO.get
			
		});
		log.info("Окончание распределения сальдо!");

	}


}