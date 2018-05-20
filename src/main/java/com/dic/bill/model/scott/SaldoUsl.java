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

/**
 * Сальдо по организациям - услугам
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SALDO_USL", schema="SCOTT")
@IdClass(SaldoUslId.class) // суррогантый первичный ключ
@Getter @Setter
public class SaldoUsl implements java.io.Serializable {

	public SaldoUsl() {
	}

    @Id
	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

    @Id
	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код услуги

    @Id
    @Column(name = "org", updatable = false, nullable = false)
	private Integer org; // код организации

    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период

    @Id
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма

}

