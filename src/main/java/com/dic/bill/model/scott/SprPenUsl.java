package com.dic.bill.model.scott;

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
 * Справочник дат начисления пени по услугам
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "C_SPR_PEN_USL", schema="SCOTT")
@Getter @Setter
public class SprPenUsl {

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

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USL")
	private Usl usl;

	// УК по которой определена дата начала пени
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REU", referencedColumnName="REU")
	private Org uk;

}