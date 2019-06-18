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

	// Id услуги
	String uslId;
	// Id организации
	Integer orgId;
	// сумма для расчета долга
	BigDecimal debOut;
	// период
	Integer mg;

	@Generated("SparkTools")
	private SumPenRec(Builder builder) {
		this.uslId = builder.uslId;
		this.orgId = builder.orgId;
		this.debOut = builder.debOut;
		this.mg = builder.mg;
	}

	public SumPenRec(BigDecimal debIn, BigDecimal penyaPay, BigDecimal payCorr, BigDecimal debPay,
			BigDecimal chrg, BigDecimal chng, String uslId, Integer orgId, BigDecimal debOut,
			BigDecimal debRolled,
			BigDecimal penyaIn, BigDecimal penyaCorr, Integer days, Integer mg) {
		super();

		if (debOut == null) {
			this.debOut = BigDecimal.ZERO;
		} else {
			this.debOut = debOut;
		}

		this.uslId = uslId;
		this.orgId = orgId;
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
		private String uslId;
		private Integer orgId;
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

		public Builder withLog(org.slf4j.Logger log) {
			return this;
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

		public Builder withUslId(String uslId) {
			this.uslId = uslId;
			return this;
		}

		public Builder withOrgId(Integer orgId) {
			this.orgId = orgId;
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
