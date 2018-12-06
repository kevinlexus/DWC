package com.dic.bill.dto;

import java.math.BigDecimal;

/*
 * Projection для хранения записи финансовой операции
 * @author - Lev
 * @ver 1.01
 */
public interface SumRecMg {

	// период
	Integer getMg();
	// прочие суммы по операциям
	BigDecimal getSumma();
}
