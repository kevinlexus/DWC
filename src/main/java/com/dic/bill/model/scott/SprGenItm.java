package com.dic.bill.model.scott;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

//Класс строки из справочника spr_gen_itm
//описывающего шаги выполнения формирования

@Entity
@Table(name = "SPR_GEN_ITM")
public class SprGenItm {

	private int id;
	private String cd, name, state;
	private Integer npp, npp2, err;
	private Double proc;
	private Boolean sel;
	private Date dt1, dt2;

	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@NaturalId
	@Column(name = "CD", unique = true, nullable = false)
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}

	@Column(name = "NAME", nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NPP", nullable = true)
	public Integer getNpp() {
		return npp;
	}
	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	@Column(name = "NPP2", nullable = true)
	public Integer getNpp2() {
		return npp2;
	}
	public void setNpp2(Integer npp2) {
		this.npp2 = npp2;
	}

	@Column(name = "ERR", nullable = true)
	public Integer getErr() {
		return err;
	}
	public void setErr(Integer err) {
		this.err = err;
	}

	@Column(name = "PROC", nullable = true)
	public Double getProc() {
		return proc;
	}
	public void setProc(Double proc) {
		this.proc = proc;
	}

	@Column(name = "STATE", nullable = true)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	@Column(name = "DT1", nullable = true)
	public Date getDt1() {
		return dt1;
	}
	public void setDt1(Date dt1) {
		this.dt1 = dt1;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	@Column(name = "DT2", nullable = true)
	public Date getDt2() {
		return dt2;
	}
	public void setDt2(Date dt2) {
		this.dt2 = dt2;
	}
	
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "SEL", nullable = true)
	public Boolean getSel() {
		return sel;
	}
	
	public void setSel(Boolean sel) {
		this.sel = sel;
	}
}