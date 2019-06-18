package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи финансовой операции (входящии записи по долгу)
 * @author - Lev
 * @ver 1.00
 */
public interface SumDebPenRec {

	// Id записи
	Long getId();
	// Id услуги
	String getUslId();
	// Id организации
	Integer getOrgId();
	// cумма задолженности (исходящее сальдо по задолженности)
	BigDecimal getDebOut();
	// период
	Integer getMg();
	// дата операции
	Date getDt();
}
