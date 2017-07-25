package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Задолженности по периодам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGEPAY", schema="SCOTT")
@IdClass(AchargeId.class) // суррогатный первичный ключ
public class Acharge implements java.io.Serializable { 

	public Acharge() {
	}

    @Id
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Id
	@Column(name = "type", updatable = false, nullable = false)
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата
    
    @Id
    @Column(name = "usl", updatable = false, nullable = false)
	private String usl; // период задолжности

    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период задолжности
    
    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма задолжности

	public String getLsk() {
		return lsk;
	}

	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public String getUsl() {
		return usl;
	}

	public void setUsl(String usl) {
		this.usl = usl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lsk == null) ? 0 : lsk.hashCode());
		result = prime * result + ((mg == null) ? 0 : mg.hashCode());
		result = prime * result + ((usl == null) ? 0 : usl.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Acharge)) {
			return false;
		}
		Acharge other = (Acharge) obj;
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
		if (usl == null) {
			if (other.usl != null) {
				return false;
			}
		} else if (!usl.equals(other.usl)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

    
		
}

