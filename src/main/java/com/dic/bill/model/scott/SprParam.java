package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Справочник параметров
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SPR_PARAMS", schema="SCOTT")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class SprParam implements java.io.Serializable  {

	public SprParam() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD")
	private String cd;

	// наименование
	@Column(name = "NAME")
	private String name;

	// тип значения (0 - число, 1- Строка симв, 2-Дата, 3-Логич.,4 - список(одно знач.),5-диапазон, 6-список своих значений)
	@Column(name = "CDTP")
	private Integer cdTp;

	// значение типа number
	@Column(name = "PARN1")
	private Double n1;

	// значение типа varchar2
	@Column(name = "PARVC1")
	private String s1;

	// значение типа Date
	@Column(name = "PARDT1")
	private Date d1;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof SprParam))
	        return false;

	    SprParam other = (SprParam)o;

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

