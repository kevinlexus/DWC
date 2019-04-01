package com.dic.bill.model.scott;

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

import java.math.BigDecimal;

/**
 * Сальдо по организациям - услугам
 * @author lev
 *
 */
@Entity
@Table(name = "SALDO_USL", schema="SCOTT")
@IdClass(SaldoUslId.class) // суррогатный первичный ключ
@Getter @Setter
public class SaldoUsl implements java.io.Serializable {

	public SaldoUsl() {
	}

	// лиц.счет
    @Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USL", referencedColumnName = "USl", updatable = false, nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG", referencedColumnName = "ID", updatable = false, nullable = false)
	private Org org;

	// бухгалтерский период
    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg;

    // сумма
    @Column(name = "summa", updatable = false, nullable = false)
	private BigDecimal summa;

}

