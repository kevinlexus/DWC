package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/*
 * DTO для хранения записи финансовой операции (долг, оплата и т.п.)
 * @author - Lev
 * @ver 1.2
 */
@Slf4j
@Getter @Setter
public class SumDebRec {

	// дата
	Date dt;
	// флаг последней даты расчета (используется в DebitMngImpl, при группировке записей)
	Boolean isLastDay;
	// Id услуги
	String uslId;
	// Id организации
	Integer orgId;
	// начисление
	BigDecimal chrg;
	// перерасчеты
	BigDecimal chng;
	// оплата задолженности
	BigDecimal debPay;
	// входящее сальдо по задолженности
	BigDecimal debIn;
	// корректировки оплаты
	BigDecimal payCorr;
	// cумма задолженности (исходящее сальдо по задолженности) (включая поступления текущего дня)
	BigDecimal debOut;
	// свернутый долг. сумма задолженности, для расчета пени (не включая поступления текущего дня) (будут учтены переплаты ранних периодов)
	BigDecimal debRolled;
	// период
	Integer mg;
	// вспомогательный тип записи
	Integer tp;

	/**
	 * конструктор
	 * @param debOut - сумма свернутого долга
	 * @param mg - период
	 * @param tp - вспомогательный тип записи
	 */
	public SumDebRec(BigDecimal debIn, BigDecimal payCorr, BigDecimal debPay, BigDecimal chrg,
					 BigDecimal chng, BigDecimal debOut,
					 Integer mg, Integer tp) {
		super();

		if (debIn == null) {
			this.debIn = BigDecimal.ZERO;
		} else {
			this.debIn = debIn;
		}

		if (payCorr == null) {
			this.payCorr = BigDecimal.ZERO;
		} else {
			this.payCorr = payCorr;
		}

		if (debPay == null) {
			this.debPay = BigDecimal.ZERO;
		} else {
			this.debPay = debPay;
		}

		if (chrg == null) {
			this.chrg = BigDecimal.ZERO;
		} else {
			this.chrg = chrg;
		}

		if (chng == null) {
			this.chng = BigDecimal.ZERO;
		} else {
			this.chng = chng;
		}

		if (debOut == null) {
			this.debOut = BigDecimal.ZERO;
			this.debRolled = BigDecimal.ZERO;
		} else {
			this.debOut = debOut;
			this.debRolled = debOut;
		}
		this.mg = mg;
		this.tp = tp;
	}

}
