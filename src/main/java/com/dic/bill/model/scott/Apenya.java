package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Пеня 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_PENYA", schema="SCOTT")
@Getter @Setter
@IdClass(ApenyaId.class) // суррогатный первичный ключ
public class Apenya implements java.io.Serializable { 

	public Apenya() {
	}

    @Id
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // задолженность

    @Column(name = "penya", updatable = false, nullable = false)
	private Double penya; // пеня

    @Id
	@Column(name = "mg1", updatable = false, nullable = false)
	private String mg1; // период задолженности

    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // архивный период
	
}

