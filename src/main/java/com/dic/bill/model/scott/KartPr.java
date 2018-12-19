package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Проживающий
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KART_PR", schema="SCOTT")
@Getter @Setter
public class KartPr implements java.io.Serializable  {

	public KartPr() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KartPr_id")
	@SequenceGenerator(name="SEQ_KartPr_id", sequenceName="scott.kart_pr_id", allocationSize=1)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", insertable = true, updatable = false, nullable = false)
	private Kart kart;

	// статус
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STATUS", referencedColumnName="ID", updatable = false, nullable = false)
	private StatusPr statusPr;

	// родственная связь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="RELAT_ID", referencedColumnName="ID", updatable = false, nullable = true)
	private Relation relation;

	// дата рождения
	@Column(name = "DAT_ROG", unique=true, updatable = false, nullable = true)
	private Date dtBirdth;

	// дата прописки
	@Column(name = "DAT_PROP", unique=true, updatable = false, nullable = true)
	private Date dtReg;

	// дата убытия
	@Column(name = "DAT_UB", unique=true, updatable = false, nullable = true)
	private Date dtUnReg;

	// ф.и.о.
	@Column(name = "FIO", unique=true, updatable = false, nullable = true)
	private String fio;

	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="FK_KART_PR", referencedColumnName="ID", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<StatePr> statePr = new ArrayList<>(0);

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof KartPr))
	        return false;

	    KartPr other = (KartPr)o;

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

