package com.dic.bill.model.scott;

import javax.persistence.*;

import com.dic.bill.model.exs.Eolink;
import lombok.Getter;
import lombok.Setter;

/**
 * Справочник всех объектов Klsk Objects - KO
 * @author Lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "K_LSK", schema="SCOTT")
@Getter @Setter
public class Ko implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// объект Eolink
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID", referencedColumnName="FK_KLSK_OBJ", updatable = false, insertable = false)
	private Eolink eolink;

	public Ko() {
		super();
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Ko))
	        return false;

	    Ko other = (Ko)o;

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

