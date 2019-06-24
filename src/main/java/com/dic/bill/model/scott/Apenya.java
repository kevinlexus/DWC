package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Пеня
 * @author lev
 * @version 1.2
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_PENYA", schema="SCOTT")
@Getter @Setter
public class Apenya implements java.io.Serializable {

	public Apenya() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_A_PENYA_ID")
	@SequenceGenerator(name = "SEQ_A_PENYA_ID", sequenceName = "SCOTT.A_PENYA_ID", allocationSize = 1)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

    @Column(name = "summa", updatable = false, nullable = false)
	private BigDecimal summa; // задолженность

    @Column(name = "penya", updatable = false, nullable = false)
	private BigDecimal penya; // пеня

	@Column(name = "mg1", updatable = false, nullable = false)
	private String mg1; // период задолженности

    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // архивный период

}

