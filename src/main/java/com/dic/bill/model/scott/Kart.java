package com.dic.bill.model.scott;

import com.dic.bill.model.exs.Eolink;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Лицевой счет (он же - помещение)
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART", schema = "SCOTT")
@DynamicUpdate
@Getter
@Setter
public class Kart {

    public Kart() {
    }

    public Kart(String lsk) {
        this.lsk = lsk;
    }

    @Id
    @Column(name = "LSK", nullable = false)
    private String lsk;

    // УК
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REU", referencedColumnName = "REU")
    private Org uk;

    // код улицы (повторяемый столбец, так как есть Spul!)
    @Column(name = "KUL", insertable = false, updatable = false, nullable = false)
    private String kul;

    // номер дома
    @Column(name = "ND", nullable = false)
    private String nd;

    // номер помещения
    @Column(name = "KW", nullable = false)
    private String num;

    // фамилия
    @Column(name = "K_FAM", nullable = false)
    private String kFam;

    // имя
    @Column(name = "K_IM", nullable = false)
    private String kIm;

    // отчество
    @Column(name = "K_OT", nullable = false)
    private String kOt;

    // дата ограничения пени
    @Column(name = "PN_DT", nullable = false)
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
    @JoinColumn(name = "FK_TP", referencedColumnName = "ID")
    private Lst tp;

    // статус лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS", referencedColumnName = "ID", nullable = false)
    private Status status;

    // улица
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KUL", referencedColumnName = "ID", nullable = false)
    private Spul spul;

    // Ko финансового лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "K_LSK_ID", referencedColumnName = "ID")
    // updatable = false - чтобы не было Update Foreign key
    // fixme ред.11.12.20  убрал ВЕЗДЕ в данном классе updatable = false - не работают тесты, позже разобраться (может начать тормозить в долгах сбера!)
    private Ko koKw;

    // Ko помещения -- почему OneToOne? исправил ред.08.06.20
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_PREMISE", referencedColumnName = "ID")
    private Ko koPremise;

    // Ko лиц.счета (здесь OneToOne, cascade=CascadeType.ALL)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_KLSK_OBJ", referencedColumnName = "ID")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private Ko koLsk;

    // детализация по лиц.счету
    @OneToOne(mappedBy = "kart", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY, optional = false)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private KartDetail kartDetail;

    // объект Eolink лиц.счета, здесь OneToMany, так как в странном ГИС ЖКХ могут быть лиц.счета с одинаковым LSK и разными GUID
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eolink> eolink = new HashSet<>(0);

    // дом
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", referencedColumnName = "ID")
    private House house;

    // родительский лицевой счет (привязка)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_LSK", referencedColumnName = "LSK")
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
    @Column(name = "FK_ERR")
    private Integer fkErr;

    // наличие ошибки при контроле статусов проживающих, счетчиков и т.п.
    @Column(name = "FACT_METER_TP")
    private Integer factMeterTp;

    // начало действия
    @Column(name = "MG1")
    private String mgFrom;

    // окончание действия
    @Column(name = "MG2")
    private String mgTo;

    // набор услуг
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN) возможно вызывало когда-то OutOfMemory - убрал
    private Set<Nabor> nabor = new HashSet<>(0);

    // набор услуг, в архиве
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN) возможно вызывало когда-то OutOfMemory - убрал
    private Set<Anabor> anabor = new HashSet<>(0);

    // проживающие
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN) // note не включать!!! вызывает N+1!!! использовать entityGraph ред.24.02.21
    private Set<KartPr> kartPr = new HashSet<>(0);

    // текущие начисления
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Charge> charge = new ArrayList<>(0);

    // статус лиц.счета (открыт, закрыт, наличие счетчиков
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StateSch> stateSch = new HashSet<>(0);

    // внешние лиц счета
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KartExt> kartExt = new ArrayList<>(0);

    // перерасчеты
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Change> change = new ArrayList<>(0);

    // корректировки оплатой
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorrectPay> correctPay = new ArrayList<>(0);

    // подготовительная информация для расчета начисления
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargePrep> chargePrep = new ArrayList<>(0);

    // движение по лиц.счету
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargePay> chargepay = new ArrayList<>(0);

    // сальдо
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaldoUsl> saldoUsl = new HashSet<>(0);

    // платеж, заголовок
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Kwtp> kwtp = new HashSet<>(0);

    // платеж, распределение по периодам
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KwtpMg> kwtpMg = new HashSet<>(0);

    // платеж, распределение по услугам, орг.
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KwtpDay> kwtpDay = new HashSet<>(0);

    // задолженность
