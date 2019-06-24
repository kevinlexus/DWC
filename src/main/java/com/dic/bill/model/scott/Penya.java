package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

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
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = true, nullable = false, insertable = true)
	private Kart kart;

	// долг
    @Column(name = "SUMMA", updatable = false, nullable = false)
	private BigDecimal summa;

    // пеня
    @Column(name = "PENYA", updatable = false, nullable = false)
	private BigDecimal penya;

	// кол-во дней долга
    @Column(name = "DAYS", updatable = false, nullable = false)
	private Integer days;

	// период долга
    @Column(name = "MG1", updatable = false, nullable = false)
	private String mg1;

}

