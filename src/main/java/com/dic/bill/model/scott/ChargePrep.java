package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Задолженности по периодам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHARGEPREP", schema="SCOTT")
@IdClass(ChargePrepId.class) // суррогантый первичный ключ
public class ChargePrep implements java.io.Serializable { 

	public ChargePrep() {
	}

    @Id
	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

    @Id
	@Column(name = "type", updatable = false, nullable = false)
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата
    
    @Id
    @Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период задолжности
    
    @Id
    @Column(name = "period", updatable = false, nullable = false)
	private String period; // период бухгалтерский

    @Column(name = "summa", updatable = false, nullable = false)
	private Double summa; // сумма задолжности

    
		
}

