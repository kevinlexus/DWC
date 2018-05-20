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

	@Column(name = "USL", updatable = false, nullable = false)
	private String usl; // код услуги

    @Column(name = "ORG", updatable = false, nullable = false)
	private Integer org; // код организации

    @Column(name = "SUMMA", updatable = false, nullable = false)
	private BigDecimal summa; // сумма задолженности

    @Column(name = "PENYA", updatable = false, nullable = false)
	private BigDecimal penya; // задолженность по пене

    @Column(name = "MG", updatable = false, nullable = false)
	private Integer mg; // период

    @Column(name = "PERIOD", updatable = false, nullable = false)
	private Integer period; // период бухгалтерский

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

