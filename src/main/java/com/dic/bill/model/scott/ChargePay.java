package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Задолженности по периодам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGEPAY", schema="SCOTT")
@IdClass(ChargePayId.class) // суррогатный первичный ключ
@Getter @Setter
public class ChargePay implements java.io.Serializable { 

	public ChargePay() {
	}

    @Id
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Id
	@Column(name = "type", updatable = false, nullable = false)
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата
    
    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период задолженности
    
    @Id
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма задолженности

		
}

