package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Распределение платежа по услугам, организациям
 * @author lev
 * @version 1.02
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KWTP_DAY", schema="SCOTT")
@Getter @Setter
public class KwtpDay implements java.io.Serializable  {

	public KwtpDay() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KWTP_DAY_ID")
	@SequenceGenerator(name="SEQ_KWTP_DAY_ID", sequenceName="SCOTT.KWTP_DAY_ID", allocationSize=1)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LSK", referencedColumnName = "LSK")
	private Kart kart;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// дата принятия платежа
	@Column(name = "DTEK", updatable = false, nullable = false)
	private Date dt;

	// дата инкассации
	@Column(name = "DAT_INK", updatable = false)
	private Date dtInk;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

	// № компьютера
	@Column(name = "NKOM", updatable = false)
	private String nkom;

	// № инкассации
	@Column(name = "NINK", updatable = false)
	private Integer nink;

	// тип поступления 1 - оплата, 0 - пеня
	@Column(name = "PRIZNAK", updatable = false, nullable = false)
	private Integer tp;

	// период оплаты
	@Column(name = "DOPL", updatable = false, nullable = false)
	private String dopl;

	// код операции
	@Column(name = "OPER", updatable = false)
	private String oper;

	// распределение платежа по периоду
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="KWTP_ID", referencedColumnName="ID")
	private KwtpMg kwtpMg;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KwtpDay kwtpDay = (KwtpDay) o;
		return Objects.equals(getId(), kwtpDay.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}


	public static final class KwtpDayBuilder {
		private Integer id;
		// лиц.счет
		private Kart kart;
		// сумма
		private BigDecimal summa;
		// дата принятия платежа
		private Date dt;
		// дата инкассации
		private Date dtInk;
		// услуга
		private Usl usl;
		// организация
		private Org org;
		// № компьютера
		private String nkom;
		// № инкассации
		private Integer nink;
		// тип поступления 1 - оплата, 0 - пеня
		private Integer tp;
		// период оплаты
		private String dopl;
		// код операции
		private String oper;
		// распределение платежа по периоду
		private KwtpMg kwtpMg;

		private KwtpDayBuilder() {
		}

		public static KwtpDayBuilder aKwtpDay() {
			return new KwtpDayBuilder();
		}

		public KwtpDayBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public KwtpDayBuilder withKart(Kart kart) {
			this.kart = kart;
			return this;
		}

		public KwtpDayBuilder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public KwtpDayBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public KwtpDayBuilder withDtInk(Date dtInk) {
			this.dtInk = dtInk;
			return this;
		}

		public KwtpDayBuilder withUsl(Usl usl) {
			this.usl = usl;
			return this;
		}

		public KwtpDayBuilder withOrg(Org org) {
			this.org = org;
			return this;
		}

		public KwtpDayBuilder withNkom(String nkom) {
			this.nkom = nkom;
			return this;
		}

		public KwtpDayBuilder withNink(Integer nink) {
			this.nink = nink;
			return this;
		}

		public KwtpDayBuilder withTp(Integer tp) {
			this.tp = tp;
			return this;
		}

		public KwtpDayBuilder withDopl(String dopl) {
			this.dopl = dopl;
			return this;
		}

		public KwtpDayBuilder withOper(String oper) {
			this.oper = oper;
			return this;
		}

		public KwtpDayBuilder withKwtpMg(KwtpMg kwtpMg) {
			this.kwtpMg = kwtpMg;
			return this;
		}

		public KwtpDayBuilder but() {
			return aKwtpDay().withId(id).withKart(kart).withSumma(summa).withDt(dt).withDtInk(dtInk).withUsl(usl).withOrg(org).withNkom(nkom).withNink(nink).withTp(tp).withDopl(dopl).withOper(oper).withKwtpMg(kwtpMg);
		}

		public KwtpDay build() {
			KwtpDay kwtpDay = new KwtpDay();
			kwtpDay.setId(id);
			kwtpDay.setKart(kart);
			kwtpDay.setSumma(summa);
			kwtpDay.setDt(dt);
			kwtpDay.setDtInk(dtInk);
			kwtpDay.setUsl(usl);
			kwtpDay.setOrg(org);
			kwtpDay.setNkom(nkom);
			kwtpDay.setNink(nink);
			kwtpDay.setTp(tp);
			kwtpDay.setDopl(dopl);
			kwtpDay.setOper(oper);
			kwtpDay.setKwtpMg(kwtpMg);
			return kwtpDay;
		}
	}
}

