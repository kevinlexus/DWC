package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Тип элемента списка
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "U_LISTTP", schema="SCOTT")
public class LstTp implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd;

	// наименование
    @Column(name = "NAME", updatable = false, nullable = false)
	private String name;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof LstTp))
	        return false;

	    LstTp other = (LstTp)o;

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

