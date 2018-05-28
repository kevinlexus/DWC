package com.dic.bill.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/*
 * DTO для хранения записи финансовой операции (долг, оплата и т.п.)
 * @author - Lev
 * @ver 1.00
 */
@Slf4j
@Getter @Setter
public class SumDebRec {

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
	// корректировки начисленной пени
	BigDecimal penChrgCorr;
	// сумма задолженности, для расчета пени
	BigDecimal summa;
	// cумма задолженности (исходящее сальдо по задолженности)
	BigDecimal debOut;
	// сумма для свернутого долга (будут учтены переплаты ранних периодов)
	BigDecimal debRolled;
	// сумма пени (вх.сальдо по пене)
	BigDecimal penyaIn;
	// сумма рассчитанной (текущей пени)
	BigDecimal penyaChrg;
	// сумма оплаты пени
	BigDecimal penyaPay;
	// сумма корректировки начисления пени
	BigDecimal penyaCorr;
	// % по которому рассчитана пеня (информационно)
	BigDecimal proc;
	// кол-во дней просрочки
	Integer days;
	// период
	Integer mg;
	// вспомогательный тип записи
	Integer tp;
	// флаг последней даты расчета (используется в DebitMngImpl, при группировке записей)
	Boolean isLastDay;

	/**
	 * конструктор
	 * @param summa - сумма долга для расчета пени
	 * @param debOut - сумма свернутого долга
	 * @param penyaIn - сумма пени (входящее сальдо по пене)
	 * @param penyaCorr - сумма корректировки пени
	 * @param mg - период
	 * @param tp - вспомогательный тип записи
	 */
	public SumDebRec(BigDecimal debIn, BigDecimal penyaPay, BigDecimal payCorr, BigDecimal debPay, BigDecimal chrg,
			BigDecimal chng, BigDecimal summa, BigDecimal debOut, BigDecimal penyaIn,
			BigDecimal penyaCorr, Integer mg, Integer tp) {
		super();

		if (debIn == null) {
			this.debIn = BigDecimal.ZERO;
		} else {
			this.debIn = debIn;
		}

		if (penyaPay == null) {
			this.penyaPay = BigDecimal.ZERO;
		} else {
			this.penyaPay = penyaPay;
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

		if (summa == null) {
			this.summa = BigDecimal.ZERO;
		} else {
			this.summa = summa;
		}
		if (debOut == null) {
			this.debOut = BigDecimal.ZERO;
			this.debRolled = BigDecimal.ZERO;
		} else {
			this.debOut = debOut;
			this.debRolled = debOut;
		}
		if (penyaIn == null) {
			this.penyaIn = BigDecimal.ZERO;
		} else {
			this.penyaIn = penyaIn;
		}

		if (penyaCorr == null) {
			this.penyaCorr = BigDecimal.ZERO;
		} else {
			this.penyaCorr = penyaCorr;
		}
		this.mg = mg;
		this.tp = tp;
		// нулим, потому что значения появятся при расчете
		this.penyaChrg = BigDecimal.ZERO;
		this.days = 0;
	}

}
