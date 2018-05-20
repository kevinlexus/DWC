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
 * Оплата, детализация по услугам
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KWTP_DAY", schema="SCOTT")
@Getter @Setter
public class KwtpDay implements java.io.Serializable  {

	public KwtpDay() {
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

	// дата принятия платежа
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// дата инкассации
	@Column(name = "DAT_INK", updatable = false)
	private Date dtInk;

	// оплата по периоду
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="KWTP_ID", referencedColumnName="ID")
	private KwtpMg kwtpMg;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof KwtpDay))
	        return false;

	    KwtpDay other = (KwtpDay)o;

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

