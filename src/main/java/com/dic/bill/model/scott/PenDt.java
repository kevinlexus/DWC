package com.dic.bill.model.scott;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник дат начисления пени по типам услуг
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "PEN_DT", schema="SCOTT")
@Getter @Setter
public class PenDt {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// период начисления пени
    @Column(name = "MG", updatable = false, nullable = true)
    private Integer mg;

    // дата, с которой идёт начисление пени
    @Column(name = "DT", updatable = false, nullable = true)
    private Date dt;


	// тип услуги -> USL.TP_PEN (0-обычная услуга, 1- капремонт)
    @Column(name = "USL_TP_PEN", updatable = false, nullable = true)
    private Integer uslTpPen;

	// Начальный код УК
    @Column(name = "REUFROM", updatable = false, nullable = true)
    private String reuFrom;

	// Конечный код УК
    @Column(name = "REUTO", updatable = false, nullable = true)
    private String reuTo;
}