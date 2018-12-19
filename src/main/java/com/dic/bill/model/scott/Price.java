package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Расценка
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Price", schema="SCOTT")
@Getter @Setter
public class Price implements java.io.Serializable {

	public Price() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Price_id")
	@SequenceGenerator(name="SEQ_Price_id", sequenceName="scott.prices_id", allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id; //id записи

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, insertable = false)
	private Usl usl;

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, insertable = false)
	private Org uk;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Price))
	        return false;

	    Price other = (Price)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

