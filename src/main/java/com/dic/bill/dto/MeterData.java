package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи показания счетчика
 * @author - Lev
 * @ver 1.00
 */
public interface MeterData {

	// timestamp
	Date getTs();
	// GUID счетчика
	String getGuid();
}
