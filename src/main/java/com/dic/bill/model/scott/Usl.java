package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

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

	// для справочника дат начала обязательств по долгу -  PEN_DT Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_DT", updatable = false, nullable = true)
    private Integer tpPenDt;

	// для справочника ставок рефинансирования - PEN_REF Тип услуги (0-прочие услуги, 1-капремонт)
    @Column(name = "TP_PEN_REF", updatable = false, nullable = true)
    private Integer tpPenRef;

	// 0 - коэфф. в справочнике тарифов, 1 - норматив в справочнике тарифов,
	// 2 - koeff-коэфф и norm-норматив, 3-koeff и norm-оба коэфф
	@Column(name = "sptarn", updatable = false, nullable = true)
	private Integer sptarn;

	// родительская услуга (например Х.В. 0 прожив --> Х.В.)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl parentUsl;

	// тип услуги 2 -- 0 - услуга коммунальная (х.в.), 1 - услуга жилищная (тек.сод.)
	@Column(name = "USL_TYPE2", updatable = false, nullable = true)
	private Integer type2;


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
	 * Является ли услуга основной? (не имеет услуг по parent_usl)
	 * @return
	 */
	@Transient
	public boolean isMain() {
		return getParentUsl() != null? false:true;
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

