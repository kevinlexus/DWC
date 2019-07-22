package com.dic.bill.model.scott;

import com.dic.bill.Simple;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Пользователь (сделан Tuser потому что уже есть sec.User)
 *
 *
 */
@SuppressWarnings("serial")
@Immutable
@Entity
@Table(name = "T_USER", schema="SCOTT")
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class Tuser implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD пользователя
    @Column(name = "CD")
	private String cd;

	public Tuser() {
		super();
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Tuser))
	        return false;

	    Tuser other = (Tuser)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

