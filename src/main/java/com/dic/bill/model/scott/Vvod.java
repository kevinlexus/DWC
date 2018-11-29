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
 * Ввод
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_VVOD", schema="SCOTT")
@Getter @Setter
public class Vvod implements java.io.Serializable {

	public Vvod() {

	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id; //id записи

	// дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = false)
	private House house;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, insertable = false)
	private Usl usl;

	// распределение воды по дому (0, null-пропорционально расходу, 2-нет услуги, не считать вообще, 1 - проп. площади,
	// 4-по дому, без ОДПУ, есть возм.установки, 5-по дому, без ОДПУ, нет возм.установки,
	// 6-просто учитывать объем, 7 - информационно отобразить объем в Счете в ОДПУ)
    @Column(name = "dist_tp", updatable = false, nullable = true)
	private Integer distTp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Vvod))
	        return false;

	    Vvod other = (Vvod)o;

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

