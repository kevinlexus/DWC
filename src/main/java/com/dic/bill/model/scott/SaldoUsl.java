package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Сальдо по организациям - услугам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SALDO_USL", schema="SCOTT")
@IdClass(SaldoUslId.class) // суррогантый первичный ключ
public class SaldoUsl implements java.io.Serializable { 

	public SaldoUsl() {
	}

    @Id
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Id
	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код услуги

    @Id
    @Column(name = "org", updatable = false, nullable = false)
	private Integer org; // код организации

    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период задолжности
    
    @Id
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма задолжности

    
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SaldoUsl)) {
			return false;
		}
		SaldoUsl other = (SaldoUsl) obj;
		if (lsk == null) {
			if (other.lsk != null) {
				return false;
			}
		} else if (!lsk.equals(other.lsk)) {
			return false;
		}
		if (mg == null) {
			if (other.mg != null) {
				return false;
			}
		} else if (!mg.equals(other.mg)) {
			return false;
		}
		if (org == null) {
			if (other.org != null) {
				return false;
			}
		} else if (!org.equals(other.org)) {
			return false;
		}
		if (period == null) {
			if (other.period != null) {
				return false;
			}
		} else if (!period.equals(other.period)) {
			return false;
		}
		if (usl == null) {
			if (other.usl != null) {
				return false;
			}
		} else if (!usl.equals(other.usl)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lsk == null) ? 0 : lsk.hashCode());
		result = prime * result + ((mg == null) ? 0 : mg.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((usl == null) ? 0 : usl.hashCode());
		return result;
	}

	
	
}

