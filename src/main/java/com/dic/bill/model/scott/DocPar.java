package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Документ по параметру объекта (например реестр по загрузке показаний счетчиков)
 */
@Getter
@Setter
@Entity
@DynamicUpdate // так как обновляем не все поля
@Table(name = "T_DOCXPAR", schema = "SCOTT")
public class DocPar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DocPar_id")
    @SequenceGenerator(name = "SEQ_DocPar_id", sequenceName = "scott.T_DOCXPAR_ID", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // CD
    @Column(name = "CD")
    private String cd;

    // дата создания
    @Column(name = "DT_CRT")
    private Date dtCrt;

    // дата обновления
    @Column(name = "DT_UPD")
    private Date updDt;

    // пользователь создавший запись
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER", referencedColumnName = "ID")
    private Tuser tuser;

    // параметры
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_DOC", referencedColumnName = "ID", updatable = false)
    // updatable = false - чтобы не было Update Foreign key
    private List<DocPar> docPar = new ArrayList<>(0);

    // комментарий
    @Column(name = "comm")
    private String comm;

    // установить начальные показания? (для реестра показаний по счетчикам)
    @Type(type= "org.hibernate.type.NumericBooleanType")
    @Column(name = "IS_SET_PREV_VAL")
    private Boolean isSetPreviousVal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof DocPar))
            return false;

        DocPar other = (DocPar) o;

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

    public static final class DocParBuilder {
        // CD
        private String cd;
        // дата создания
        private Date dtCrt;
        // дата обновления
        private Date updDt;
        // пользователь создавший запись
        private Tuser tuser;
        // комментарий
        private String comm;
        // установить начальные показания? (для реестра показаний по счетчикам)
        private Boolean isSetPreviousVal;

        private DocParBuilder() {
        }

        public static DocParBuilder aDocPar() {
            return new DocParBuilder();
        }

        public DocParBuilder withCd(String cd) {
            this.cd = cd;
            return this;
        }

        public DocParBuilder withDtCrt(Date dtCrt) {
            this.dtCrt = dtCrt;
            return this;
        }

        public DocParBuilder withUpdDt(Date updDt) {
            this.updDt = updDt;
            return this;
        }

        public DocParBuilder withTuser(Tuser tuser) {
            this.tuser = tuser;
            return this;
        }

        public DocParBuilder withComm(String comm) {
            this.comm = comm;
            return this;
        }

        public DocParBuilder withIsSetPreviousVal(Boolean isSetPreviousVal) {
            this.isSetPreviousVal = isSetPreviousVal;
            return this;
        }

        public DocPar build() {
            DocPar docPar = new DocPar();
            docPar.setCd(cd);
            docPar.setDtCrt(dtCrt);
            docPar.setUpdDt(updDt);
            docPar.setTuser(tuser);
            docPar.setComm(comm);
            docPar.setIsSetPreviousVal(isSetPreviousVal);
            return docPar;
        }
    }
}

