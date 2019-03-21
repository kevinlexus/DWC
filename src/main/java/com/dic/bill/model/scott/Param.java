package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Таблица параметров
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PARAMS", schema="SCOTT")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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

