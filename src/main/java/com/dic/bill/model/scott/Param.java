package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Таблица параметров
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PARAMS", schema="SCOTT")
public class Param implements java.io.Serializable { 

	public Param() {
	}

    @Id
	@Column(name = "period", updatable = false, nullable = false)
	private String period; // ID, он же период

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}


    
		
}

