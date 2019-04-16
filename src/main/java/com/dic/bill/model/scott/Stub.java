package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Заглушка - для вывода информации из REF_CURSOR Oracle
 * @version 1.0
 * @author lev
 *
 */
@Entity
@Table
@Immutable
@Getter @Setter
public class Stub implements java.io.Serializable {

	public Stub() {
	}

    @Id
	@Column(name = "ID")
	private Integer id;

	// какой-либо текст (лиц.счет, Id дома и прочее)
	@Column(name = "TEXT")
	private String text;

}

