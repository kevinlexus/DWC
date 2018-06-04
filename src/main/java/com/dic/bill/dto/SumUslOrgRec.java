package com.dic.bill.dto;

import java.math.BigDecimal;

/*
 * DTO для хранения записи финансовой операции
 * @author - Lev
 * @ver 1.01
 */
public interface SumUslOrgRec {

	// Id услуги
	String getUslId();
	// Id организации
	Integer getOrgId();
	// прочие суммы по операциям
	BigDecimal getSumma();
}
