package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Проживающий
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_KART_PR", schema = "SCOTT")
@Getter
@Setter
public class KartPr implements java.io.Serializable {

    public KartPr() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KartPr_id")
    @SequenceGenerator(name = "SEQ_KartPr_id", sequenceName = "scott.kart_pr_id", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false, nullable = false)
    private Kart kart;

    // статус
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS", referencedColumnName = "ID", updatable = false, nullable = false)
    private StatusPr statusPr;

    // родственная связь
    @ManyToOne(fetch = FetchType.LAZY) // сделал EAGER - иногда приводит к LazyInitialisationException ред. 06.03.2019
    @JoinColumn(name = "RELAT_ID", referencedColumnName = "ID", updatable = false, nullable = true)
    private Relation relation;

    // дата рождения
    @Column(name = "DAT_ROG", updatable = false, nullable = true)
    private Date dtBirdth;

    // дата прописки
    @Column(name = "DAT_PROP", updatable = false, nullable = true)
    private Date dtReg;

    // дата убытия
    @Column(name = "DAT_UB", updatable = false, nullable = true)
    private Date dtUnReg;

    // ф.и.о.
    @Column(name = "FIO", updatable = false, nullable = true)
    private String fio;

    // тип документа
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DOC_TP", referencedColumnName = "ID", updatable = false)
    private Lst docTp;

    // документ: серия
    @Column(name = "DOK_C", updatable = false)
    private String docSeries;

    // документ: номер
    @Column(name = "DOK_N", updatable = false)
    private String docNumber;

    // документ: дата выдачи
    @Column(name = "DOK_D", updatable = false)
    private Date docDtIssued;

    // документ: выдан
    @Column(name = "DOK_V", updatable = false)
    private String docIssued;

    // СНИЛС
    @Column(name = "DOK_SNILS", updatable = false)
    private String snils;

    // использовать информацию о собственнике в разделенном ЕЛС ГИС ЖКХ?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "USE_GIS_DIVIDE_ELS", updatable = false)
    private Boolean isUseDividedEls;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_KART_PR", referencedColumnName = "ID", updatable = false)
    // updatable = false - чтобы не было Update Foreign key
    private List<StatePr> statePr = new ArrayList<>(0);

    // использовать ли документы проживающего для разделения ЕЛС в ГИС ЖКХ?
    @Transient
    public boolean isUseDocForDividedEls() {
        if (snils != null || (docTp != null && docSeries != null && docNumber != null && docDtIssued != null)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof KartPr))
            return false;

        KartPr other = (KartPr) o;

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

