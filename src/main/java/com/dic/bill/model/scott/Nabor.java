package com.dic.bill.model.scott;

import javax.persistence.*;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Наборов услуг по организациям в лицевом счете
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NABOR", schema="TEST")
@Getter @Setter
public class Nabor implements java.io.Serializable  {

	public Nabor() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Nabor_id")
	@SequenceGenerator(name="SEQ_Nabor_id", sequenceName="scott.nabor_id", allocationSize=1)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false, nullable = false, insertable = true)
	private Kart kart;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, nullable = false, insertable = true)
	private Usl usl;

	// организация - поставщик услуги
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG", referencedColumnName="ID", updatable = false, nullable = false, insertable = true)
	private Org org;

	// ввод
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_VVOD", referencedColumnName="ID", updatable = false, insertable = true)
	private Vvod vvod;

	@Column(name = "KOEFF")
	private BigDecimal koeff;

	@Column(name = "NORM")
	private BigDecimal norm;

	// распределение объема (например по отоплению гкал.)
	@Column(name = "VOL")
	private BigDecimal vol;

	// распределение объема (например по Х.В.ОДН - старому)
	@Column(name = "VOL_ADD")
	private BigDecimal volAdd;

	// лимит объема по услуге (Используется при начислении по ОДН)
	@Column(name = "LIMIT")
	private BigDecimal limit;

	/**
	 * Получить статус действующей услуги
	 * @return
	 */
	@Transient
	public boolean isValid() {
		BigDecimal bdKoeff = Utl.nvl(getKoeff(), BigDecimal.ZERO);
		BigDecimal bdNorm = Utl.nvl(getNorm(), BigDecimal.ZERO);
		if (usl.getFkCalcTp() != null && usl.getFkCalcTp().equals(14)) {
			// отопление Гкал - отдельная проверка
			// контроль только по коэфф.
			if (bdKoeff.compareTo(BigDecimal.ZERO) != 0) {
				return true;
			}
		} else {
			switch (usl.getSptarn()) {
				case 0: {
					// контроль только по коэфф.
					if (bdKoeff.compareTo(BigDecimal.ZERO) != 0) {
						return true;
					}
					break;
				}
				case 1: {
					// контроль только по нормативу
					if (bdNorm.compareTo(BigDecimal.ZERO) != 0) {
						return true;
					}
					break;
				}
				case 2:
				case 3: {
					// когда koeff-является коэфф. и когда norm-тоже является коэфф.
					// контроль по коэфф.и нормативу (странно и 2 и 3 sptarn, - потом разобраться, почему так FIXME
					if (bdKoeff.multiply(bdNorm).compareTo(BigDecimal.ZERO) != 0) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}

	/**
	 * Получить коэффициент для использования в получении расценки
	 * @return
	 */
	@Transient
	public BigDecimal getKoeffForPrice() {
		BigDecimal bdKoeff = Utl.nvl(getKoeff(), BigDecimal.ZERO);
		BigDecimal bdNorm = Utl.nvl(getNorm(), BigDecimal.ZERO);
		switch (usl.getSptarn()) {
			case 0 :
			case 1 :
			case 2 : {
				return bdKoeff;
			}
			case 3 : {
				// когда koeff-является коэфф. и когда norm-тоже является коэфф.
				// контроль по коэфф.и нормативу (странно и 2 и 3 sptarn, - потом разобраться, почему так FIXME
				return bdKoeff.multiply(bdNorm);
			}
			default: {
				return BigDecimal.ZERO;
			}
		}
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Nabor))
	        return false;

	    Nabor other = (Nabor)o;

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

}

