package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Пеня по организациям - услугам - периодам - для отчета
 * @author lev
 * @version 1.00
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "PEN", schema="SCOTT")
public class Pen implements java.io.Serializable{

	public Pen() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEN")
	@SequenceGenerator(name="SEQ_PEN", sequenceName="SCOTT.PEN_ID", allocationSize=10)
    @Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

    // входящее сальдо по пене
    @Column(name = "PENIN", updatable = false, nullable = false)
	private BigDecimal penIn;

    // пеня начисленная в текущем периоде (в т.ч. корректировки пени)
    @Column(name = "PENCHRG", updatable = false, nullable = false)
	private BigDecimal penChrg;

    // корректировки начисления пени
    @Column(name = "PENCORR", updatable = false, nullable = false)
	private BigDecimal penCorr;

    // пеня оплаченная
    @Column(name = "PENPAY", updatable = false, nullable = false)
	private BigDecimal penPay;

    // исходящее сальдо по пене
    @Column(name = "PENOUT", updatable = false, nullable = false)
	private BigDecimal penOut;

    // дней просрочки
    @Column(name = "DAYS", updatable = false, nullable = false)
	private Integer days;

    // период задолжности
    @Column(name = "MG", updatable = false, nullable = false)
	private Integer mg;

    // бухгалтерский период - начало
    @Column(name = "MGFROM", updatable = false, nullable = false)
	private Integer mgFrom;

    // бухгалтерский период - окончание
    @Column(name = "MGTO", updatable = false, nullable = false)
	private Integer mgTo;

	@Generated("SparkTools")
	private Pen(Builder builder) {
		this.id = builder.id;
		this.kart = builder.kart;
		this.usl = builder.usl;
		this.org = builder.org;
		this.penIn = builder.penIn;
		this.penChrg = builder.penChrg;
		this.penCorr = builder.penCorr;
		this.penPay = builder.penPay;
		this.penOut = builder.penOut;
		this.days = builder.days;
		this.mg = builder.mg;
		this.mgFrom = builder.mgFrom;
		this.mgTo = builder.mgTo;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Pen))
	        return false;

	    Pen other = (Pen)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	/**
	 * Creates builder to build {@link Pen}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Pen}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private Kart kart;
		private Usl usl;
		private Org org;
		private BigDecimal penIn;
		private BigDecimal penChrg;
		private BigDecimal penCorr;
		private BigDecimal penPay;
		private BigDecimal penOut;
		private Integer days;
		private Integer mg;
		private Integer mgFrom;
		private Integer mgTo;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withKart(Kart kart) {
			this.kart = kart;
			return this;
		}

		public Builder withUsl(Usl usl) {
			this.usl = usl;
			return this;
		}

		public Builder withOrg(Org org) {
			this.org = org;
			return this;
		}

		public Builder withPenIn(BigDecimal penIn) {
			this.penIn = penIn;
			return this;
		}

		public Builder withPenChrg(BigDecimal penChrg) {
			this.penChrg = penChrg;
			return this;
		}

		public Builder withPenCorr(BigDecimal penCorr) {
			this.penCorr = penCorr;
			return this;
		}

		public Builder withPenPay(BigDecimal penPay) {
			this.penPay = penPay;
			return this;
		}

		public Builder withPenOut(BigDecimal penOut) {
			this.penOut = penOut;
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

		public Builder withMgFrom(Integer mgFrom) {
			this.mgFrom = mgFrom;
			return this;
		}

		public Builder withMgTo(Integer mgTo) {
			this.mgTo = mgTo;
			return this;
		}

		public Pen build() {
			return new Pen(this);
		}
	}


}

