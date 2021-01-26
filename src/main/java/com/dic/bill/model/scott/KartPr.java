package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
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
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", nullable = false)
    private Kart kart;

    // статус
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS", referencedColumnName = "ID", nullable = false)
    private StatusPr statusPr;

    // родственная связь
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELAT_ID", referencedColumnName = "ID")
    private Relation relation;

    // дата рождения
    @Column(name = "DAT_ROG")
    private Date dtBirdth;

    // дата прописки
    @Column(name = "DAT_PROP")
    private Date dtReg;

    // дата убытия
    @Column(name = "DAT_UB")
    private Date dtUnReg;

    // ф.и.о.
    @Column(name = "FIO")
    private String fio;

    // тип документа
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DOC_TP", referencedColumnName = "ID")
    private Lst docTp;

    // документ: серия
    @Column(name = "DOK_C")
    private String docSeries;

    // документ: номер
    @Column(name = "DOK_N")
    private String docNumber;

    // документ: дата выдачи
    @Column(name = "DOK_D")
    private Date docDtIssued;

    // документ: выдан
    @Column(name = "DOK_V")
    private String docIssued;

    // СНИЛС
    @Column(name = "DOK_SNILS")
    private String snils;

    // использовать информацию о собственнике в разделенном ЕЛС ГИС ЖКХ?
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "USE_GIS_DIVIDE_ELS")
    private Boolean isUseDividedEls;

    @OneToMany(mappedBy = "kartPr", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

