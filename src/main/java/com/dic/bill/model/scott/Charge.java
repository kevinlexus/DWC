package com.dic.bill.model.scott;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * Начисление
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGE", schema="TESTPOLIS")
@Getter @Setter
public class Charge implements java.io.Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Charge_id")
	@SequenceGenerator(name="SEQ_Charge_id", sequenceName="scott.c_charge_id", allocationSize=1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = true, nullable = false, insertable = true)
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = true, nullable = false, insertable = true)
	private Usl usl;

	// № п.п.
	@Column(name = "NPP", updatable = true, nullable = true, insertable = true)
	private Integer npp;

	// сумма
	@Column(name = "SUMMA", updatable = true, nullable = true, insertable = true)
	private BigDecimal summa;

	// объем
	@Column(name = "TEST_OPL", updatable = true, nullable = true, insertable = true)
	private BigDecimal testOpl;

	// кол-во проживающих
	@Column(name = "KPR", updatable = true, nullable = true, insertable = true)
	private BigDecimal kpr;

	// кол-во временно зарег
	@Column(name = "KPRZ", updatable = true, nullable = true, insertable = true)
	private BigDecimal kprz;

	// кол-во временно отсут
	@Column(name = "KPRO", updatable = true, nullable = true, insertable = true)
	private BigDecimal kpro;

	// кол-во проживающих, в т.ч. все, В.О. В.З. В.П.
	@Column(name = "KPR2", updatable = true, nullable = true, insertable = true)
	private BigDecimal kpr2;

	// площадь
	@Column(name = "OPL", updatable = true, nullable = true, insertable = true)
	private BigDecimal opl;

	// цена
	@Column(name = "TEST_CENA", updatable = true, nullable = true, insertable = true)
	private BigDecimal testCena;

	// тип записи 0 - начисл со льгот.(без уч. изменений)  1 - начисл без льгот(по тарифу). 2- субсидия (с уч. изменений) 3 - сами льготы 4 - льготы (с уч. изменений) 5-инф.ОДН
	@Column(name = "TYPE", updatable = true, nullable = false, insertable = true)
	private Integer type;

	// наличие счетчика
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "SCH", nullable = true)
	private Boolean isSch;

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

