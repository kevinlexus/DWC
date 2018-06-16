package com.dic.bill.model.scott;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * Лицевой счет
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART", schema="SCOTT")
@Getter @Setter
public class Kart implements java.io.Serializable{

	public Kart() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; //id записи

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REU", referencedColumnName="REU")
	private Org uk;

    @Column(name = "kul", updatable = false, nullable = false)
	private String kul;

    @Column(name = "nd", updatable = false, nullable = false)
	private String nd;

    // дата ограничения пени
    @Column(name = "pn_dt", updatable = false, nullable = false)
	private Date pnDt;

    // номер квартиры
	@Column(name = "KW", nullable = true)
	private String num;

	// тип лиц.счета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID", updatable = false, insertable = false)
	private Lst tp;

	// Ko помешения (здесь OneToOne)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="K_LSK_ID", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko koKw;

	// Ko лиц.счета (здесь OneToOne)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko koLsk;

	// дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = false)
	private House house;

	// набор услуг
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private List<Nabor> nabor = new ArrayList<Nabor>(0);

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kart))
	        return false;

	    Kart other = (Kart)o;

	    if (lsk == other.getLsk()) return true;
	    if (lsk == null) return false;

	    // equivalence by id
	    return lsk.equals(other.getLsk());
	}

	@Override
	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

