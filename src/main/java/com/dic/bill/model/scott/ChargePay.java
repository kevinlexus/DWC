package com.dic.bill.model.scott;

import com.dic.bill.Compress;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

/**
 * Задолженности по периодам
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGEPAY2", schema="SCOTT")
@Getter @Setter
public class ChargePay implements java.io.Serializable, Compress {

	public ChargePay() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_Chargepay2_id")
	@SequenceGenerator(name = "SEQ_C_Chargepay2_id", sequenceName = "scott.c_chargepay2_id", allocationSize = 1)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	@Column(name = "type", updatable = false, nullable = false)
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата

    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период задолженности

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма задолженности

    @Column(name = "summap", updatable = false, nullable = false)
	private Double summap; // сумма оплаты пени

	// Начало действия записи
	@Column(name = "mgfrom", nullable = false)
	private Integer mgFrom;

	// Начало действия записи
	@Column(name = "mgto", nullable = false)
	private Integer mgTo; // Начало действия записи

	// ключ, по которому фильтровать сравниваемые кортежи
	@Formula("concat(TYPE,MG)")
	private String key;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof ChargePay))
			return false;

		ChargePay other = (ChargePay)o;

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

	@Override
	public boolean isTheSame(Compress compr) {
		ChargePay chargePay = (ChargePay) compr;

		if (kart != null ? !kart.equals(chargePay.kart) : chargePay.kart != null) return false;
		if (type != null ? !type.equals(chargePay.type) : chargePay.type != null) return false;
		if (mg != null ? !mg.equals(chargePay.mg) : chargePay.mg != null) return false;
		if (summap != null ? !summap.equals(chargePay.summap) : chargePay.summap != null) return false;
		return summa != null ? summa.equals(chargePay.summa) : chargePay.summa == null;

	}

	@Override
	public int getHash() {
		int result = kart != null ? kart.hashCode() : 0;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (mg != null ? mg.hashCode() : 0);
		result = 31 * result + (summa != null ? summa.hashCode() : 0);
		result = 31 * result + (summap != null ? summap.hashCode() : 0);
		return result;
	}
}

