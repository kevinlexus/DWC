package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dic.bill.Compress;;

/**
 * Архивное начисление 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_CHARGE_PREP2", schema="SCOTT")
public class AchargePrep implements java.io.Serializable, Compress { 

	public AchargePrep() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id; // Id

	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код.услуги

	@Column(name = "vol", updatable = false, nullable = true)
	private Double vol;

	@Column(name = "kpr", updatable = false, nullable = true)
	private Double kpr;
	
	@Column(name = "kprz", updatable = false, nullable = true)
	private Double kprz;

	@Column(name = "kpro", updatable = false, nullable = true)
	private Double kpro;
	
	@Column(name = "sch", updatable = false, nullable = true)
	private Double sch;

	@Column(name = "tp", updatable = false, nullable = true)
	private Double tp;
	
	@Column(name = "vol_nrm", updatable = false, nullable = true)
	private Double volNrm;

	@Column(name = "vol_sv_nrm", updatable = false, nullable = true)
	private Double volSvNrm;

	@Column(name = "kpr2", updatable = false, nullable = true)
	private Double kpr2;

	@Column(name = "opl", updatable = false, nullable = true)
	private Double opl;
	
	@Column(name = "fk_spk", updatable = false, nullable = true)
	private Double fkSpk;
	
	@Column(name = "mgFrom", updatable = true, nullable = false)
	private Integer mgFrom; // Начало действия записи

	@Column(name = "mgTo", updatable = true, nullable = false)
	private Integer mgTo; // Окончание действия записи

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

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}

	public Double getKpr() {
		return kpr;
	}

	public void setKpr(Double kpr) {
		this.kpr = kpr;
	}

	public Double getKprz() {
		return kprz;
	}

	public void setKprz(Double kprz) {
		this.kprz = kprz;
	}

	public Double getKpro() {
		return kpro;
	}

	public void setKpro(Double kpro) {
		this.kpro = kpro;
	}

	public Double getSch() {
		return sch;
	}

	public void setSch(Double sch) {
		this.sch = sch;
	}

	public Double getTp() {
		return tp;
	}

	public void setTp(Double tp) {
		this.tp = tp;
	}

	public Double getVolNrm() {
		return volNrm;
	}

	public void setVolNrm(Double volNrm) {
		this.volNrm = volNrm;
	}

	public Double getVolSvNrm() {
		return volSvNrm;
	}

	public void setVolSvNrm(Double volSvNrm) {
		this.volSvNrm = volSvNrm;
	}

	public Double getKpr2() {
		return kpr2;
	}

	public void setKpr2(Double kpr2) {
		this.kpr2 = kpr2;
	}

	public Double getOpl() {
		return opl;
	}

	public void setOpl(Double opl) {
		this.opl = opl;
	}

	public Double getFkSpk() {
		return fkSpk;
	}

	public void setFkSpk(Double fkSpk) {
		this.fkSpk = fkSpk;
	}

	public Integer getMgFrom() {
		return mgFrom;
	}

	public void setMgFrom(Integer mgFrom) {
		this.mgFrom = mgFrom;
	}

	public Integer getMgTo() {
		return mgTo;
	}

	public void setMgTo(Integer mgTo) {
		this.mgTo = mgTo;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof AchargePrep))
	        return false;

	    AchargePrep other = (AchargePrep)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}
	
	/**
	 * Получить hash всех полей, кроме id, mgFrom, mgTo - для компаратора 
	 * @return
	 */
	public int getHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fkSpk == null) ? 0 : fkSpk.hashCode());
		result = prime * result + ((kpr == null) ? 0 : kpr.hashCode());
		result = prime * result + ((kpr2 == null) ? 0 : kpr2.hashCode());
		result = prime * result + ((kpro == null) ? 0 : kpro.hashCode());
		result = prime * result + ((kprz == null) ? 0 : kprz.hashCode());
		result = prime * result + ((lsk == null) ? 0 : lsk.hashCode());
		result = prime * result + ((opl == null) ? 0 : opl.hashCode());
		result = prime * result + ((sch == null) ? 0 : sch.hashCode());
		result = prime * result + ((tp == null) ? 0 : tp.hashCode());
		result = prime * result + ((usl == null) ? 0 : usl.hashCode());
		result = prime * result + ((vol == null) ? 0 : vol.hashCode());
		result = prime * result + ((volNrm == null) ? 0 : volNrm.hashCode());
		result = prime * result + ((volSvNrm == null) ? 0 : volSvNrm.hashCode());
		return result;
	}
	
	/**
	 * Сравнить все поля, кроме id, mgFrom, mgTo - для компаратора 
	 * @return
	 */
	public boolean isTheSame(Compress compr) {
		AchargePrep other = (AchargePrep) compr;
		if (fkSpk == null) {
			if (other.fkSpk != null)
				return false;
		} else if (!fkSpk.equals(other.fkSpk))
			return false;
		if (kpr == null) {
			if (other.kpr != null)
				return false;
		} else if (!kpr.equals(other.kpr))
			return false;
		if (kpr2 == null) {
			if (other.kpr2 != null)
				return false;
		} else if (!kpr2.equals(other.kpr2))
			return false;
		if (kpro == null) {
			if (other.kpro != null)
				return false;
		} else if (!kpro.equals(other.kpro))
			return false;
		if (kprz == null) {
			if (other.kprz != null)
				return false;
		} else if (!kprz.equals(other.kprz))
			return false;
		if (lsk == null) {
			if (other.lsk != null)
				return false;
		} else if (!lsk.equals(other.lsk))
			return false;
		if (opl == null) {
			if (other.opl != null)
				return false;
		} else if (!opl.equals(other.opl))
			return false;
		if (sch == null) {
			if (other.sch != null)
				return false;
		} else if (!sch.equals(other.sch))
			return false;
		if (tp == null) {
			if (other.tp != null)
				return false;
		} else if (!tp.equals(other.tp))
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
		if (volNrm == null) {
			if (other.volNrm != null)
				return false;
		} else if (!volNrm.equals(other.volNrm))
			return false;
		if (volSvNrm == null) {
			if (other.volSvNrm != null)
				return false;
		} else if (!volSvNrm.equals(other.volSvNrm))
			return false;
		return true;
		}

	
	
}

