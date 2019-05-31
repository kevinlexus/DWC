package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Ставки рефинансирования по типам лиц.счетов
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "STAV_R", schema="SCOTT")
@Getter @Setter
public class Stavr {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

    // дата начала
    @Column(name = "DAT1", updatable = false, nullable = true)
    private Date dt1;

    // дата окончания
    @Column(name = "DAT2", updatable = false, nullable = true)
    private Date dt2;

	// % начисления пени
    @Column(name = "PROC", updatable = false, nullable = true)
    private BigDecimal proc;

	// кол-во дней просрочки распространяется С
    @Column(name = "DAYS1", updatable = false, nullable = true)
    private Integer days1;

	// кол-во дней просрочки распространяется По
    @Column(name = "DAYS2", updatable = false, nullable = true)
    private Integer days2;

    // тип лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LSK_TP", referencedColumnName = "ID", updatable = false)
    private Lst tp;

}