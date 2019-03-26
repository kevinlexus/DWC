package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Статус проживающего
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "C_STATUS_PR", schema="TEST")
public class StatusPr implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// наименование
    @Column(name = "NAME", updatable = false, nullable = false)
	private String name;

	// наименование укороченное
	@Column(name = "NAME2", updatable = false, nullable = false)
	private String name2;

	// тип статуса (временная регистрация, прописка и т.п.)
	@ManyToOne(fetch = FetchType.LAZY) // сделал EAGER - иногда приводит к LazyInitialisationException ред. 06.03.2019
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof StatusPr))
	        return false;

	    StatusPr other = (StatusPr)o;

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

