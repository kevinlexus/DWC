package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * История статуса проживающего
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "C_STATES_PR", schema="SCOTT")
public class StatesPr implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// проживающий
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KART_PR", referencedColumnName="ID")
	private KartPr kartPr;

    // статус
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_STATUS", referencedColumnName="ID")
	private StatusPr statusPr;

	// начало периода
	@Column(name = "DT1", unique=true, updatable = false, nullable = true)
	private Date dtFrom;

	// окончание периода
	@Column(name = "DT2", unique=true, updatable = false, nullable = true)
	private Date dtTo;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof StatesPr))
	        return false;

	    StatesPr other = (StatesPr)o;

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

