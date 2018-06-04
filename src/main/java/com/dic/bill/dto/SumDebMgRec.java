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
public class SumDebMgRec {

	// сумма
	BigDecimal summa;
	// период
	Integer mg;
	// знак, 1 - больше нуля, -1 - меньше нуля, 0 - равно нулю
	Integer sign;

	@Generated("SparkTools")
	private SumDebMgRec(Builder builder) {
		this.summa = builder.summa;
		this.mg = builder.mg;
		this.sign = builder.sign;
	}

	/**
	 * Creates builder to build {@link SumDebMgRec}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link SumDebMgRec}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private org.slf4j.Logger log;
		private BigDecimal summa;
		private Integer mg;
		private Integer sign;

		private Builder() {
		}

		public Builder withLog(org.slf4j.Logger log) {
			this.log = log;
			return this;
		}

		public Builder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public Builder withSign(Integer sign) {
			this.sign = sign;
			return this;
		}

		public SumDebMgRec build() {
			return new SumDebMgRec(this);
		}
	}


}
