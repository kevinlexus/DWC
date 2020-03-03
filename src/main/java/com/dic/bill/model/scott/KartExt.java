package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Внешний лиц.счет (например Чистый город)
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART_EXT", schema = "SCOTT")
@Getter
@Setter
public class KartExt implements java.io.Serializable {

    public KartExt() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KART_EXT")
    @SequenceGenerator(name = "SEQ_KART_EXT", sequenceName = "SCOTT.KART_EXT_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;

    // лиц.счет в системе "Директ"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK")
    private Kart kart;

    // Уникальный лиц.счет во внешней системе
    @Column(name = "EXT_LSK", unique = true, updatable = false)
    private String extLsk;

    // ФИО абонента во внешней системе
    @Column(name = "fio")
    private String fio;

    // дата создания
    @Column(name = "DT_CRT", updatable = false)
    private Date dtCrt;

    // дата обновления
    @Column(name = "DT_UPD")
    private Date updDt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KartExt kwtp = (KartExt) o;
        return Objects.equals(getId(), kwtp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static final class KartExtBuilder {
        // лиц.счет в системе "Директ"
        private Kart kart;
        // Уникальный лиц.счет во внешней системе
        private String extLsk;
        // ФИО абонента во внешней системе
        private String fio;
        // дата создания
        private Date dtCrt;
        // дата обновления
        private Date updDt;

        private KartExtBuilder() {
        }

        public static KartExtBuilder aKartExt() {
            return new KartExtBuilder();
        }

        public KartExtBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public KartExtBuilder withExtLsk(String extLsk) {
            this.extLsk = extLsk;
            return this;
        }

        public KartExtBuilder withFio(String fio) {
            this.fio = fio;
            return this;
        }

        public KartExtBuilder withDtCrt(Date dtCrt) {
            this.dtCrt = dtCrt;
            return this;
        }

        public KartExtBuilder withUpdDt(Date updDt) {
            this.updDt = updDt;
            return this;
        }

        public KartExt build() {
            KartExt kartExt = new KartExt();
            kartExt.setKart(kart);
            kartExt.setExtLsk(extLsk);
            kartExt.setFio(fio);
            kartExt.setDtCrt(dtCrt);
            kartExt.setUpdDt(updDt);
            return kartExt;
        }
    }
}
