package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Проживающий
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KART_PR", schema="SCOTT")
@Getter @Setter
public class KartPr implements java.io.Serializable  {

	public KartPr() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false, nullable = false)
	private Kart kart;

	// статус
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STATUS", referencedColumnName="ID", updatable = false, nullable = false)
	private StatusPr statusPr;

	// родственная связь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="RELAT_ID", referencedColumnName="ID", updatable = false, nullable = true)
	private Relation relation;

	// дата рождения
	@Column(name = "DAT_ROG", unique=true, updatable = false, nullable = true)
	private Date dtBirdth;

	// дата прописки
	@Column(name = "DAT_REG", unique=true, updatable = false, nullable = true)
	private Date dtReg;

	// дата убытия
	@Column(name = "DAT_UB", unique=true, updatable = false, nullable = true)
	private Date dtUnReg;

	// ф.и.о.
	@Column(name = "FIO", unique=true, updatable = false, nullable = true)
	private String fio;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof KartPr))
	        return false;

	    KartPr other = (KartPr)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

}

