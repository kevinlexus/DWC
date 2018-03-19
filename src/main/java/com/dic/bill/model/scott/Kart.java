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

import com.dic.bill.model.scott.Ko;

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
	
    @Column(name = "reu", updatable = false, nullable = false)
	private String reu;

    @Column(name = "kul", updatable = false, nullable = false)
	private String kul;

    @Column(name = "nd", updatable = false, nullable = false)
	private String nd;
    
	// номер квартиры
	@Column(name = "KW", nullable = true)
	private String num;

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

	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	
}

