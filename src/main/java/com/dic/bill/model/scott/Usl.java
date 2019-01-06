package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Справочник услуг
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USL", schema="SCOTT")
@Getter @Setter
public class Usl implements java.io.Serializable  {

	public Usl() {
	}

	@Id
    @Column(name = "USL", unique=true, updatable = false, nullable = false)
	private String id;

	// наименование
	@Column(name = "NM")
	private String name;

	// наименование поля счетчик в scott.kart
	@Column(name = "COUNTER")
	private String counter;

	// для справочника дат начала обязательств по долгу -  PEN_DT Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_DT", updatable = false, nullable = true)
    private Integer tpPenDt;

	// для справочника ставок рефинансирования - PEN_REF Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_REF", updatable = false, nullable = true)
    private Integer tpPenRef;

	// 0 - коэфф. в справочнике тарифов, 1 - норматив в справочнике тарифов,
	// 2 - koeff-коэфф и norm-норматив, 3-koeff и norm-оба коэфф
	@Column(name = "SPTARN", updatable = false, nullable = true)
	private Integer sptarn;

	// родительская услуга (например Х.В. 0 прожив --> Х.В.)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl parentUsl;

	// услуга без проживающих
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="USL_EMPT", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Usl uslEmpt;

	// услуга свыше соцнормы
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="USL_P", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Usl uslOverNorm;

	// услуга для определения объема (иногда делается подстановка)
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="USL_VOL", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Usl uslVol;

	// тип услуги 2 -- 0 - услуга коммунальная (х.в.), 1 - услуга жилищная (тек.сод.)
	@Column(name = "USL_TYPE2", updatable = false, nullable = true)
	private Integer type2;

	// вариант расчета услуги
	@Column(name = "FK_CALC_TP", updatable = false, nullable = true)
	private Integer fkCalcTp;

	// цены по услуге
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="USL", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<Price> price = new ArrayList<>(0);

	// порядок расчета услуг
	@Column(name = "USL_ORDER", updatable = false, nullable = true)
	private Integer uslOrder;

	/**
	 * Получить фактическую услугу, поставляющую объем (иногда нужно, например для услуги fkCalcTp=31)
	 * @return
	 */
	@Transient
	public Usl getFactUslVol() {
		return getUslVol()!=null? getUslVol():this;
	}

	/**
	 * Является ли услуга жилищной?
	 * @return
	 */
	@Transient
	public boolean isHousing() {

		if (getType2() != null && getType2().equals(1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Является ли услуга основной? (заполнено fk_calc_tp)
	 * @return
	 */
	@Transient
	public boolean isMain() {
		return getFkCalcTp() != null? true:false;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Usl))
	        return false;

	    Usl other = (Usl)o;

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

