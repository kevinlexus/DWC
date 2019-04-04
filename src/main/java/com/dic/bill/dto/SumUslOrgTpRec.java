package com.dic.bill.dto;

import java.math.BigDecimal;

/*
 * Projection для хранения записи финансовой операции
 * Здесь используется Spring Interface-based Projection: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.dtos
 * @author - Lev
 * @ver 1.01
 */
public interface SumUslOrgTpRec extends SumUslOrgRec {

	// тип финансового потока
	// 0-вх.сальдо, 1-начисление, 2-перерасчет, 3-оплата, 4-корректировка оплаты
	Integer getTp();
}
