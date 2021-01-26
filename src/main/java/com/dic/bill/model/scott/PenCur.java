package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Текущая начисленная пеня
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_PEN_CUR_KMP", schema="SCOTT") // fixme временно изменил наименование
@Getter @Setter
@NoArgsConstructor
public class PenCur implements java.io.Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEN_CUR")
	@SequenceGenerator(name = "SEQ_PEN_CUR", sequenceName = "scott.c_pen_cur_id", allocationSize = 1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// ставка рефинансирования
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_STAV", referencedColumnName="ID")
	private Stavr stavr;

	// долг для расчета пени
	@Column(name = "SUMMA2", updatable = false, nullable = false)
	private BigDecimal deb;

	// пеня
    @Column(name = "PENYA", updatable = false, nullable = false)
	private BigDecimal penya;

	// кол-во дней начисления пени
    @Column(name = "CURDAYS", updatable = false, nullable = false)
	private Integer curDays;

	// период задолженности
    @Column(name = "MG1", updatable = false, nullable = false)
	private String mg1;

	// Дата начала
	@Column(name = "DT1", updatable = false)
	private Date dt1;

	// Дата окончания
	@Column(name = "DT2", updatable = false)
	private Date dt2;

}

