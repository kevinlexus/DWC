package com.dic.bill.dto;

import java.math.BigDecimal;

import javax.annotation.Generated;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/*
 * DTO для хранения упрощенной записи финансовой операции (долг, оплата и т.п.)
 * @author - Lev
 * @ver 1.00
 */
@Slf4j
@Getter @Setter
public class SumDebUslMgRec {

	// Id услуги
	String uslId;
	// Id организации
	Integer orgId;
	// сумма
	BigDecimal summa;
	// вес записи (чем больше, summa, тем больше вес)
	BigDecimal weigth;
	// знак, 1 - больше нуля, -1 - меньше нуля, 0 - равно нулю
	Integer sign;
	// период
	Integer mg;

	@Generated("SparkTools")
	private SumDebUslMgRec(Builder builder) {
		this.uslId = builder.uslId;
		this.orgId = builder.orgId;
		this.summa = builder.summa;
		this.weigth = builder.weigth;
		this.sign = builder.sign;
		this.mg = builder.mg;
	}

	/**
	 * Creates builder to build {@link SumDebUslMgRec}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link SumDebUslMgRec}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private org.slf4j.Logger log;
		private String uslId;
		private Integer orgId;
		private BigDecimal summa;
		private BigDecimal weigth;
		private Integer sign;
		private Integer mg;

		private Builder() {
		}

		public Builder withLog(org.slf4j.Logger log) {
			this.log = log;
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

		public Builder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public Builder withWeigth(BigDecimal weigth) {
			this.weigth = weigth;
			return this;
		}

		public Builder withSign(Integer sign) {
			this.sign = sign;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public SumDebUslMgRec build() {
			return new SumDebUslMgRec(this);
		}
	}


}
