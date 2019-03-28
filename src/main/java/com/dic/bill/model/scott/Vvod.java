package com.dic.bill.model.scott;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Ввод
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_VVOD", schema="TEST")
@Getter @Setter
public class Vvod implements java.io.Serializable {

	public Vvod() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Vvod_id")
	@SequenceGenerator(name="SEQ_Vvod_id", sequenceName="scott.c_vvod_id", allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
	private Long id; //id записи

	// дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = true)
	private House house;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, insertable = true)
	private Usl usl;

	// распределение воды по дому (0, null-пропорционально расходу, 2-нет услуги, не считать вообще, 1 - проп. площади,
	// 4-по дому, без ОДПУ, есть возм.установки, 5-по дому, без ОДПУ, нет возм.установки,
	// 6-просто учитывать объем, 7 - информационно отобразить объем в Счете в ОДПУ)
    @Column(name = "DIST_TP", updatable = false, nullable = true, insertable = true)
	private Integer distTp;

	// объем для распределения
	@Column(name = "KUB", updatable = true, nullable = true, insertable = true)
	private BigDecimal kub;

	// объем по счетчикам
	@Column(name = "KUB_SCH", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubSch;

	// объем по нормативу
	@Column(name = "KUB_NORM", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubNorm;

	// объем по нежилым
	@Column(name = "KUB_AR", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubAr;

	// распределенный объем
	@Column(name = "KUB_DIST", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubDist;

	// дораспр.факт - на норматив
	@Column(name = "KUB_NRM_FACT", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubNrmFact;

	// дораспр.факт - на счетчики
	@Column(name = "KUB_SCH_FACT", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubSchFact;

	// дораспр.факт - итого
	@Column(name = "KUB_FACT", updatable = true, nullable = true, insertable = true)
	private BigDecimal kubFact;

	// кол-во лиц.счетов со счетчиками
	@Column(name = "SCH_CNT", updatable = true, nullable = true, insertable = true)
	private BigDecimal schCnt;

	// кол-во лиц.счетов по нормативу
	@Column(name = "CNT_LSK", updatable = true, nullable = true, insertable = true)
	private BigDecimal cntLsk;

	// кол-во людей по нормативу
	@Column(name = "KPR", updatable = true, nullable = true, insertable = true)
	private BigDecimal kpr;

	// кол-во людей по счетч.
	@Column(name = "SCH_KPR", updatable = true, nullable = true, insertable = true)
	private BigDecimal schKpr;

	// площадь по вводу
	@Column(name = "OPL_ADD", updatable = true, nullable = true, insertable = true)
	private BigDecimal oplAdd;

	// норматив по ОДН, для отчётов, рассчитывается либо по таблице opl_liter, либо в условии
	@Column(name = "NRM", updatable = true, nullable = true, insertable = true)
	private BigDecimal nrm;

	// площадь по нежилым
	@Column(name = "OPL_AR", updatable = true, nullable = true, insertable = true)
	private BigDecimal oplAr;

	// набор услуг
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="FK_VVOD", referencedColumnName="ID", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<Nabor> nabor = new ArrayList<>(0);

	// начислять ли отопление в неотопительный период?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "NON_HEAT_PER", nullable = true, insertable = true)
	private Boolean isChargeInNotHeatingPeriod;

	// использовать счетчики для распределения?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "USE_SCH", nullable = true, insertable = true)
	private Boolean isUseSch;

	// не ограничивать лимитом потребления ОДН? (долько для определённых dist_tp)
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "WO_LIMIT", nullable = true, insertable = true)
	private Boolean isWithoutLimit;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Vvod))
	        return false;

	    Vvod other = (Vvod)o;

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

