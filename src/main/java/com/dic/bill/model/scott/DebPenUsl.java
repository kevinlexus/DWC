package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Задолженность по организациям - услугам - периодам
 * @author lev
 * @version 1.00
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "C_DEBPEN_USL", schema="SCOTT")
public class DebPenUsl implements java.io.Serializable{

	public DebPenUsl() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

	 // сумма задолженности
    @Column(name = "SUMMA", updatable = false, nullable = false)
	private BigDecimal summa;

    // задолженность по пене
    @Column(name = "PENYA", updatable = false, nullable = false)
	private BigDecimal penya;

    // период
    @Column(name = "MG", updatable = false, nullable = false)
	private Integer mg;

    // период бухгалтерский
    @Column(name = "PERIOD", updatable = false, nullable = false)
	private Integer period;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof DebPenUsl))
	        return false;

	    DebPenUsl other = (DebPenUsl)o;

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

