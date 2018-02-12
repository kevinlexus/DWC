package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Таблица начислений
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGE", schema="SCOTT")
@Getter @Setter
public class Charge implements java.io.Serializable  {

	public Charge() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Лиц.счет
	@Column(name = "LSK", updatable = false)
	private String lsk;

	// Сумма
	@Column(name = "SUMMA", updatable = false)
	private Double summa;

	// Тип записи 0 - начисл со льгот.(без уч. изменений)  1 - начисл без льгот(по тарифу). 2- субсидия (с уч. изменений) 3 - сами льготы 4 - льготы (с уч. изменений) 5-инф.ОДН
	@Column(name = "TYPE", updatable = false)
	private Integer type;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Charge))
	        return false;

	    Charge other = (Charge)o;

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
