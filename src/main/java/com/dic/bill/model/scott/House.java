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
 * Дом 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_HOUSES", schema="SCOTT")
@Getter @Setter
public class House implements java.io.Serializable { 

	public House() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id; //id записи
	
    @Column(name = "reu", updatable = false, nullable = false)
	private String reu;

    @Column(name = "kul", updatable = false, nullable = false)
	private String kul;

    @Column(name = "nd", updatable = false, nullable = false)
	private String nd;
    
	// Ko помешения (здесь OneToOne)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="K_LSK_ID", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko ko;

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof House))
	        return false;

	    House other = (House)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	
}

