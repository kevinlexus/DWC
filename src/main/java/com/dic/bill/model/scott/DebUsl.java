package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Задолженность по организациям - услугам - периодам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_DEB_USL", schema="SCOTT")
public class DebUsl implements java.io.Serializable{ 

	public DebUsl() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код услуги

    @Column(name = "org", updatable = false, nullable = false)
	private Integer org; // код организации

    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период
    
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма

    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLsk() {
		return lsk;
	}

	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	public String getUsl() {
		return usl;
	}

	public void setUsl(String usl) {
		this.usl = usl;
	}

	public Integer getOrg() {
		return org;
	}

	public void setOrg(Integer org) {
		this.org = org;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof DebUsl))
	        return false;

	    DebUsl other = (DebUsl)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	
}

