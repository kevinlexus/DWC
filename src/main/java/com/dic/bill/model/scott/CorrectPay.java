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
}

