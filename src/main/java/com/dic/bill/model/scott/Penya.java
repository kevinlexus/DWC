package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Исходящее сальдо по пене
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_PENYA", schema="SCOTT")
@Getter @Setter
public class Penya implements java.io.Serializable {

	public Penya() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id; // Id

	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Column(name = "summa", updatable = false, nullable = false)
	private BigDecimal summa; // задолженность

    @Column(name = "penya", updatable = false, nullable = false)
	private BigDecimal penya; // пеня

    @Column(name = "days", updatable = false, nullable = false)
	private Integer days; // кол-во дней долга

    @Column(name = "mg1", updatable = false, nullable = false)
	private String mg1; // период задолженности

}
