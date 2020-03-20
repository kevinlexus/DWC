package com.dic.bill.model.scott;

import com.dic.bill.model.exs.Eolink;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Лицевой счет (он же - помещение)
 *
 * @author lev
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
    @Column(name = "LSK", updatable = false, nullable = false)
    private String lsk;

    // УК
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REU", referencedColumnName = "REU")
    private Org uk;

    // код улицы (повторяемый столбец, так как есть Spul!)
    @Column(name = "KUL", insertable = false, updatable = false, nullable = false)
    private String kul;

    // номер дома
    @Column(name = "ND", updatable = false, nullable = false)
    private String nd;

    // номер помещения
    @Column(name = "KW", updatable = false, nullable = false)
    private String num;

    // фамилия
    @Column(name = "K_FAM", updatable = false, nullable = false)
    private String kFam;

    // имя
    @Column(name = "K_IM", updatable = false, nullable = false)
    private String kIm;

    // отчество
    @Column(name = "K_OT", updatable = false, nullable = false)
    private String kOt;

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

    // улица
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KUL", referencedColumnName = "ID", nullable = false, updatable = false)
    private Spul spul;

    // Ko финансового лиц.счета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "K_LSK_ID", referencedColumnName = "ID", updatable = false)
    // updatable = false - чтобы не было Update Foreign key
    private Ko koKw;

    // Ko помещения
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_PREMISE", referencedColumnName = "ID", updatable = false)
    private Ko koPremise;

    // Ko лиц.счета (здесь OneToOne, cascade=CascadeType.ALL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_OBJ", referencedColumnName = "ID", updatable = false)
    private Ko koLsk;

    // детализация по лиц.счету
    @OneToOne(mappedBy = "kart", fetch = FetchType.LAZY)
    private KartDetail kartDetail;

    // объект Eolink лиц.счета, здесь OneToMany, так как в странном ГИС ЖКХ могут быть лиц.счета с одинаковым LSK и разными GUID
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<Eolink> eolink = new HashSet<>(0);

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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN) возможно приводит к утечке памяти (до 700 тыс объектов, при расчете по всем УК) ред.06.03.2019
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<Nabor> nabor = new HashSet<>(0);

    // проживающие
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
// updatable = false - чтобы не было Update Foreign key
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

    // задолженность
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<Deb> deb = new ArrayList<>(0);

    // текущая пеня
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private List<PenCur> penCur = new ArrayList<>(0);

    // архивная пеня (долги)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<Penya> penya = new HashSet<>(0);

    // кран из системы отопления
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "KRAN1", updatable = false, nullable = false)
    private Boolean isKran1;

    // список коротких наименований услуг, по лиц.счету  (для удобного представления счета при оплате) обновляется при начислении
    @Column(name = "USL_NAME_SHORT")
    private String uslNameShort;

    // статус лиц.счета (открыт, закрыт, наличие счетчиков
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false)
    private Set<StateSch> stateSch = new HashSet<>(0);

    // разделенный в ГИС ЖКХ ЕЛС?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "DIVIDED", updatable = false)
    private Boolean isDivided;

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

