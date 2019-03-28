package com.dic.bill.model.scott;

import javax.persistence.*;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Справочник услуг
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USL", schema="TEST")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class Usl implements java.io.Serializable  {

	public Usl() {
	}

	@Id
    @Column(name = "USL", unique=true, updatable = false, nullable = false)
	private String id;

	// CD
	@Column(name = "CD")
	private String cd;

	// наименование
	@Column(name = "NM")
	private String name;

	// наименование поля счетчик в scott.kart
	@Column(name = "COUNTER")
	private String counter;

	// для справочника дат начала обязательств по долгу -  PEN_DT Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_DT", updatable = false)
    private Integer tpPenDt;

	// для справочника ставок рефинансирования - PEN_REF Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_REF", updatable = false)
    private Integer tpPenRef;

	// 0 - коэфф. в справочнике тарифов, 1 - норматив в справочнике тарифов,
	// 2 - koeff-коэфф и norm-норматив, 3-koeff и norm-оба коэфф
	// 4 - koeff-коэфф и norm-норматив, только если koeff == 0, то берётся в объемы, и не берётся в c_charge (для х.в., г.в. и водоотведения)
	@Column(name = "SPTARN", updatable = false)
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
	private Usl uslOverSoc;

	// услуга для определения объема (иногда делается подстановка)
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="USL_VOL", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Usl uslVol;

	// тип услуги 2 -- 0 - услуга коммунальная (х.в.), 1 - услуга жилищная (тек.сод.)
	@Column(name = "USL_TYPE2", updatable = false)
	private Integer type2;

	// вариант расчета услуги
	@Column(name = "FK_CALC_TP", updatable = false)
	private Integer fkCalcTp;

	// цены по услуге
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="USL", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	//@Fetch(FetchMode.JOIN) возможно приводит к утечке памяти (до 700 тыс объектов, при расчете по всем УК) ред.06.03.2019
	private Set<Price> price = new HashSet<>(0);

	// порядок расчета услуг
	@Column(name = "USL_ORDER", updatable = false)
	private Integer uslOrder;

	// дочерняя услуга (связанная) например х.в.--> х.в.МОП.
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="FK_USL_CHLD", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Usl uslChild;

	// коды REU, для округления, для ГИС ЖКХ
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="USL", referencedColumnName="USL", updatable = false) // updatable = false - чтобы не было Update Foreign key
	//@Fetch(FetchMode.JOIN) возможно приводит к утечке памяти (до 700 тыс объектов, при расчете по всем УК) ред.06.03.2019
	private Set<UslRound> uslRound = new HashSet<>(0);

	/**
	 * Получить фактическую услугу, поставляющую объем (иногда нужно, например для услуги fkCalcTp=31)
	 */
	@Transient
	public Usl getMeterUslVol() {
		return getUslVol()!=null? getUslVol():this;
	}

	/**
	 * Является ли услуга жилищной?
	 */
	@Transient
	public boolean isHousing() {

		return getType2() != null && getType2().equals(1);
	}

	/**
	 * Получить фактическую услугу свыше соц.нормы
	 */
	@Transient
	public Usl getFactUslOverSoc() {
		if (getUslOverSoc() != null) {
			return getUslOverSoc();
		} else {
			// пустая услуга свыше соц.нормы, вернуть основную
			return this;
		}
	}

	/**
	 * Получить фактическую услугу 0 зарег
	 */
	@Transient
	public Usl getFactUslEmpt() {
		if (getUslEmpt() != null) {
			return getUslEmpt();
		} else {
			// пустая услуга 0 зарег, вернуть свыше соц.нормы
			if (getUslOverSoc() != null) {
				return getUslOverSoc();
			} else {
				// пустая услуга свыше соц.нормы, вернуть основную
				return this;
			}
		}
	}

	/**
	 * Является ли услуга основной? (заполнено fk_calc_tp)
	 */
	@Transient
	public boolean isMain() {
		return getFkCalcTp() != null;
	}

	/**
	 * Рассчитывается ли услуга по площади? (для округления площади в рассчете)
	 */
	@Transient
	public boolean isCalcByArea()
	{
		return Utl.in(fkCalcTp, 25) // текущее содержание и подобные услуги (без свыше соц.нормы и без 0 проживающих)
				|| fkCalcTp.equals(7) // найм (только по муниципальным помещениям) расчет на м2
				|| fkCalcTp.equals(24) || fkCalcTp.equals(32) // прочие услуги, расчитываемые как расценка * норматив * общ.площадь
				|| fkCalcTp.equals(36)// вывоз жидких нечистот и т.п. услуги
				|| fkCalcTp.equals(37); // капремонт
	}

	/**
	 * Особый расчет цены
	 */
	@Transient
	public boolean isCalcPriceSpecial()
	{
		return fkCalcTp.equals(7) // найм (только по муниципальным помещениям) расчет на м2
				|| fkCalcTp.equals(25) // текущее содержание и подобные услуги (без свыше соц.нормы и без 0 проживающих)
				|| fkCalcTp.equals(32) // прочие услуги, расчитываемые как расценка * норматив * общ.площадь
				|| fkCalcTp.equals(36)// вывоз жидких нечистот и т.п. услуги
				|| fkCalcTp.equals(37); // капремонт
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

