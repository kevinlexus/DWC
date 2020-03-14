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
@SuppressWarnings("serial")
@Entity
@Table(name = "A_CHARGE2", schema="SCOTT")
@Getter @Setter
public class Acharge implements java.io.Serializable, Compress {

	public Acharge() {
	}

    @Id
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// код.услуги
	@Column(name = "usl", updatable = false, nullable = false)
	private String usl;

	// сумма начисления
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

	// начало действия записи
	@Column(name = "mgfrom", updatable = true, nullable = false)
	private Integer mgFrom;

	// окончание действия записи
	@Column(name = "mgto", updatable = true, nullable = false)
	private Integer mgTo;

	// ключ, по которому фильтровать сравниваемые кортежи
	@Formula("concat(USL,TYPE)")
    private String key;

	@Override
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

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	/**
	 * Получить hash всех полей, кроме id, npp, mgFrom, mgTo - для компаратора
	 */
	@Override
	public int getHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kartPrId == null) ? 0 : kartPrId.hashCode());
		result = prime * result + ((kpr == null) ? 0 : kpr.hashCode());
		result = prime * result + ((kpr2 == null) ? 0 : kpr2.hashCode());
		result = prime * result + ((kpro == null) ? 0 : kpro.hashCode());
		result = prime * result + ((kprz == null) ? 0 : kprz.hashCode());
		result = prime * result + ((lgDocId == null) ? 0 : lgDocId.hashCode());
		result = prime * result + ((kart.getLsk() == null) ? 0 : kart.getLsk().hashCode());
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
	@Override
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
		if (kart == null) {
			if (other.kart != null)
				return false;
		} else if (!kart.equals(other.kart))
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

