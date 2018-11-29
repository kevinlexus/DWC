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
 * Ставки рефинансирования по типам услуг
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "PEN_REF", schema="SCOTT")
@Getter @Setter
public class PenRef {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// % начисления пени
    @Column(name = "PROC", updatable = false, nullable = true)
    private BigDecimal proc;

	// кол-во дней просрочки распространяется С
    @Column(name = "DAYS1", updatable = false, nullable = true)
    private Integer days1;

	// кол-во дней просрочки распространяется По
    @Column(name = "DAYS2", updatable = false, nullable = true)
    private Integer days2;

	// тип услуги -> USL.TP_REF (0-обычная услуга, 1- капремонт)
    @Column(name = "USL_TP_REF", updatable = false, nullable = true)
    private Integer uslTpRef;

    // дата начала
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

	// дата окончания
    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;

}