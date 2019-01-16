package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Задолженности по периодам
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGEPREP", schema="SCOTT")
@IdClass(ChargePrepId.class) // суррогантый первичный ключ
@Getter @Setter
public class ChargePrep implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ChargePrep_id")
	@SequenceGenerator(name="SEQ_ChargePrep_id", sequenceName="scott.c_charge_prep_id", allocationSize=1)
	@Column(name = "ID", updatable = false, nullable = false)
	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

    @Id
	@Column(name = "type", updatable = false, nullable = false)
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата

    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период

    @Id
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false)
	private Usl usl;

}

