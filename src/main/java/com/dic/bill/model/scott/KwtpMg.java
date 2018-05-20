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
 * Оплата по периоду
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KWTP_MGW", schema="SCOTT")
@Getter @Setter
public class KwtpMg implements java.io.Serializable  {

	public KwtpMg() {
	}

	// Id
	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// период
	@Column(name = "DOPL", updatable = false)
	private String dopl;

	// дата
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// заголовок платежа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="C_KWTP_ID", referencedColumnName="ID")
	private Kwtp kwtp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof KwtpMg))
	        return false;

	    KwtpMg other = (KwtpMg)o;

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

