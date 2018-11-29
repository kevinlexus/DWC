package com.dic.bill.model.scott;

import java.math.BigDecimal;
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
 * Перерасчет
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHANGE", schema="SCOTT")
@Getter @Setter
public class Change implements java.io.Serializable  {

	public Change() {
	}

	// Id
	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// период за который перерасчет
	@Column(name = "MGCHANGE", updatable = false)
	private String mgchange;

	// Дата перерасчета
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Change))
	        return false;

	    Change other = (Change)o;

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

