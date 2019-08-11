package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

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

	// является ли РСО?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "IS_RSO", updatable = false)
	private Boolean isRSO;

	// осуществлять обмен по организации с ГИС ЖКХ? (0-нет, 1-да)
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "IS_EXCHANGE_GIS", updatable = false)
	private Boolean isExchangeGis;

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

