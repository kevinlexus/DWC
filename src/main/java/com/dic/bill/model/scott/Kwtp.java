package com.dic.bill.model.scott;

import com.dic.bill.model.exs.Pdoc;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//import com.dic.bill.model.exs.Pdoc;

/**
 * Платеж, заголовок
 *
 * @author lev
 * @version 1.01
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KWTP", schema = "SCOTT")
@Getter
@Setter
public class Kwtp implements java.io.Serializable {

    public Kwtp() {
    }

    // Id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KWTP")
    @SequenceGenerator(name = "SEQ_KWTP", sequenceName = "SCOTT.C_KWTP_ID", allocationSize = 1)
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

    // дата платежа
    @Column(name = "DTEK", updatable = false)
    private Date dt;

    // распределение платежа по услугам, организациям
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_KWTP_ID", referencedColumnName = "ID")
    private List<KwtpMg> kwtpMg = new ArrayList<>(0);

    // № платежного документа (из банка, из ГИС ЖКХ)
    @Column(name = "NUM_DOC", updatable = false)
    private String numDoc;

    // период оплаты - на этом уровне не используется, оставлено для совместимости
    @Column(name = "DOPL", updatable = false)
    private String dopl;

    // № компьютера
    @Column(name = "NKOM", updatable = false)
    private String nkom;

    // № инкассации
    @Column(name = "NINK", updatable = false)
    private Integer nink;

    // дата инкассации
    @Column(name = "DAT_INK", updatable = false)
    private Date dtInk;

    // код операции
    @Column(name = "OPER", updatable = false)
    private String oper;

    // ПД из ГИС ЖКХ
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PDOC", referencedColumnName = "ID")
    private Pdoc pdoc;

    // аннулировано?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "ANNUL")
    private Boolean isAnnul;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kwtp kwtp = (Kwtp) o;
        return getId().equals(kwtp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public static final class KwtpBuilder {
        // Id
        private Integer id;
        // лиц.счет
        private Kart kart;
        // сумма
        private BigDecimal summa;
        // пеня
        private BigDecimal penya;
        // дата платежа
        private Date dt;
        // распределение платежа по услугам, организациям
        private List<KwtpMg> kwtpMg = new ArrayList<>(0);
        // № платежного документа (из банка, из ГИС ЖКХ)
        private String numDoc;
        // период оплаты - на этом уровне не используется, оставлено для совместимости
        private String dopl;
        // № компьютера
        private String nkom;
        // № инкассации
        private Integer nink;
        // дата инкассации
        private Date dtInk;
        // код операции
        private String oper;
        // ПД из ГИС ЖКХ
        private Pdoc pdoc;
        // аннулировано?
        private Boolean isAnnul;

        private KwtpBuilder() {
        }

        public static KwtpBuilder aKwtp() {
            return new KwtpBuilder();
        }

        public KwtpBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public KwtpBuilder withKart(Kart kart) {
            this.kart = kart;
            return this;
        }

        public KwtpBuilder withSumma(BigDecimal summa) {
            this.summa = summa;
            return this;
        }

        public KwtpBuilder withPenya(BigDecimal penya) {
            this.penya = penya;
            return this;
        }

        public KwtpBuilder withDt(Date dt) {
            this.dt = dt;
            return this;
        }

        public KwtpBuilder withKwtpMg(List<KwtpMg> kwtpMg) {
            this.kwtpMg = kwtpMg;
            return this;
        }

        public KwtpBuilder withNumDoc(String numDoc) {
            this.numDoc = numDoc;
            return this;
        }

        public KwtpBuilder withDopl(String dopl) {
            this.dopl = dopl;
            return this;
        }

        public KwtpBuilder withNkom(String nkom) {
            this.nkom = nkom;
            return this;
        }

        public KwtpBuilder withNink(Integer nink) {
            this.nink = nink;
            return this;
        }

        public KwtpBuilder withDtInk(Date dtInk) {
            this.dtInk = dtInk;
            return this;
        }

        public KwtpBuilder withOper(String oper) {
            this.oper = oper;
            return this;
        }

        public KwtpBuilder withPdoc(Pdoc pdoc) {
            this.pdoc = pdoc;
            return this;
        }

        public KwtpBuilder withIsAnnul(Boolean isAnnul) {
            this.isAnnul = isAnnul;
            return this;
        }

        public Kwtp build() {
            Kwtp kwtp = new Kwtp();
            kwtp.setId(id);
            kwtp.setKart(kart);
            kwtp.setSumma(summa);
            kwtp.setPenya(penya);
            kwtp.setDt(dt);
            kwtp.setKwtpMg(kwtpMg);
            kwtp.setNumDoc(numDoc);
            kwtp.setDopl(dopl);
            kwtp.setNkom(nkom);
            kwtp.setNink(nink);
            kwtp.setDtInk(dtInk);
            kwtp.setOper(oper);
            kwtp.setPdoc(pdoc);
            kwtp.setIsAnnul(isAnnul);
            return kwtp;
        }
    }
}

