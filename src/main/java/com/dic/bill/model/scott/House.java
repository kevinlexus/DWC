package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дом
 * @author lev
 * @version 1.01
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_House_id")
	@SequenceGenerator(name="SEQ_House_id", sequenceName="scott.c_house_id", allocationSize=1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id записи

    @Column(name = "kul", updatable = false, nullable = false)
	private String kul;

    @Column(name = "nd", updatable = false, nullable = false)
	private String nd;

    // статус дома (0 - открытый, 1 - закрытый) - Бред!
    @Column(name = "psch", updatable = false, nullable = true)
	private Integer psch;

	// GUID дома по справочнику FIAS
	@Column(name = "GUID")
	private String guid;

	// Ko дома (здесь OneToOne)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="K_LSK_ID", referencedColumnName="ID", updatable = false, insertable = true)
	private Ko ko;

	// лицевые счета
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = true) // updatable = false - чтобы не было Update Foreign key
	private List<Kart> kart = new ArrayList<>(0);

	// вводы
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = true) // updatable = false - чтобы не было Update Foreign key
	private List<Vvod> vvod = new ArrayList<>(0);

	@Override
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

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

