package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Корректировки по пене
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_PEN_CORR", schema="SCOTT")
@Getter @Setter
@NoArgsConstructor
public class PenCorr implements java.io.Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEN_CORR")
	@SequenceGenerator(name = "SEQ_PEN_CORR", sequenceName = "scott.c_pen_corr_id", allocationSize = 1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// пеня
    @Column(name = "PENYA", updatable = false, nullable = false)
	private BigDecimal penya;

	// период задолженности
    @Column(name = "DOPL", updatable = false, nullable = false)
	private String dopl;

	// дата
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USL", referencedColumnName = "USl", updatable = false, nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG", referencedColumnName = "ID", updatable = false, nullable = false)
	private Org org;

	// документ корректировки
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_DOC", referencedColumnName="ID")
	private ChangeDoc changeDoc;
}

