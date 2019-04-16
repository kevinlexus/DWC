package com.dic.bill.model.scott;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка из справочника spr_gen_itm,
 * описывающая шаги выполнения формирования
 * @version 1.0
 */
@Entity
@Table(name = "SPR_GEN_ITM", schema="SCOTT")
@DynamicUpdate
@Getter @Setter
public class SprGenItm {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@NaturalId
	@Column(name = "CD", unique = true, nullable = false)
	private String cd;

	@Column(name = "NAME", nullable = true)
	private String name;

	@Column(name = "STATE", nullable = true)
	private String state;

	@Column(name = "NPP", nullable = true)
	private Integer npp;

	@Column(name = "NPP2", nullable = true)
	private Integer npp2;

	@Column(name = "ERR", nullable = true)
	private Integer err;

	@Column(name = "PROC", nullable = true)
	private Double proc;

	// Выбрано пользователем?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "SEL", nullable = true)
	private Boolean sel;

	// Отображать пользователю?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "V", nullable = true)
	private Boolean v;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	@Column(name = "DT1", nullable = true)
	private Date dt1;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	@Column(name = "DT2", nullable = true)
	private Date dt2;

}