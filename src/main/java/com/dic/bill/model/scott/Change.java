package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Перерасчет
 * @author lev
 * @version 1.01
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHANGE", schema="SCOTT")
@Getter @Setter
public class Change implements java.io.Serializable  {

	public Change() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHANGE_ID")
	@SequenceGenerator(name = "SEQ_CHANGE_ID", sequenceName = "SCOTT.CHANGES_ID", allocationSize = 1)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// документ перерасчета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DOC_ID", referencedColumnName="ID")
	private ChangeDoc changeDoc;

	// сумма
	@Column(name = "SUMMA")
	private BigDecimal summa;

	// период за который перерасчет
	@Column(name = "MGCHANGE")
	private String mgchange;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USL", referencedColumnName = "USl", nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG", referencedColumnName = "ID", nullable = false)
	private Org org;

	// пользователь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
	private Tuser user;

	// период, которым надо провести разовые изменения
	// (сделано, чтобы можно было проводить доначисление за прошлый период, не трогая расчёт пени)
	@Column(name = "MG2")
	private String mg2;

	// Дата перерасчета
	@Column(name = "DTEK")
	private Date dt;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Change change = (Change) o;
		return Objects.equals(getId(), change.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}

