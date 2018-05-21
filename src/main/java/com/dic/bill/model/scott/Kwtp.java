package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Оплата, заголовок
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KWTP", schema="SCOTT")
@Getter @Setter
public class Kwtp implements java.io.Serializable  {

	public Kwtp() {
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

	// дата
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// детализация платежа
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="C_KWTP_ID", referencedColumnName="ID")
	private List<KwtpMg> kwtpMg = new ArrayList<KwtpMg>(0);

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kwtp))
	        return false;

	    Kwtp other = (Kwtp)o;

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

