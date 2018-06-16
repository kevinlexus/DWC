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
 * Задолженность по организациям - услугам - периодам
 * @author lev
 * @version 1.00
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "DEB", schema="SCOTT")
public class Deb implements java.io.Serializable{

	public Deb() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEB")
	@SequenceGenerator(name="SEQ_DEB", sequenceName="SCOTT.DEB_ID", allocationSize=10)
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

	 // входящее сальдо по задолженности
    @Column(name = "DEBIN", updatable = false, nullable = false)
	private BigDecimal debIn;

    // исходящее сальдо по задолженности
    @Column(name = "DEBOUT", updatable = false, nullable = false)
	private BigDecimal debOut;

    // свернутый долг
    @Column(name = "DEBROLLED", updatable = false, nullable = false)
	private BigDecimal debRolled;

    // начисление
    @Column(name = "CHRG", updatable = false, nullable = false)
	private BigDecimal chrg;

    // перерасчеты
    @Column(name = "CHNG", updatable = false, nullable = false)
	private BigDecimal chng;

    // оплата задолженности
    @Column(name = "DEBPAY", updatable = false, nullable = false)
	private BigDecimal debPay;

    // корректировки оплаты
    @Column(name = "PAYCORR", updatable = false, nullable = false)
	private BigDecimal payCorr;

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
	private Deb(Builder builder) {
		this.id = builder.id;
		this.kart = builder.kart;
		this.usl = builder.usl;
		this.org = builder.org;
		this.debIn = builder.debIn;
		this.debOut = builder.debOut;
		this.debRolled = builder.debRolled;
		this.chrg = builder.chrg;
		this.chng = builder.chng;
		this.debPay = builder.debPay;
		this.payCorr = builder.payCorr;
		this.mg = builder.mg;
		this.mgFrom = builder.mgFrom;
		this.mgTo = builder.mgTo;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Deb))
	        return false;

	    Deb other = (Deb)o;

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
	 * Creates builder to build {@link Deb}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Deb}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Long id;
		private Kart kart;
		private Usl usl;
		private Org org;
		private BigDecimal debIn;
		private BigDecimal debOut;
		private BigDecimal debRolled;
		private BigDecimal chrg;
		private BigDecimal chng;
		private BigDecimal debPay;
		private BigDecimal payCorr;
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

		public Builder withDebIn(BigDecimal debIn) {
			this.debIn = debIn;
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

		public Builder withPayCorr(BigDecimal payCorr) {
			this.payCorr = payCorr;
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

		public Deb build() {
			return new Deb(this);
		}
	}


}

