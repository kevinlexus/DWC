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

    // период задолженности
    @Column(name = "PERIOD_DEB")
    private String periodDeb;

    // сумма задолженности
    @Column(name = "SUMMA")
    private BigDecimal summa;

    // комментарий по загрузке
    @Column(name = "COMM")
    private String comm;

    // статус (0 - принять к загрузке, 1 - уже загружен, 2 - ошибка (смотреть COMM)
    @Column(name = "STATUS")
    private Integer status;

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
        // период задолженности
        private String periodDeb;
        // сумма задолженности
        private BigDecimal summa;
        // комментарий по загрузке
        private String comm;
        // статус (0 - принять к загрузке, 1 - уже загружен, 2 - ошибка (смотреть COMM)
        private Integer status;

        private LoadKartExtBuilder() {
        }

        public static LoadKartExtBuilder aLoadKartExt() {
            return new LoadKartExtBuilder();
        }

        public LoadKartExtBuilder withKart(Kart kart) {
            this.kart = kart;
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

        public LoadKartExtBuilder withPeriodDeb(String periodDeb) {
            this.periodDeb = periodDeb;
            return this;
        }

        public LoadKartExtBuilder withSumma(BigDecimal summa) {
            this.summa = summa;
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

        public LoadKartExt build() {
            LoadKartExt loadKartExt = new LoadKartExt();
            loadKartExt.setKart(kart);
            loadKartExt.setExtLsk(extLsk);
            loadKartExt.setGuid(guid);
            loadKartExt.setFio(fio);
            loadKartExt.setAddress(address);
            loadKartExt.setCode(code);
            loadKartExt.setNm(nm);
            loadKartExt.setPeriodDeb(periodDeb);
            loadKartExt.setSumma(summa);
            loadKartExt.setComm(comm);
            loadKartExt.setStatus(status);
            return loadKartExt;
        }
    }
}


