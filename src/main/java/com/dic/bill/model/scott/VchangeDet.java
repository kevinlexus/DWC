package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Перерасчет сгруппированный из View
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "V_CHANGES_FOR_SALDO_DET", schema="SCOTT")
@Getter @Setter
@IdClass(VchangeDetId.class) // суррогатный первичный ключ
public class VchangeDet implements java.io.Serializable  {

	public VchangeDet() {
	}

	// лиц.счет
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// период за который перерасчет
	@Id
	@Column(name = "MGCHANGE", updatable = false)
	private String mgchange;

	// Дата перерасчета
	@Id
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// услуга
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

}

