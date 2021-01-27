package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Временная сущность, для загрузки внешнего лиц.счета (например Чистый город)
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LOAD_KART_EXT", schema = "SCOTT")
@Getter
@Setter
public class LoadKartExt implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOAD_KART_EXT")
    @SequenceGenerator(name = "SEQ_LOAD_KART_EXT", sequenceName = "SCOTT.LOAD_KART_EXT_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;

    // лиц.счет в системе "Директ"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK")
    private Kart kart;

    // Ko фин лиц.счета
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_ID", referencedColumnName = "ID", updatable = false)
    private Ko koKw;

    // Ko помещения
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KLSK_PREMISE", referencedColumnName = "ID", updatable = false)
    private Ko koPremise;

    // уникальный лиц.счет во внешней системе
    @Column(name = "EXT_LSK")
    private String extLsk;

    // GUID из ФИАС
    @Column(name = "GUID")
    private String guid;

    // ФИО абонента во внешней системе
    @Column(name = "FIO")
    private String fio;

    // адрес
    @Column(name = "ADDRESS")
    private String address;

    // код услуги
    @Column(name = "CODE")
    private Integer code;

    // наименование услуги
    @Column(name = "NM")
    private String nm;

    // № помещения (квартиры)
    @Column(name = "KW")
    private String kw;

    // период задолженности
    @Column(name = "PERIOD_DEB")
    private String periodDeb;

    // вх.остаток
    @Column(name = "INSAL")
    private BigDecimal insal;

    // исх.остаток (сумма задолженности)
    @Column(name = "SUMMA")
    private BigDecimal summa;

    // начислено
    @Column(name = "CHRG")
    private BigDecimal chrg;

    // оплачено
    @Column(name = "PAYMENT")
    private BigDecimal payment;

    // комментарий по загрузке
    @Column(name = "COMM")
    private String comm;

    // статус (0 - принять к загрузке, 1 - уже загружен, 2 - ошибка (смотреть COMM)
    @Column(name = "STATUS")
    private Integer status;

    // расчетный счет, для выставления счетов на оплату (используется в REP_BILLS_COMPOUND)
    @Column(name = "RASCHET_SCHET")
    private String rSchet;

    // расчетный счет, используемая колнока в t_org (1-RASCHET_SCHET,2-RASCHET_SCHET2)
    @Column(name = "RASCHET_SCHET_COLUMN")
    private Integer rSchetColumn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadKartExt kwtp = (LoadKartExt) o;
        return Objects.equals(getId(), kwtp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public static final class LoadKartExtBuilder {
        // лиц.счет в системе "Директ"
        private Kart kart;
        // Ko фин лиц.счета
        private Ko koKw;
        // Ko помещения
        private Ko koPremise;
        // уникальный лиц.счет во внешней системе
        private String extLsk;
        // GUID из ФИАС
        private String guid;
        // ФИО абонента во внешней системе
        private String fio;
        // адрес
        private String address;
        // код услуги
        private Integer code;
        // наименование услуги
        private String nm;
        // № помещения (квартиры)
        private String kw;
        // период задолженности
        private String periodDeb;
        // вх.остаток
        private BigDecimal insal;
        // исх.остаток (сумма задолженности)
        private BigDecimal summa;
        // начислено
        private BigDecimal chrg;
        // оплачено
        private BigDecimal payment;
        // комментарий по загрузке
        private String comm;
        // статус (0 - принять к загрузке, 1 - уже загружен, 2 - ошибка (смотреть COMM)
        private Integer status;
        // расчетный счет, для выставления счетов на оплату (используется в REP_BILLS_COMPOUND)
        private String rSchet;
        // расчетный счет, используемая колнока в t_org (1-RASCHET_SCHET,2-RASCHET_SCHET2)
        private Integer rSchetColumn;

        private LoadKartExtBuilder() {
        }

        public static LoadKartExtBuilder aLoadKartExt() {
            return new LoadKartExtBuilder();
        }

        public LoadKartExtBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public LoadKartExtBuilder withKoKw(Ko koKw) {
            this.koKw = koKw;
            return this;
        }

        public LoadKartExtBuilder withKoPremise(Ko koPremise) {
            this.koPremise = koPremise;
            return this;
        }

        public LoadKartExtBuilder withExtLsk(String extLsk) {
            this.extLsk = extLsk;
            return this;
        }

        public LoadKartExtBuilder withGuid(String guid) {
            this.guid = guid;
            return this;
        }

        public LoadKartExtBuilder withFio(String fio) {
            this.fio = fio;
            return this;
        }

        public LoadKartExtBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public LoadKartExtBuilder withCode(Integer code) {
            this.code = code;
            return this;
        }

        public LoadKartExtBuilder withNm(String nm) {
            this.nm = nm;
            return this;
        }

        public LoadKartExtBuilder withKw(String kw) {
            this.kw = kw;
            return this;
        }

        public LoadKartExtBuilder withPeriodDeb(String periodDeb) {
            this.periodDeb = periodDeb;
            return this;
        }

        public LoadKartExtBuilder withInsal(BigDecimal insal) {
            this.insal = insal;
            return this;
        }

        public LoadKartExtBuilder withSumma(BigDecimal summa) {
            this.summa = summa;
            return this;
        }

        public LoadKartExtBuilder withChrg(BigDecimal chrg) {
            this.chrg = chrg;
            return this;
        }

        public LoadKartExtBuilder withPayment(BigDecimal payment) {
            this.payment = payment;
            return this;
        }

        public LoadKartExtBuilder withComm(String comm) {
            this.comm = comm;
            return this;
        }

        public LoadKartExtBuilder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public LoadKartExtBuilder withRSchet(String rSchet) {
            this.rSchet = rSchet;
            return this;
        }

        public LoadKartExtBuilder withRSchetColumn(Integer rSchetColumn) {
            this.rSchetColumn = rSchetColumn;
            return this;
        }

        public LoadKartExt build() {
            LoadKartExt loadKartExt = new LoadKartExt();
            loadKartExt.setKart(kart);
            loadKartExt.setKoKw(koKw);
            loadKartExt.setKoPremise(koPremise);
            loadKartExt.setExtLsk(extLsk);
            loadKartExt.setGuid(guid);
            loadKartExt.setFio(fio);
            loadKartExt.setAddress(address);
            loadKartExt.setCode(code);
            loadKartExt.setNm(nm);
            loadKartExt.setKw(kw);
            loadKartExt.setPeriodDeb(periodDeb);
            loadKartExt.setInsal(insal);
            loadKartExt.setSumma(summa);
            loadKartExt.setChrg(chrg);
            loadKartExt.setPayment(payment);
            loadKartExt.setComm(comm);
            loadKartExt.setStatus(status);
            loadKartExt.setRSchet(rSchet);
            loadKartExt.setRSchetColumn(rSchetColumn);
            return loadKartExt;
        }
    }
}


