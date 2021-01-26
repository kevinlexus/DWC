package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
	@JoinColumn(name = "USL", referencedColumnName = "USl", nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG", referencedColumnName = "ID", nullable = false)
	private Org org;

	// бухгалтерский период
    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg;

    // сумма
    @Column(name = "summa", updatable = false, nullable = false)
	private BigDecimal summa;

}

