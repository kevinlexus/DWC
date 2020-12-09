package com.dic.bill.dto;

import java.math.BigDecimal;

/*
 * Projection для хранения записи финансовой операции (входящии записи по долгу)
 * @author - Lev
 * @ver 1.00
 */
public interface SumDebPenRec {

	// долг
	BigDecimal getDebOut();
	// период
	Integer getMg();
}
