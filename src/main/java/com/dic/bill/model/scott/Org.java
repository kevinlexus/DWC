package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Организация
 *
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORG", schema="SCOTT")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class Org implements java.io.Serializable {

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd;

	// код REU
    @Column(name = "REU")
	private String reu;

	// код TREST
    @Column(name = "TREST")
	private String trest;

	// Наименование
    @Column(name = "NAME")
	private String name;

	// ИНН
    @Column(name = "INN")
	private String inn;

	// БИК
    @Column(name = "BIK")
	private String bik;

	// расчетный счет
    @Column(name = "RASCHET_SCHET")
	private String operAcc;

	// расчетный счет для гис ЖКХ
	@Column(name = "R_SCH_GIS")
	private String operAccGis;

	// тип организации
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORGTP", referencedColumnName="ID")
	private OrgTp orgTp;

	// Тип распределения оплаты KWTP_MG (0-общий тип, 1 - сложный тип (ук 14,15 Кис)
	@Column(name = "DIST_PAY_TP")
	private Integer distPayTp;

	// осуществлять обмен по организации с ГИС ЖКХ? (0-нет, 1-да)
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "IS_EXCHANGE_GIS", updatable = false)
	private Boolean isExchangeGis;

	// Тип организации для ГИС ЖКХ (1-УО (упр.орг.), 2-РСО, 3-ТКО)
	@Column(name = "ORG_TP_GIS")
	private Integer orgTpGis;

	// Группировка для долгов Сбера (Не заполнено - брать REU, заполнено - группировать по этому полю)
	@Column(name = "GRP_DEB")
	private Integer grpDeb;

	@Transient
	public boolean isUO() {
		if (orgTpGis == null) {
			return false;
		} else {
			return orgTpGis.equals(1);
		}
	}

	@Transient
	public boolean isRSO() {
		if (orgTpGis == null) {
			return false;
		} else {
			return orgTpGis.equals(2);
		}
	}

	@Transient
	public boolean isTKO() {
		if (orgTpGis == null) {
			return false;
		} else {
			return orgTpGis.equals(3);
		}
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Org))
	        return false;

	    Org other = (Org)o;

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

