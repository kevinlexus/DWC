package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Исходящее сальдо по пене
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_PENYA", schema="SCOTT")
@Getter @Setter
public class Penya implements java.io.Serializable {

	public Penya() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PENYA")
	@SequenceGenerator(name = "SEQ_PENYA", sequenceName = "SCOTT.C_PENYA_ID", allocationSize = 1)
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", nullable = false)
	private Kart kart;

	// долг
    @Column(name = "SUMMA")
	private BigDecimal summa;

    // пеня
    @Column(name = "PENYA")
	private BigDecimal penya;

	// кол-во дней долга
    @Column(name = "DAYS")
	private Integer days;

	// период долга
    @Column(name = "MG1")
	private String mg1;

}

