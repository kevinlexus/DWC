package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи финансовой операции (оплата и т.п.)
 * @author - Lev
 * @ver 1.01
 */
public interface SumRec {

	// Id услуги
	String getUslId();
	// Id организации
	Integer getOrgId();
	// cумма задолженности (исходящее сальдо по задолженности)
	BigDecimal getDebOut();
	// задолженность по пене (исходящее сальдо по пене)
	BigDecimal getPenOut();
	// прочие суммы по операциям
	BigDecimal getSumma();
	// период
	Integer getMg();
	// дата операции
	Date getDt();
	// тип записи
	Integer getTp();
}
