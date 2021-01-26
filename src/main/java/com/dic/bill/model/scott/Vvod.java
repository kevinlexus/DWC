package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Ввод
 *
 * @author lev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_VVOD", schema = "SCOTT")
@Getter
@Setter
public class Vvod implements java.io.Serializable {

    public Vvod() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Vvod_id")
    @SequenceGenerator(name = "SEQ_Vvod_id", sequenceName = "scott.c_vvod_id", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id; //id записи

    // дом
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", referencedColumnName = "ID")
    private House house;

    // услуга
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USL", referencedColumnName = "USl")
    private Usl usl;

    // распределение воды по дому (0, null-пропорционально расходу, 2-нет услуги, не считать вообще, 1 - проп. площади,
    // 4-по дому, без ОДПУ, есть возм.установки, 5-по дому, без ОДПУ, нет возм.установки,
    // 6-просто учитывать объем, 7 - информационно отобразить объем в Счете в ОДПУ)
    @Column(name = "DIST_TP")
    private Integer distTp;

    // объем для распределения
    @Column(name = "KUB")
    private BigDecimal kub;

    // объем по счетчикам
    @Column(name = "KUB_SCH")
    private BigDecimal kubSch;

    // объем по нормативу
    @Column(name = "KUB_NORM")
    private BigDecimal kubNorm;

    // объем по нежилым
    @Column(name = "KUB_AR")
    private BigDecimal kubAr;

    // распределенный объем
    @Column(name = "KUB_DIST")
    private BigDecimal kubDist;

    // дораспр.факт - на норматив
    @Column(name = "KUB_NRM_FACT")
    private BigDecimal kubNrmFact;

    // дораспр.факт - на счетчики
    @Column(name = "KUB_SCH_FACT")
    private BigDecimal kubSchFact;

    // дораспр.факт - итого
    @Column(name = "KUB_FACT")
    private BigDecimal kubFact;

    // дораспр.факт - на счетчики
    @Column(name = "KUB_FACT_UPNORM")
    private BigDecimal kubFactUpNorm;

    // кол-во лиц.счетов со счетчиками
    @Column(name = "SCH_CNT")
    private BigDecimal schCnt;

    // кол-во лиц.счетов по нормативу
    @Column(name = "CNT_LSK")
    private BigDecimal cntLsk;

    // кол-во людей по нормативу
    @Column(name = "KPR")
    private BigDecimal kpr;

    // кол-во людей по счетч.
    @Column(name = "SCH_KPR")
    private BigDecimal schKpr;

    // площадь по вводу
    @Column(name = "OPL_ADD")
    private BigDecimal oplAdd;

    // норматив по ОДН, для отчётов, рассчитывается либо по таблице opl_liter, либо в условии
    @Column(name = "NRM")
    private BigDecimal nrm;

    // площадь по нежилым
    @Column(name = "OPL_AR")
    private BigDecimal oplAr;

    // набор услуг
    @OneToMany(mappedBy = "vvod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Nabor> nabor = new ArrayList<>(0);

    // начислять ли отопление в неотопительный период?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "NON_HEAT_PER")
    private Boolean isChargeInNotHeatingPeriod;

    // использовать счетчики для распределения?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "USE_SCH")
    private Boolean isUseSch;

    // не ограничивать лимитом потребления ОДН? (долько для определённых dist_tp)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "WO_LIMIT")
    private Boolean isWithoutLimit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Vvod))
            return false;

        Vvod other = (Vvod) o;

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

