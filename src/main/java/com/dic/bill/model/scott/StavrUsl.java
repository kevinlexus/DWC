package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Ставки рефинансирования по услугам
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "STAV_R_USl", schema="SCOTT")
@Getter @Setter
public class StavrUsl {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// доля  от ставки рефинансирования
    @Column(name = "PROC", updatable = false, nullable = true)
    private BigDecimal proc;

	// кол-во дней просрочки распространяется С
    @Column(name = "DAYS1", updatable = false, nullable = true)
    private Integer days1;

	// кол-во дней просрочки распространяется По
    @Column(name = "DAYS2", updatable = false, nullable = true)
    private Integer days2;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USL")
	private Usl usl;

    // дата начала
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

	// дата окончания
    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;

}