package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Справочник перенаправления оплаты и пени на другие услуги
 * @author Lev
 * @version 1.00
 */
@Entity
@Table(name = "REDIR_PAY", schema="SCOTT")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RedirPay {

	// Id
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	// Тип обработки (1-Оплата, 0-Пеня, 2-Статистика)
    @Column(name = "TP", updatable = false, nullable = true)
    private Integer tp;

	// услуга - источник
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USL_SRC", referencedColumnName="USL")
	private Usl uslSrc;

	// услуга - назначение (если не заполнено, не меняется)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USL_DST", referencedColumnName="USL")
	private Usl uslDst;

	// Организация - источник (если не заполнено, берётся любая)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG_SRC", referencedColumnName="ID")
	private Org orgSrc;

	// Организация - назначение (если не заполнено, не меняется)
	// Использовать орг, которая обслуживает фонд (-1) (минус один) - изза этого не смог сделать Mapping ManyToOne!
    @Column(name = "FK_ORG_DST", updatable = false, nullable = true)
	private Integer orgDstId;

	// УК по которой производится перенаправление, если пусто - то редирект для всех УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REU", referencedColumnName="REU")
	private Org uk;

}