/*
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<Deb> deb = new ArrayList<>(0);
*/

    // текущая пеня
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PenCur> penCur = new ArrayList<>(0);

    // архивная пеня (долги)
    @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Penya> penya = new HashSet<>(0);

    // кран из системы отопления
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "KRAN1", nullable = false)
    private Boolean isKran1;

    // разделенный в ГИС ЖКХ ЕЛС?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "DIVIDED")
    private Boolean isDivided;

    // ЕЛС ГИС ЖКХ
    @Column(name = "ELSK")
    private String elsk;

    // активный ли лицевой счет?
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
        return Utl.in(psch, 1, 2);
    }

    // наличие счетчика г.в. (решил сделать Transient, так как неудобно обрабатывать NVL в методах)
    @Transient
    public boolean isExistHotWaterMeter() {
        return Utl.in(psch, 1, 3);
    }

    // наличие счетчика эл.эн (решил сделать Transient, так как неудобно обрабатывать NVL в методах)
    @Transient
    public boolean isExistElMeter() {
        return schEl != null && schEl.equals(1);
    }

    /**
     * получить ФИО собственника
     */
    @Transient
    public String getOwnerFIO() {
        return (getKFam() != null ? getKFam() : "") + " "
                + (getKIm() != null ? getKIm() : "") + " "
                + (getKOt() != null ? getKOt() : "");
    }

    /**
     * Получить обрезанную версию номера дома
     */
    @Transient
    public String getNdTrimmed() {
        return Utl.ltrim(getNd(), "0");
    }

    /**
     * Получить обрезанную версию номера дома, без индекса
     */
    @Transient
    public String getNdDigit() {
        String trimNd = Utl.ltrim(getNd(), "0");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(trimNd);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "";
        }
    }

    /**
     * Получить обрезанную версию индекса дома (буквы)
     */
    @Transient
    public String getNdIndex() {
        String trimNd = Utl.ltrim(getNd(), "0");
        Pattern pattern = Pattern.compile("([/\\\\-].+|\\d\\p{L}+)$");
        Matcher matcher = pattern.matcher(trimNd);
        if (matcher.find()) {
            return matcher.group(0).substring(1);
        } else {
            return "";
        }
    }

    /**
     * Получить обрезанную версию номера квартиры
     */
    @Transient
    public String getNumTrimmed() {
        return Utl.ltrim(getNum(), "0");
    }


    /**
     * Получить обрезанную версию номера квартиры, без индекса
     */
    @Transient
    public String getNumDigit() {
        String trimNum = Utl.ltrim(getNum(), "0");
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(trimNum);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "";
        }
    }

    /**
     * Получить обрезанную версию индекса квартиры (буквы)
     */
    @Transient
    public String getNumIndex() {
        String trimNum = Utl.ltrim(getNum(), "0");
        Pattern pattern = Pattern.compile("([/\\\\-].+|\\d\\p{L}+)$");
        Matcher matcher = pattern.matcher(trimNum);
        if (matcher.find()) {
            return matcher.group(0).substring(1);
        } else {
            return "";
        }
    }


    /**
     * Получить адрес по лиц.счету
     */
    @Transient
    public String getAdr() {
        return getSpul().getName() + ", " + getNdTrimmed() +
                (getNumTrimmed().length() > 0 ? (", " + getNumTrimmed()) : "");
    }

    /**
     * Получить адрес с УК по лиц.счету
     */
    @Transient
    public String getAdrWithUk() {
        return getUk().getName() + ", " + getAdr();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kart))
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

