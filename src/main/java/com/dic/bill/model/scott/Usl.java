package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

	// Наименование
	@Column(name = "NM")
	private String nm;

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

