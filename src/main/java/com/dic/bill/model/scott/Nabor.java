package com.dic.bill.model.scott;

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
 * Наборов услуг по организациям в лицевом счете
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NABOR", schema="SCOTT")
@Getter @Setter
public class Nabor implements java.io.Serializable  {

	public Nabor() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false)
	private Org org;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Nabor))
	        return false;

	    Nabor other = (Nabor)o;

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

