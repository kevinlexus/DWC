package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Таблица параметров
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PARAMS", schema="TEST")
@Getter @Setter
public class Param implements java.io.Serializable {

	public Param() {
	}

    @Id
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id; // ID

	@Column(name = "PERIOD", updatable = true, nullable = false)
	private String period; // период

}

