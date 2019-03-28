package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Родственная связь
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "RELATIONS", schema="TEST")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class Relation implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd;

	// наименование
    @Column(name = "NAME", updatable = false, nullable = true)
	private String name;

	// наименование (2 вариант)
	@Column(name = "NAME2", updatable = false, nullable = true)
	private String name2;

	// тип отношения (если 1, - то Квартиросъемщик)
	@Column(name = "FK_RELAT_TP", updatable = false, nullable = true)
	private String relationTp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Relation))
	        return false;

	    Relation other = (Relation)o;

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

