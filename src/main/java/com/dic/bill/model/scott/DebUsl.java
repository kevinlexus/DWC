package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Задолженность по организациям - услугам - периодам
 * @author lev
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "C_DEB_USL", schema="SCOTT")
public class DebUsl implements java.io.Serializable{

	public DebUsl() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код услуги

    @Column(name = "org", updatable = false, nullable = false)
	private Integer org; // код организации

    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период

    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма

    @Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof DebUsl))
	        return false;

	    DebUsl other = (DebUsl)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (kart != null) {
	        return kart.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

