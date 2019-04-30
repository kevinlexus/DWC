package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Корректировки оплатой
 * @author lev
 * @version 1.01
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CORRECTS_PAYMENTS", schema="SCOTT")
@Getter @Setter
public class CorrectPay implements java.io.Serializable  {

	public CorrectPay() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CorrectPay_ID")
	@SequenceGenerator(name = "SEQ_CorrectPay_ID", sequenceName = "SCOTT.T_CORRECTS_PAYMENTS_ID", allocationSize = 1)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false, nullable = false)
	private Kart kart;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// дата
	@Column(name = "DAT", updatable = false, nullable = false)
	private Date dt;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

	// период оплаты
	@Column(name = "DOPL", updatable = false, nullable = false)
	private String dopl;

	// бухгалтерский период
	@Column(name = "MG", updatable = false, nullable = false)
	private String mg;

	// вариант обработки
	@Column(name = "VAR", updatable = false, nullable = false)
	private Integer var;

	// пользователь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", updatable = false, nullable = false)
	private Tuser user;

	// документ перерасчета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_DOC", referencedColumnName="ID")
	private ChangeDoc changeDoc;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CorrectPay that = (CorrectPay) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public static final class CorrectPayBuilder {
		// лиц.счет
		private Kart kart;
		// сумма
		private BigDecimal summa;
		// дата
		private Date dt;
		// услуга
		private Usl usl;
		// организация
		private Org org;
		// период оплаты
		private String dopl;
		// бухгалтерский период
		private String mg;
		// вариант обработки
		private Integer var;
		// пользователь
		private Tuser user;
		// документ перерасчета
		private ChangeDoc changeDoc;

		private CorrectPayBuilder() {
		}

		public static CorrectPayBuilder aCorrectPay() {
			return new CorrectPayBuilder();
		}

		public CorrectPayBuilder withKart(Kart kart) {
			this.kart = kart;
			return this;
		}

		public CorrectPayBuilder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public CorrectPayBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public CorrectPayBuilder withUsl(Usl usl) {
			this.usl = usl;
			return this;
		}

		public CorrectPayBuilder withOrg(Org org) {
			this.org = org;
			return this;
		}

		public CorrectPayBuilder withDopl(String dopl) {
			this.dopl = dopl;
			return this;
		}

		public CorrectPayBuilder withMg(String mg) {
			this.mg = mg;
			return this;
		}

		public CorrectPayBuilder withVar(Integer var) {
			this.var = var;
			return this;
		}

		public CorrectPayBuilder withUser(Tuser user) {
			this.user = user;
			return this;
		}

		public CorrectPayBuilder withChangeDoc(ChangeDoc changeDoc) {
			this.changeDoc = changeDoc;
			return this;
		}

		public CorrectPay build() {
			CorrectPay correctPay = new CorrectPay();
			correctPay.setKart(kart);
			correctPay.setSumma(summa);
			correctPay.setDt(dt);
			correctPay.setUsl(usl);
			correctPay.setOrg(org);
			correctPay.setDopl(dopl);
			correctPay.setMg(mg);
			correctPay.setVar(var);
			correctPay.setUser(user);
			correctPay.setChangeDoc(changeDoc);
			return correctPay;
		}
	}
}

