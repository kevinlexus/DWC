package com.dic.bill.dto;

import java.math.BigDecimal;

/*
 * Projection для хранения записи финансовой операции
 * Здесь используется Spring Interface-based Projection: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.dtos
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
