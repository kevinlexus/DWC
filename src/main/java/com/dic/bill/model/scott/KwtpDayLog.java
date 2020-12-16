package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Лог распределения платежа из C_KWTP_MG
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KWTP_DAY_LOG", schema = "SCOTT")
@Getter
@Setter
public class KwtpDayLog implements java.io.Serializable {

    public KwtpDayLog() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KWTP_DAY_LOG")
    @SequenceGenerator(name = "SEQ_KWTP_DAY_LOG", sequenceName = "SCOTT.KWTP_DAY_LOG_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;


    // fk на C_KWTP_MG - сделано, так как не возможно видеть KwtpMg на этапе вставки записи из пакета PL/SQL
    @Column(name = "FK_C_KWTP_MG", insertable = false, updatable = false)
    private Integer fkKwtpMg;

    // № п.п.
    @Column(name = "NPP")
    private Integer npp;

    // распределение платежа по периоду
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_C_KWTP_MG", referencedColumnName = "ID")
    private KwtpMg kwtpMg;

    // комментарий распределения
    @Column(name = "TEXT", updatable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KwtpDayLog kwtpMg = (KwtpDayLog) o;
        return Objects.equals(getId(), kwtpMg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public static final class KwtpDayLogBuilder {
        // fk на C_KWTP_MG - сделано, так как не возможно видеть KwtpMg на этапе вставки записи из пакета PL/SQL
        private Integer fkKwtpMg;
        // № п.п.
        private Integer npp;
        // распределение платежа по периоду
        private KwtpMg kwtpMg;
        // комментарий распределения
        private String text;

        private KwtpDayLogBuilder() {
        }

        public static KwtpDayLogBuilder aKwtpDayLog() {
            return new KwtpDayLogBuilder();
        }

        public KwtpDayLogBuilder withFkKwtpMg(Integer fkKwtpMg) {
            this.fkKwtpMg = fkKwtpMg;
            return this;
        }

        public KwtpDayLogBuilder withNpp(Integer npp) {
            this.npp = npp;
            return this;
        }

        public KwtpDayLogBuilder withKwtpMg(KwtpMg kwtpMg) {
            this.kwtpMg = kwtpMg;
            return this;
        }

        public KwtpDayLogBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public KwtpDayLog build() {
            KwtpDayLog kwtpDayLog = new KwtpDayLog();
            kwtpDayLog.setFkKwtpMg(fkKwtpMg);
            kwtpDayLog.setNpp(npp);
            kwtpDayLog.setKwtpMg(kwtpMg);
            kwtpDayLog.setText(text);
            return kwtpDayLog;
        }
    }
}

