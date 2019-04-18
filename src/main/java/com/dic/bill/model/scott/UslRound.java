package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Справочник округления услуг тек.содержания для ГИС ЖКХ
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USL_ROUND", schema="TEST201903")
@IdClass(UslRoundId.class) // суррогатный первичный ключ
@Getter @Setter
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class UslRound implements java.io.Serializable  {

	public UslRound() {
	}

	// услуга
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// код REU
	@Id
	@Column(name = "REU")
	private String reu;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UslRound uslRound = (UslRound) o;

		if (usl != null ? !usl.equals(uslRound.usl) : uslRound.usl != null) return false;
		return reu != null ? reu.equals(uslRound.reu) : uslRound.reu == null;
	}

	@Override
	public int hashCode() {
		int result = usl != null ? usl.hashCode() : 0;
		result = 31 * result + (reu != null ? reu.hashCode() : 0);
		return result;
	}
}

