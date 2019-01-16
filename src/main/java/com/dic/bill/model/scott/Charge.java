package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Начисление
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGE", schema="SCOTT")
@Getter @Setter
public class Charge implements java.io.Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Charge_id")
	@SequenceGenerator(name="SEQ_Charge_id", sequenceName="scott.c_charge_id", allocationSize=1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

	// Сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// Тип записи 0 - начисл со льгот.(без уч. изменений)  1 - начисл без льгот(по тарифу). 2- субсидия (с уч. изменений) 3 - сами льготы 4 - льготы (с уч. изменений) 5-инф.ОДН
	@Column(name = "TYPE", updatable = false)
	private Integer type;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Charge))
	        return false;

	    Charge other = (Charge)o;

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

