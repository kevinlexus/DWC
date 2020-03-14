package com.dic.bill.model.scott;

import com.dic.bill.Compress;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

;

/**
 * Архивное начисление
 * @author lev
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "A_CHARGE_PREP2", schema="SCOTT")
public class AchargePrep implements java.io.Serializable, Compress {

	public AchargePrep() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Long id; // Id

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

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
	private Integer sch;

	@Column(name = "tp", updatable = false, nullable = true)
	private Integer tp;

	@Column(name = "vol_nrm", updatable = false, nullable = true)
	private Double volNrm;

	@Column(name = "vol_sv_nrm", updatable = false, nullable = true)
	private Double volSvNrm;

	@Column(name = "kpr2", updatable = false, nullable = true)
	private Double kpr2;

	@Column(name = "opl", updatable = false, nullable = true)
	private Double opl;

	@Column(name = "fk_spk", updatable = false, nullable = true)
	private Integer fkSpk;

	@Column(name = "mgfrom", updatable = true, nullable = false)
	private Integer mgFrom; // Начало действия записи

	@Column(name = "mgto", updatable = true, nullable = false)
	private Integer mgTo; // Окончание действия записи

	// ключ, по которому фильтровать сравниваемые кортежи
	@Formula("concat(USL,TP)")
    private String key;

	@Override
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

	@Override
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
	@Override
	public int getHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fkSpk == null) ? 0 : fkSpk.hashCode());
		result = prime * result + ((kpr == null) ? 0 : kpr.hashCode());
		result = prime * result + ((kpr2 == null) ? 0 : kpr2.hashCode());
		result = prime * result + ((kpro == null) ? 0 : kpro.hashCode());
		result = prime * result + ((kprz == null) ? 0 : kprz.hashCode());
		result = prime * result + ((kart.getLsk() == null) ? 0 : kart.getLsk().hashCode());
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
	@Override
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
		if (kart == null) {
			if (other.kart != null)
				return false;
		} else if (!kart.equals(other.kart))
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

	// ключ -  медленно работает! использовал @Formula
	//public String getKey() {
		//return getUsl().concat(String.valueOf(getTp())); <- очень медленно!
		//return new StringBuilder(getUsl()).append(getTp()).toString(); // <- побыстрее!
		//return getUsl()+getTp();
		//return getFullName();
	//}

}

