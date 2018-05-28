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
@Table(name = "C_DEBPEN_USL", schema="SCOTT")
public class DebPenUsl implements java.io.Serializable{

	public DebPenUsl() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEBPEN_USL")
	@SequenceGenerator(name="SEQ_DEBPEN_USL", sequenceName="SCOTT.C_DEBPEN_USL_ID", allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;

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

	 // сумма задолженности (исходящее сальдо по задолженности)
    @Column(name = "DEBOUT", updatable = false, nullable = false)
	private BigDecimal debOut;

    // задолженность по пене
    @Column(name = "PENOUT", updatable = false, nullable = false)
	private BigDecimal penOut;

    // период
    @Column(name = "MG", updatable = false, nullable = false)
	private Integer mg;

    // период бухгалтерский
    @Column(name = "PERIOD", updatable = false, nullable = false)
	private Integer period;

	@Generated("SparkTools")
	private DebPenUsl(Builder builder) {
		this.id = builder.id;
		this.kart = builder.kart;
		this.usl = builder.usl;
		this.org = builder.org;
		this.debOut = builder.debOut;
		this.penOut = builder.penOut;
		this.mg = builder.mg;
		this.period = builder.period;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof DebPenUsl))
	        return false;

	    DebPenUsl other = (DebPenUsl)o;

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
	 * Creates builder to build {@link DebPenUsl}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link DebPenUsl}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Kart kart;
		private Usl usl;
		private Org org;
		private BigDecimal debOut;
		private BigDecimal penOut;
		private Integer mg;
		private Integer period;

		private Builder() {
		}

		public Builder withId(Integer id) {
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

		public Builder withDebOut(BigDecimal debOut) {
			this.debOut = debOut;
			return this;
		}

		public Builder withPenOut(BigDecimal penOut) {
			this.penOut = penOut;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public Builder withPeriod(Integer period) {
			this.period = period;
			return this;
		}

		public DebPenUsl build() {
			return new DebPenUsl(this);
		}
	}


}

