package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Ставки рефинансирования
 * @author Lev
 * @version 1.00
 *
 */
@Entity
@Table(name = "STAV_R", schema="SCOTT")
@Getter @Setter
public class Stavr {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// ставка, коэфф
    @Column(name = "PROC", updatable = false, nullable = true)
    private BigDecimal proc;

	// дата начала
    @Column(name = "DAT1", updatable = false, nullable = true)
    private Date dt1;

	// дата окончания
    @Column(name = "DAT2", updatable = false, nullable = true)
    private Date dt2;

}