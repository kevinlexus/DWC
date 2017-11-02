package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.dic.bill.Compress;

import lombok.Getter;
import org.apache.commons.collections4.Equator;
import org.hibernate.annotations.Formula;;

/**
 * Архивное начисление 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_CHARGE2", schema="SCOTT")
public class Acharge implements java.io.Serializable, Compress { 

	public Acharge() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id; // Id

	@Column(name = "lsk", updatable = false, nullable = false)
	private String lsk; // лиц.счет

	@Column(name = "usl", updatable = false, nullable = false)
	private String usl; // код.услуги

	@Column(name = "summa", updatable = false, nullable = true)
	private Double summa;

	@Column(name = "kart_pr_id", updatable = false, nullable = true)
	private Integer kartPrId;

	@Column(name = "spk_id", updatable = false, nullable = true)
	private Integer spkId;

	@Column(name = "type", updatable = false, nullable = true)
	private Integer type;
	
	@Column(name = "test_opl", updatable = false, nullable = true)
	private Double testOpl;
	
	@Column(name = "test_cena", updatable = false, nullable = true)
	private Double testCena;

	@Column(name = "test_tarkoef", updatable = false, nullable = true)
	private Double testTarkoef;
	
	@Column(name = "test_spk_koef", updatable = false, nullable = true)
	private Double testSpkkoef;
	
	@Column(name = "main", updatable = false, nullable = true)
	private Integer main;
	
	@Column(name = "lg_doc_id", updatable = false, nullable = true)
	private Integer lgDocId;

	@Column(name = "npp", updatable = false, nullable = true)
	private Integer npp;
	
	@Column(name = "sch", updatable = false, nullable = true)
	private Integer sch;

	@Column(name = "kpr", updatable = false, nullable = true)
	private Double kpr;
	
	@Column(name = "kprz", updatable = false, nullable = true)
	private Double kprz;

	@Column(name = "kpro", updatable = false, nullable = true)
	private Double kpro;

	@Column(name = "kpr2", updatable = false, nullable = true)
	private Double kpr2;
	
	@Column(name = "opl", updatable = false, nullable = true)
	private Double opl;
	
	@Column(name = "mgFrom", updatable = true, nullable = false)
	private Integer mgFrom; // Начало действия записи

	@Column(name = "mgTo", updatable = true, nullable = false)
	private Integer mgTo; // Окончание действия записи

	// ключ, по которому фильтровать сравниваемые кортежи
	@Formula("concat(USL,TYPE)")
    private String key;

	
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

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public Integer getKartPrId() {
		return kartPrId;
	}

	public void setKartPrId(Integer kartPrId) {
		this.kartPrId = kartPrId;
	}

	public Integer getSpkId() {
		return spkId;
	}

	public void setSpkId(Integer spkId) {
		this.spkId = spkId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getTestOpl() {
		return testOpl;
	}

	public void setTestOpl(Double testOpl) {
		this.testOpl = testOpl;
	}

	public Double getTestCena() {
		return testCena;
	}

	public void setTestCena(Double testCena) {
		this.testCena = testCena;
	}

	public Double getTestTarkoef() {
		return testTarkoef;
	}

	public void setTestTarkoef(Double testTarkoef) {
		this.testTarkoef = testTarkoef;
	}

	public Double getTestSpkkoef() {
		return testSpkkoef;
	}

	public void setTestSpkkoef(Double testSpkkoef) {
		this.testSpkkoef = testSpkkoef;
	}

	public Integer getMain() {
		return main;
	}

	public void setMain(Integer main) {
		this.main = main;
	}

	public Integer getLgDocId() {
		return lgDocId;
	}

	public void setLgDocId(Integer lgDocId) {
		this.lgDocId = lgDocId;
	}

	public Integer getNpp() {
		return npp;
	}

	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	public Integer getSch() {
		return sch;
	}

	public void setSch(Integer sch) {
		this.sch = sch;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Acharge))
	        return false;

	    Acharge other = (Acharge)o;

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
	 * Получить hash всех полей, кроме id, npp, mgFrom, mgTo - для компаратора 
	 * @return
	 */
	public int getHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kartPrId == null) ? 0 : kartPrId.hashCode());
		result = prime * result + ((kpr == null) ? 0 : kpr.hashCode());
		result = prime * result + ((kpr2 == null) ? 0 : kpr2.hashCode());
		result = prime * result + ((kpro == null) ? 0 : kpro.hashCode());
		result = prime * result + ((kprz == null) ? 0 : kprz.hashCode());
		result = prime * result + ((lgDocId == null) ? 0 : lgDocId.hashCode());
		result = prime * result + ((lsk == null) ? 0 : lsk.hashCode());
		result = prime * result + ((main == null) ? 0 : main.hashCode());
		result = prime * result + ((opl == null) ? 0 : opl.hashCode());
		result = prime * result + ((sch == null) ? 0 : sch.hashCode());
		result = prime * result + ((spkId == null) ? 0 : spkId.hashCode());
		result = prime * result + ((summa == null) ? 0 : summa.hashCode());
		result = prime * result + ((testCena == null) ? 0 : testCena.hashCode());
		result = prime * result + ((testOpl == null) ? 0 : testOpl.hashCode());
		result = prime * result + ((testSpkkoef == null) ? 0 : testSpkkoef.hashCode());
		result = prime * result + ((testTarkoef == null) ? 0 : testTarkoef.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((usl == null) ? 0 : usl.hashCode());
		return result;
	}
	
	/**
	 * Сравнить все поля, кроме id, npp, mgFrom, mgTo - для компаратора 
	 * @return
	 */
	public boolean isTheSame(Compress compr) {
		Acharge other = (Acharge) compr;
		if (kartPrId == null) {
			if (other.kartPrId != null)
				return false;
		} else if (!kartPrId.equals(other.kartPrId))
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
		if (lgDocId == null) {
			if (other.lgDocId != null)
				return false;
		} else if (!lgDocId.equals(other.lgDocId))
			return false;
		if (lsk == null) {
			if (other.lsk != null)
				return false;
		} else if (!lsk.equals(other.lsk))
			return false;
		if (main == null) {
			if (other.main != null)
				return false;
		} else if (!main.equals(other.main))
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
		if (spkId == null) {
			if (other.spkId != null)
				return false;
		} else if (!spkId.equals(other.spkId))
			return false;
		if (summa == null) {
			if (other.summa != null)
				return false;
		} else if (!summa.equals(other.summa))
			return false;
		if (testCena == null) {
			if (other.testCena != null)
				return false;
		} else if (!testCena.equals(other.testCena))
			return false;
		if (testOpl == null) {
			if (other.testOpl != null)
				return false;
		} else if (!testOpl.equals(other.testOpl))
			return false;
		if (testSpkkoef == null) {
			if (other.testSpkkoef != null)
				return false;
		} else if (!testSpkkoef.equals(other.testSpkkoef))
			return false;
		if (testTarkoef == null) {
			if (other.testTarkoef != null)
				return false;
		} else if (!testTarkoef.equals(other.testTarkoef))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (usl == null) {
			if (other.usl != null)
				return false;
		} else if (!usl.equals(other.usl))
			return false;
		return true;
	}

	// ключ -  медленно работает! использовал @Formula
	/*public String getKey() {
		return getUsl().concat(String.valueOf(getType()));
	}*/

}

