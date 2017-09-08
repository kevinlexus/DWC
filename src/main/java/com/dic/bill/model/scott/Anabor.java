package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dic.bill.Compress;

/**
 * Наборы услуг по периодам 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_NABOR", schema="SCOTT")
public class Anabor implements java.io.Serializable, Compress { 

	public Anabor() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id; // Id

    @Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код услуги
		
	@Column(name = "mg", updatable = false, nullable = false)
	private String mg; // период по-старому

	@Column(name = "mg1", updatable = true, nullable = false)
	private Integer mg1; // Начало действия записи

	@Column(name = "mg2", updatable = true, nullable = false)
	private Integer mg2; // Окончание действия записи

	@Column(name = "org", updatable = false, nullable = false)
	private Double org; // код орг.

	@Column(name = "koeff", updatable = false, nullable = false)
	private Double koeff; 
	
	@Column(name = "fk_tarif", updatable = false, nullable = false)
	private Double fkTarif; 

	@Column(name = "fk_vvod", updatable = false, nullable = false)
	private Double fkVvod; 

	@Column(name = "vol", updatable = false, nullable = false)
	private Double vol;
	
	@Column(name = "fk_dvb", updatable = false, nullable = false)
	private Double fkDvb;
	
	@Column(name = "vol_add", updatable = false, nullable = false)
	private Double volAdd;
	
	@Column(name = "kf_kpr", updatable = false, nullable = false)
	private Double kfKpr;
	
	@Column(name = "sch_auto", updatable = false, nullable = false)
	private Double schAuto;

	@Column(name = "nrm_kpr", updatable = false, nullable = false)
	private Double nrmKpr;

	@Column(name = "kf_kpr_sch", updatable = false, nullable = false)
	private Double kfKprSch;

	@Column(name = "kf_kpr_wrz", updatable = false, nullable = false)
	private Double kfKprWrz;

	@Column(name = "kf_kpr_wro", updatable = false, nullable = false)
	private Double kfKprWro;

	@Column(name = "kf_kpr_wrz_sch", updatable = false, nullable = false)
	private Double kfKprWrzSch;

	@Column(name = "kf_kpr_wro_sch", updatable = false, nullable = false)
	private Double kfKprWroSch;

	@Column(name = "limit", updatable = false, nullable = false)
	private Double Limit;

	@Column(name = "nrm_kpr2", updatable = false, nullable = false)
	private Double nrmKpr2;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLsk() {
		return lsk;
	}

	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	public String getUsl() {
		return usl;
	}

	public void setUsl(String usl) {
		this.usl = usl;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	public Integer getMg1() {
		return mg1;
	}

	public void setMg1(Integer mg1) {
		this.mg1 = mg1;
	}

	public Integer getMg2() {
		return mg2;
	}

	public void setMg2(Integer mg2) {
		this.mg2 = mg2;
	}

	public Double getOrg() {
		return org;
	}

	public void setOrg(Double org) {
		this.org = org;
	}

	public Double getKoeff() {
		return koeff;
	}

	public void setKoeff(Double koeff) {
		this.koeff = koeff;
	}

	public Double getFkTarif() {
		return fkTarif;
	}

	public void setFkTarif(Double fkTarif) {
		this.fkTarif = fkTarif;
	}

	public Double getFkVvod() {
		return fkVvod;
	}

	public void setFkVvod(Double fkVvod) {
		this.fkVvod = fkVvod;
	}

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}

	public Double getFkDvb() {
		return fkDvb;
	}

	public void setFkDvb(Double fkDvb) {
		this.fkDvb = fkDvb;
	}

	public Double getVolAdd() {
		return volAdd;
	}

	public void setVolAdd(Double volAdd) {
		this.volAdd = volAdd;
	}

	public Double getKfKpr() {
		return kfKpr;
	}

	public void setKfKpr(Double kfKpr) {
		this.kfKpr = kfKpr;
	}

	public Double getSchAuto() {
		return schAuto;
	}

	public void setSchAuto(Double schAuto) {
		this.schAuto = schAuto;
	}

	public Double getNrmKpr() {
		return nrmKpr;
	}

	public void setNrmKpr(Double nrmKpr) {
		this.nrmKpr = nrmKpr;
	}

	public Double getKfKprSch() {
		return kfKprSch;
	}

	public void setKfKprSch(Double kfKprSch) {
		this.kfKprSch = kfKprSch;
	}

	public Double getKfKprWrz() {
		return kfKprWrz;
	}

	public void setKfKprWrz(Double kfKprWrz) {
		this.kfKprWrz = kfKprWrz;
	}

	public Double getKfKprWro() {
		return kfKprWro;
	}

	public void setKfKprWro(Double kfKprWro) {
		this.kfKprWro = kfKprWro;
	}

	public Double getKfKprWrzSch() {
		return kfKprWrzSch;
	}

	public void setKfKprWrzSch(Double kfKprWrzSch) {
		this.kfKprWrzSch = kfKprWrzSch;
	}

	public Double getKfKprWroSch() {
		return kfKprWroSch;
	}

	public void setKfKprWroSch(Double kfKprWroSch) {
		this.kfKprWroSch = kfKprWroSch;
	}

	public Double getLimit() {
		return Limit;
	}

	public void setLimit(Double limit) {
		Limit = limit;
	}

	public Double getNrmKpr2() {
		return nrmKpr2;
	}

	public void setNrmKpr2(Double nrmKpr2) {
		this.nrmKpr2 = nrmKpr2;
	}

	/**
	 * Сравнить тип объекта и все поля, кроме полей id, mg, mg1, mg2 
	 * @param obj - Сравниваемый объект
	 * @return
	 */
	public boolean same(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anabor other = (Anabor) obj;
		if (Limit == null) {
			if (other.Limit != null)
				return false;
		} else if (!Limit.equals(other.Limit))
			return false;
		if (fkDvb == null) {
			if (other.fkDvb != null)
				return false;
		} else if (!fkDvb.equals(other.fkDvb))
			return false;
		if (fkTarif == null) {
			if (other.fkTarif != null)
				return false;
		} else if (!fkTarif.equals(other.fkTarif))
			return false;
		if (fkVvod == null) {
			if (other.fkVvod != null)
				return false;
		} else if (!fkVvod.equals(other.fkVvod))
			return false;
		if (kfKpr == null) {
			if (other.kfKpr != null)
				return false;
		} else if (!kfKpr.equals(other.kfKpr))
			return false;
		if (kfKprSch == null) {
			if (other.kfKprSch != null)
				return false;
		} else if (!kfKprSch.equals(other.kfKprSch))
			return false;
		if (kfKprWro == null) {
			if (other.kfKprWro != null)
				return false;
		} else if (!kfKprWro.equals(other.kfKprWro))
			return false;
		if (kfKprWroSch == null) {
			if (other.kfKprWroSch != null)
				return false;
		} else if (!kfKprWroSch.equals(other.kfKprWroSch))
			return false;
		if (kfKprWrz == null) {
			if (other.kfKprWrz != null)
				return false;
		} else if (!kfKprWrz.equals(other.kfKprWrz))
			return false;
		if (kfKprWrzSch == null) {
			if (other.kfKprWrzSch != null)
				return false;
		} else if (!kfKprWrzSch.equals(other.kfKprWrzSch))
			return false;
		if (koeff == null) {
			if (other.koeff != null)
				return false;
		} else if (!koeff.equals(other.koeff))
			return false;
		if (lsk == null) {
			if (other.lsk != null)
				return false;
		} else if (!lsk.equals(other.lsk))
			return false;
		if (nrmKpr == null) {
			if (other.nrmKpr != null)
				return false;
		} else if (!nrmKpr.equals(other.nrmKpr))
			return false;
		if (nrmKpr2 == null) {
			if (other.nrmKpr2 != null)
				return false;
		} else if (!nrmKpr2.equals(other.nrmKpr2))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (schAuto == null) {
			if (other.schAuto != null)
				return false;
		} else if (!schAuto.equals(other.schAuto))
			return false;
		if (usl == null) {
			if (other.usl != null)
				return false;
		} else if (!usl.equals(other.usl))
			return false;
		if (vol == null) {
			if (other.vol != null)
				return false;
		} else if (!vol.equals(other.vol))
			return false;
		if (volAdd == null) {
			if (other.volAdd != null)
				return false;
		} else if (!volAdd.equals(other.volAdd))
			return false;
		return true;
	}
	
}

