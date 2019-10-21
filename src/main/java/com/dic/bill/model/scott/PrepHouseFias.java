package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Справочник соответствия GUID домов из ФИАС - Дому из Директ
 *
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PREP_HOUSE_FIAS", schema="SCOTT")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class PrepHouseFias implements java.io.Serializable {

	@Id // ID - он же GUID по ФИАС
    @Column(name = "HOUSEGUID", updatable = false, nullable = false)
	private String id;

	// дом
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private House house;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PrepHouseFias))
	        return false;

	    PrepHouseFias other = (PrepHouseFias)o;

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

