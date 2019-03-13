package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Элемент списка
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "U_LIST", schema="SCOTT")
public class Lst implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd;

	// наименование
    @Column(name = "NAME", updatable = false, nullable = false)
	private String name;

	// тип значения
	@Column(name = "VAL_TP", updatable = false, nullable = false)
	private String valTp;

    // тип списка
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LISTTP", referencedColumnName="ID")
	private LstTp lstTp ;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Lst))
	        return false;

	    Lst other = (Lst)o;

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

