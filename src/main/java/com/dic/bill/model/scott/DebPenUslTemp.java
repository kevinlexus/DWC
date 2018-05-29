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
 * Задолженность по организациям - услугам - периодам - для отчета
 * @author lev
 * @version 1.00
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "C_DEBPEN_USL_TEMP", schema="SCOTT")
public class DebPenUslTemp implements java.io.Serializable{

	public DebPenUslTemp() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEBPEN_USL_TEMP")
	@SequenceGenerator(name="SEQ_DEBPEN_USL_TEMP", sequenceName="SCOTT.C_DEBPEN_USL_TEMP_ID", allocationSize=10)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;

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

    // входящее сальдо по пене
    @Column(name = "PENIN", updatable = false, nullable = false)
	private BigDecimal penIn;

    // исходящее сальдо по пене
    @Column(name = "PENOUT", updatable = false, nullable = false)
	private BigDecimal penOut;

    // пеня начисленная в текущем периоде (в т.ч. корректировки пени)
    @Column(name = "PENCHRG", updatable = false, nullable = false)
	private BigDecimal penChrg;

    // корректировки начисления пени
    @Column(name = "PENCORR", updatable = false, nullable = false)
	private BigDecimal penCorr;

    // пеня оплаченная
    @Column(name = "PENPAY", updatable = false, nullable = false)
	private BigDecimal penPay;

    // дней просрочки
    @Column(name = "DAYS", updatable = false, nullable = false)
	private Integer days;

    // период задолжности
    @Column(name = "MG", updatable = false, nullable = false)
	private Integer mg;

    // сессия клиента, устанавливается в UTILS.prep_users_tree
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SESSION", referencedColumnName="ID", updatable = false, nullable = false)
	private SessionDirect sessionDirect;

	@Generated("SparkTools")
	private DebPenUslTemp(Builder builder) {
		this.id = builder.id;
		this.usl = builder.usl;
		this.org = builder.org;
		this.debIn = builder.debIn;
		this.debOut = builder.debOut;
		this.debRolled = builder.debRolled;
		this.chrg = builder.chrg;
		this.chng = builder.chng;
		this.debPay = builder.debPay;
		this.payCorr = builder.payCorr;
		this.penIn = builder.penIn;
		this.penOut = builder.penOut;
		this.penChrg = builder.penChrg;
		this.penCorr = builder.penCorr;
		this.penPay = builder.penPay;
		this.days = builder.days;
		this.mg = builder.mg;
		this.sessionDirect = builder.sessionDirect;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof DebPenUslTemp))
	        return false;

	    DebPenUslTemp other = (DebPenUslTemp)o;

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
	 * Creates builder to build {@link DebPenUslTemp}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link DebPenUslTemp}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Usl usl;
		private Org org;
		private BigDecimal debIn;
		private BigDecimal debOut;
		private BigDecimal debRolled;
		private BigDecimal chrg;
		private BigDecimal chng;
		private BigDecimal debPay;
		private BigDecimal payCorr;
		private BigDecimal penIn;
		private BigDecimal penOut;
		private BigDecimal penChrg;
		private BigDecimal penCorr;
		private BigDecimal penPay;
		private Integer days;
		private Integer mg;
		private SessionDirect sessionDirect;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
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

		public Builder withPenIn(BigDecimal penIn) {
			this.penIn = penIn;
			return this;
		}

		public Builder withPenOut(BigDecimal penOut) {
			this.penOut = penOut;
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

		public Builder withDays(Integer days) {
			this.days = days;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public Builder withSessionDirect(SessionDirect sessionDirect) {
			this.sessionDirect = sessionDirect;
			return this;
		}

		public DebPenUslTemp build() {
			return new DebPenUslTemp(this);
		}
	}


}

