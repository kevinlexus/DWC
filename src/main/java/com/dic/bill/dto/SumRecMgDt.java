package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи финансовой операции
 * @author - Lev
 * @ver 1.00
 */
public interface SumRecMgDt {

	// период
	Integer getMg();
	// прочие суммы по операциям
	BigDecimal getSumma();
	// дата операции
	Date getDt();
}
