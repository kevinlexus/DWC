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
 * Организация эксперем.
 *
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORG", schema="SCOTT")
@Getter @Setter
public class Org implements java.io.Serializable {

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd;

	// код REU
    @Column(name = "REU")
	private String reu;

	// код TREST
    @Column(name = "TREST")
	private String trest;

	// Наименование
    @Column(name = "NAME")
	private String name;

	// ИНН
    @Column(name = "INN")
	private String inn;

	// БИК
    @Column(name = "BIK")
	private String bik;

	// расчетный счет
    @Column(name = "RASCHET_SCHET")
	private String operAcc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORGTP", referencedColumnName="ID")
	private OrgTp orgTp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Org))
	        return false;

	    Org other = (Org)o;

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

