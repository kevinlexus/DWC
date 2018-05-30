package com.dic.bill.dto;

import java.math.BigDecimal;

import javax.annotation.Generated;

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
	Integer days = 0;
	// период
	Integer mg;

	@Generated("SparkTools")
	private SumPenRec(Builder builder) {
		this.chrg = builder.chrg;
		this.chng = builder.chng;
		this.debPay = builder.debPay;
		this.debIn = builder.debIn;
		this.payCorr = builder.payCorr;
		this.uslOrg = builder.uslOrg;
		this.debOut = builder.debOut;
		this.debRolled = builder.debRolled;
		this.penyaIn = builder.penyaIn;
		this.penyaChrg = builder.penyaChrg;
		this.penyaPay = builder.penyaPay;
		this.penyaCorr = builder.penyaCorr;
		this.days = builder.days;
		this.mg = builder.mg;
	}

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

	/**
	 * Creates builder to build {@link SumPenRec}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link SumPenRec}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private BigDecimal chrg;
		private BigDecimal chng;
		private BigDecimal debPay;
		private BigDecimal debIn;
		private BigDecimal payCorr;
		private UslOrg uslOrg;
		private BigDecimal debOut;
		private BigDecimal debRolled;
		private BigDecimal penyaIn;
		private BigDecimal penyaChrg;
		private BigDecimal penyaPay;
		private BigDecimal penyaCorr;
		private Integer days;
		private Integer mg;

		private Builder() {
		}

		public Builder withChrg(BigDecimal chrg) {
			this.chrg = chrg;
			return this;
		}

		public Builder withChng(BigDecimal chng) {
			this.chng = chng;
			return this;
		}

		public Builder withDebPay(BigDecimal debPay) {
			this.debPay = debPay;
			return this;
		}

		public Builder withDebIn(BigDecimal debIn) {
			this.debIn = debIn;
			return this;
		}

		public Builder withPayCorr(BigDecimal payCorr) {
			this.payCorr = payCorr;
			return this;
		}

		public Builder withUslOrg(UslOrg uslOrg) {
			this.uslOrg = uslOrg;
			return this;
		}

		public Builder withDebOut(BigDecimal debOut) {
			this.debOut = debOut;
			return this;
		}

		public Builder withDebRolled(BigDecimal debRolled) {
			this.debRolled = debRolled;
			return this;
		}

		public Builder withPenyaIn(BigDecimal penyaIn) {
			this.penyaIn = penyaIn;
			return this;
		}

		public Builder withPenyaChrg(BigDecimal penyaChrg) {
			this.penyaChrg = penyaChrg;
			return this;
		}

		public Builder withPenyaPay(BigDecimal penyaPay) {
			this.penyaPay = penyaPay;
			return this;
		}

		public Builder withPenyaCorr(BigDecimal penyaCorr) {
			this.penyaCorr = penyaCorr;
			return this;
		}

		public Builder withDays(Integer days) {
			this.days = days;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public SumPenRec build() {
			return new SumPenRec(this);
		}
	}

}
