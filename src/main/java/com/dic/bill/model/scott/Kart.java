package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Лицевой счет (он же - помещение)
 *
 * @author lev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART", schema = "SCOTT")
@Getter
@Setter
public class Kart implements java.io.Serializable {

    public Kart() {
    }

    public Kart(String lsk) {
        this.lsk = lsk;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // ID задается при создании Entity
    @Column(name = "LSK", updatable = false, nullable = false)
    private String lsk;

    // УК
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REU", referencedColumnName = "REU")
    private Org uk;

    @Column(name = "KUL", updatable = false, nullable = false)
    private String kul;

    @Column(name = "ND", updatable = false, nullable = false)
    private String nd;

    // номер помещения
    @Column(name = "KW", updatable = false, nullable = false)
    private String num;

    // дата ограничения пени
    @Column(name = "PN_DT", updatable = false, nullable = false)
    private Date pnDt;

    // номер подъезда
    @Column(name = "ENTR")
    private Integer entry;

    // признак счета
    @Column(name = "PSCH", nullable = false)
    private Integer psch;

    // наличие эл.эн счетчика (0, null - нет, 1 - есть)
    @Column(name = "SCH_EL")
    private Integer schEl;

    // кол-во проживающих
    @Column(name = "KPR", nullable = false)
    private Integer kpr;

    // Кол-во вр.зарег.
    @Column(name = "KPR_WR", nullable = false)
    private Integer kprWr;

    // Кол-во вр.отсут.
    @Column(name = "KPR_OT", nullable = false)
    private Integer kprOt;

    // тип лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TP", referencedColumnName = "ID", updatable = false)
    private Lst tp;

    // статус лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS", referencedColumnName = "ID", nullable = false, updatable = false)
    private Status status;

    // Ko помешения
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "K_LSK_ID", referencedColumnName = "ID", updatable = false)
    // updatable = false - чтобы не было Update Foreign key
    private Ko koKw;

    // Ko лиц.счета (здесь OneToOne, cascade=CascadeType.ALL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_OBJ", referencedColumnName = "ID", updatable = false)
    private Ko koLsk;

    // дом
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", referencedColumnName = "ID", updatable = false)
    private House house;

    // родительский лицевой счет (привязка)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_LSK", referencedColumnName = "LSK", updatable = false)
    private Kart parentKart;

    // общая площадь
    @Column(name = "OPL")
    private BigDecimal opl;

    // расход по счетчику отопления
    @Column(name = "MOT")
    private BigDecimal mot;

    // показания по счетчику отопления
    @Column(name = "POT")
    private BigDecimal pot;

    // наличие ошибки при контроле статусов проживающих, счетчиков и т.п.
    @Column(name = "FK_ERR", nullable = false)
    private Integer fkErr;

    // начало действия
    @Column(name = "MG1")
    private String mgFrom;

    // окончание действия
    @Column(name = "MG2")
    private String mgTo;

    // набор услуг
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN) возможно приводит к утечке памяти (до 700 тыс объектов, при расчете по всем УК) ред.06.03.2019
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<Nabor> nabor = new HashSet<>(0);

    // проживающие
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)// updatable = false - чтобы не было Update Foreign key
    @Fetch(FetchMode.JOIN)
    private Set<KartPr> kartPr = new HashSet<>(0);

    // текущие начисления
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<Charge> charge = new ArrayList<>(0);

    // перерасчеты
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<Change> change = new ArrayList<>(0);

    // корректировки оплатой
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<CorrectPay> correctPay = new ArrayList<>(0);

    // подготовительная информация для расчета начисления
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<ChargePrep> chargePrep = new ArrayList<>(0);

    // сальдо
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<SaldoUsl> saldoUsl = new HashSet<>(0);

    // платеж, заголовок
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<Kwtp> kwtp = new HashSet<>(0);

    // платеж, распределение по периодам
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<KwtpMg> kwtpMg = new HashSet<>(0);

    // платеж, распределение по услугам, орг.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<KwtpDay> kwtpDay = new HashSet<>(0);

    // актуальный ли лицевой счет?
    @Transient
    public boolean isActual() {
        return !psch.equals(8) && !psch.equals(9);
    }

    // жилой счет?
    @Transient
    public boolean isResidental() {
        return !status.getId().equals(9);
    }

    // наличие счетчика х.в. (решил сделать Transient, так как неудобно обрабатывать NVL в методах)
    @Transient
    public boolean isExistColdWaterMeter() {
        return psch != null && Utl.in(psch, 1, 2);
    }

    // наличие счетчика г.в. (решил сделать Transient, так как неудобно обрабатывать NVL в методах)
    @Transient
    public boolean isExistHotWaterMeter() {
        return psch != null && Utl.in(psch, 1, 3);
    }

    // наличие счетчика эл.эн (решил сделать Transient, так как неудобно обрабатывать NVL в методах)
    @Transient
    public boolean isExistElMeter() {
        return schEl != null && schEl.equals(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Kart))
            return false;

        Kart other = (Kart) o;

        if (Objects.equals(lsk, other.getLsk())) return true;
        if (lsk == null) return false;

        // equivalence by id
        return lsk.equals(other.getLsk());
    }

    @Override
    public int hashCode() {
        if (lsk != null) {
            return lsk.hashCode();
        } else {
            return super.hashCode();
        }
    }


}

