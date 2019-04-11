package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;

/**
 * Справочник обработки оплаты
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SPR_PROC_PAY", schema="SCOTT")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class SprProcPay implements java.io.Serializable  {

	public SprProcPay() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USL", referencedColumnName = "USl", updatable = false, nullable = false)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG", referencedColumnName = "ID", updatable = false, nullable = false)
	private Org org;

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REU", referencedColumnName = "REU")
	private Org uk;

	// начало действия
	@Column(name = "MG1")
	private String mgFrom;

	// окончание действия
	@Column(name = "MG2")
	private String mgTo;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof SprProcPay))
	        return false;

	    SprProcPay other = (SprProcPay)o;

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

