package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Элемент списка
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "U_LIST", schema="SCOTT")
public class Lst implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; //cd

    @Column(name = "NAME", updatable = false, nullable = false)
	private String name; //Наименование

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

