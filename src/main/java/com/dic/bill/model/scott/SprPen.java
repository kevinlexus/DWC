package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

/**
 * Справочник дат начисления пени по типам лиц.счетов
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "C_SPR_PEN", schema="SCOTT")
@Getter @Setter
public class SprPen {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// период начисления пени
    @Column(name = "MG", updatable = false, nullable = true)
    private Integer mg;

    // дата, с которой идёт начисление пени
    @Column(name = "DAT", updatable = false, nullable = true)
    private Date dt;

    // код REU
    @Column(name = "REU")
    private String reu;

    // тип лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LSK_TP", referencedColumnName = "ID", updatable = false)
    private Lst tp;

}