package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Таблица параметров
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PARAMS", schema="TEST201903")
@Immutable
@Cacheable // note как быть при переходе месяца, если закэшировано?
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
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

