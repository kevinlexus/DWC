package com.dic.bill.model.bs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dic.bill.Simple;

/**
 * Тип адреса
 *
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="rrr1")
@Table(name = "ADDR_TP", schema="BS")
public class AddrTp implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; //cd

    @Column(name = "NAME", updatable = false, nullable = false)
	private String name; //Наименование

    @Column(name = "NPP", updatable = false, nullable = false)
	private Integer npp; //№ п.п.

    public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

    public String getCd() {
		return this.cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getNpp() {
		return npp;
	}

	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof AddrTp))
	        return false;

	    AddrTp other = (AddrTp)o;

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

