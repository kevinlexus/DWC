package com.dic.bill.dto;

import com.dic.bill.model.scott.Meter;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения суммарных объемов по счетчикам
 * @author - Lev
 * @ver 1.00
 */
public interface SumMeterVol {

	// Id счетчика
	Integer getMeterId();
	// объем
	BigDecimal getVol();
}
