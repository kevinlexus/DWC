package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Распределение платежа по периоду
 *
 * @author lev
 * @version 1.01
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KWTP_MG", schema = "SCOTT")
@Getter
@Setter
public class KwtpMg implements java.io.Serializable {

    public KwtpMg() {
    }

    // Id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KWTP_MG")
    @SequenceGenerator(name = "SEQ_KWTP_MG", sequenceName = "SCOTT.C_KWTP_MG_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK")
    private Kart kart;

    // сумма
    @Column(name = "SUMMA", updatable = false)
    private BigDecimal summa;

    // пеня
    @Column(name = "PENYA", updatable = false)
    private BigDecimal penya;

    // период оплаты
    @Column(name = "DOPL", updatable = false)
    private String dopl;

    // дата платежа
    @Column(name = "DTEK", updatable = false)
    private Date dt;

    // № компьютера
    @Column(name = "NKOM", updatable = false)
    private String nkom;

    // код операции
    @Column(name = "OPER", updatable = false)
    private String oper;

    // № инкассации
    @Column(name = "NINK", updatable = false)
    private Integer nink;

    // дата инкассации
    @Column(name = "DAT_INK", updatable = false)
    private Date dtInk;

    // задолженность на момент распределения по периоду в C_GET_PAY строка 580
    @Column(name = "DEBT", updatable = false)
    private BigDecimal debt;

    // детализация платежа по услугам
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "C_KWTP_ID", referencedColumnName = "ID", updatable = false)
    private List<KwtpDay> KwtpDay = new ArrayList<>(0);

    // заголовок платежа
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_KWTP_ID", referencedColumnName = "ID")
    private Kwtp kwtp;

    // комментарий распределения строки платежа
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_C_KWTP_MG", referencedColumnName = "ID", updatable = false)
    private List<KwtpDayLog> kwtpMgLog = new ArrayList<>(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KwtpMg kwtpMg = (KwtpMg) o;
        return Objects.equals(getId(), kwtpMg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static final class KwtpMgBuilder {
        // Id
        private Integer id;
        // лиц.счет
        private Kart kart;
        // сумма
        private BigDecimal summa;
        // пеня
        private BigDecimal penya;
        // период оплаты
        private String dopl;
        // дата платежа
        private Date dt;
        // № компьютера
        private String nkom;
        // код операции
        private String oper;
        // № инкассации
        private Integer nink;
        // дата инкассации
        private Date dtInk;
        // детализация платежа по услугам
        private List<com.dic.bill.model.scott.KwtpDay> KwtpDay = new ArrayList<>(0);
        // заголовок платежа
        private Kwtp kwtp;

        private KwtpMgBuilder() {
        }

        public static KwtpMgBuilder aKwtpMg() {
            return new KwtpMgBuilder();
        }

        public KwtpMgBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public KwtpMgBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public KwtpMgBuilder withSumma(BigDecimal summa) {
            this.summa = summa;
            return this;
        }

        public KwtpMgBuilder withPenya(BigDecimal penya) {
            this.penya = penya;
            return this;
        }

        public KwtpMgBuilder withDopl(String dopl) {
            this.dopl = dopl;
            return this;
        }

        public KwtpMgBuilder withDt(Date dt) {
            this.dt = dt;
            return this;
        }

        public KwtpMgBuilder withNkom(String nkom) {
            this.nkom = nkom;
            return this;
        }

        public KwtpMgBuilder withOper(String oper) {
            this.oper = oper;
            return this;
        }

        public KwtpMgBuilder withNink(Integer nink) {
            this.nink = nink;
            return this;
        }

        public KwtpMgBuilder withDtInk(Date dtInk) {
            this.dtInk = dtInk;
            return this;
        }

        public KwtpMgBuilder withKwtpDay(List<KwtpDay> KwtpDay) {
            this.KwtpDay = KwtpDay;
            return this;
        }

        public KwtpMgBuilder withKwtp(Kwtp kwtp) {
            this.kwtp = kwtp;
            return this;
        }

        public KwtpMg build() {
            KwtpMg kwtpMg = new KwtpMg();
            kwtpMg.setId(id);
            kwtpMg.setKart(kart);
            kwtpMg.setSumma(summa);
            kwtpMg.setPenya(penya);
            kwtpMg.setDopl(dopl);
            kwtpMg.setDt(dt);
            kwtpMg.setNkom(nkom);
            kwtpMg.setOper(oper);
            kwtpMg.setNink(nink);
            kwtpMg.setDtInk(dtInk);
            kwtpMg.setKwtpDay(KwtpDay);
            kwtpMg.setKwtp(kwtp);
            return kwtpMg;
        }
    }
}

