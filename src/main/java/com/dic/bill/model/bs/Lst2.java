package com.dic.bill.model.bs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dic.bill.Simple;

/**
 * Элемент списка
 *
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="rrr1")
@Table(name = "LIST", schema="BS")
public class Lst2 implements java.io.Serializable, Simple {

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
	private LstTp2 lstTp ;


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
	public LstTp2 getLstTp() {
		return lstTp;
	}
	public void setLstTp(LstTp2 lstTp) {
		this.lstTp = lstTp;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Lst2))
	        return false;

	    Lst2 other = (Lst2)o;

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

