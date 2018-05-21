package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * DTO для хранения записи финансовой операции (долг, оплата и т.п.)
 * @author - Lev
 * @ver 1.00
 */
public interface SumRec {

	// Id услуги
	String getUslId();
	// Id организации
	Integer getOrgId();
	// долг
	BigDecimal getSumma();
	// пеня
	BigDecimal getPenya();
	// период
	String getMg();
	// дата операции
	Date getDt();
	// тип записи
	Integer getTp();
}
