package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Projection для хранения записи финансовой операции (входящии записи по долгу и пене)
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
	BigDecimal getDebIn();
	// cумма задолженности (исходящее сальдо по задолженности)
	BigDecimal getDebOut();
	// свернутый долг
	BigDecimal getDebRolled();
	// начисление
	BigDecimal getChrg();
    // перерасчеты
	BigDecimal getChng();
    // оплата задолженности
	BigDecimal getDebPay();
	// корректировки оплаты
	BigDecimal getPayCorr();
	// прочие суммы по операциям
	BigDecimal getSumma();
	// период
	Integer getMg();
	// дата операции
	Date getDt();
	// тип записи
	Integer getTp();
}
