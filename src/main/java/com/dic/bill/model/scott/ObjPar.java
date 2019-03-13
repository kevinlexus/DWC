package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Параметры по объекту
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "T_OBJXPAR", schema="SCOTT")
public class ObjPar implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ObjPar_id")
	@SequenceGenerator(name="SEQ_ObjPar_id", sequenceName="scott.u_list_id", allocationSize=1)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LSK", referencedColumnName="LSK", updatable = false, nullable = true)
	private Kart kart;

	// параметр BD
	@Column(name = "N1", updatable = false, nullable = true)
	private BigDecimal n1;

	// параметр String
    @Column(name = "S1", updatable = false, nullable = true)
	private String s1;

	// параметр Date
	@Column(name = "D1", updatable = false, nullable = true)
	private Date d1;

	// период
	@Column(name = "mg", updatable = false, nullable = true)
	private String mg;

	// Ko объекта
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_K_LSK", referencedColumnName="ID", updatable = false, insertable = true, nullable = true)
	private Ko ko;

	// параметр
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LIST", referencedColumnName="ID")
	private Lst lst;

	// пользователь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER", referencedColumnName="ID")
	private Tuser tuser;

	// timestamp
	@Column(name = "TS", updatable = false, nullable = true)
	private Date ts;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ObjPar))
	        return false;

	    ObjPar other = (ObjPar)o;

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

