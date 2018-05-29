package com.dic.bill.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/*
 * DTO для хранения итоговых записей долга и пени по периоду
 * @author - Lev
 * @ver 1.00
 */
@Slf4j
@Getter @Setter
public class SumPenRec {

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
	// услуга и организация
	UslOrg uslOrg;
	// сумма для расчета долга
	BigDecimal debOut;
	// сумма для свернутого долга (будут учтены переплаты ранних периодов)
	BigDecimal debRolled;
	// сумма пени (вх.сальдо по пене)
	BigDecimal penyaIn;
	// сумма рассчитанной, текущей пени
	BigDecimal penyaChrg;
	// сумма оплаты пени
	BigDecimal penyaPay;
	// сумма корректировки начисления пени
	BigDecimal penyaCorr;
	// кол-во дней просрочки (на расчетную дату пени)
	Integer days;
	// период
	Integer mg;

	public SumPenRec(BigDecimal debIn, BigDecimal penyaPay, BigDecimal payCorr, BigDecimal debPay,
			BigDecimal chrg, BigDecimal chng, UslOrg uslOrg, BigDecimal debOut,
			BigDecimal debRolled,
			BigDecimal penyaIn, BigDecimal penyaCorr, Integer days, Integer mg) {
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
/*		if (this.chng != BigDecimal.ZERO) {
			log.info("**************** usl={}, перерасчет={}",uslOrg.getUslId(), chng);
		}
*/
		if (debOut == null) {
			this.debOut = BigDecimal.ZERO;
		} else {
			this.debOut = debOut;
		}

		if (debRolled == null) {
			this.debRolled = BigDecimal.ZERO;
		} else {
			this.debRolled = debRolled;
		}

		if (penyaIn == null) {
			this.penyaIn = BigDecimal.ZERO;
		} else {
			this.penyaIn = penyaIn;
		}

/*		if (this.penyaIn != BigDecimal.ZERO) {
			log.info("**************** пеня вх={}", penyaIn);
		}
*/


		if (penyaCorr == null) {
			this.penyaCorr = BigDecimal.ZERO;
		} else {
			this.penyaCorr = penyaCorr;
		}

		if (days == null) {
			this.days = 0;
		} else {
			this.days = days;
		}

		this.uslOrg = uslOrg;
		this.penyaChrg = BigDecimal.ZERO;
		this.mg = mg;
	}

}
