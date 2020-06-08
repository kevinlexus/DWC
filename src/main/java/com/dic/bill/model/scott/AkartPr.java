package com.dic.bill.model.scott;

import com.dic.bill.Compress;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Проживающий (архив)
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_KART_PR2", schema = "SCOTT")
@Getter
@Setter
public class AkartPr implements java.io.Serializable, Compress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_A_KartPr2_id")
    @SequenceGenerator(name = "SEQ_A_KartPr2_id", sequenceName = "scott.a_kart_pr2_id", allocationSize = 1)
    @Column(name = "REC_ID", updatable = false, nullable = false)
    private Integer id;

    // ID проживающего
    @Column(name = "id", nullable = false)
    private Integer idPerson;

    // Начало действия записи
    @Column(name = "mgfrom", nullable = false)
    private Integer mgFrom;

    // Начало действия записи
    @Column(name = "mgto", nullable = false)
    private Integer mgTo; // Начало действия записи

    // лиц.счет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LSK", referencedColumnName="LSK")
    private Kart kart;

    // Ф.И.О.
    @Column(name = "fio")
    private String fio;

    // статус
    @Column(name = "status")
    private Integer status;

    // дата рождения
    @Column(name = "dat_rog")
    private Date dtBirth;

    // пол
    @Column(name = "pol")
    private Integer pol;

    // документ
    @Column(name = "dok")
    private String doc;

    // документ - серия
    @Column(name = "dok_c")
    private String docSer;

    // документ - номер
    @Column(name = "dok_n")
    private String docNum;

    // документ - дата выдачи
    @Column(name = "dok_d")
    private Date docDt;

    // документ - выдан
    @Column(name = "dok_v")
    private String docIssued;

    // документ - код подразделения
    @Column(name = "dok_div")
    private String docDiv;

    // документ - ИНН
    @Column(name = "dok_inn")
    private String docInn;

    // дата регистрации
    @Column(name = "dat_prop")
    private Date dtReg;

    // дата убытия
    @Column(name = "dat_ub")
    private Date dtUnreg;

    // родственная связь
    @Column(name = "relat_id")
    private Integer relatId;

    // дата действия статуса (до) (для врем. отсут, врем зарег)
    @Column(name = "status_dat")
    private Date dtStatus;

    // дата изменения статуса
    @Column(name = "status_chng")
    private Date dtChng;

    // фамилия
    @Column(name = "k_fam")
    private String fam;

    // имя
    @Column(name = "k_im")
    private String im;

    // отчество
    @Column(name = "k_ot")
    private String ot;

    // тип документа
    @Column(name = "fk_doc_tp")
    private Integer fkDocTp;

    // национальность
    @Column(name = "fk_nac")
    private Integer fkNac;

    // место рождения
    @Column(name = "b_place")
    private String birthPlace;

    // откуда:страна
    @Column(name = "fk_frm_cntr")
    private Integer fkFrmCntr;

    // откуда:область
    @Column(name = "fk_frm_regn")
    private Integer fkFrmRegion;

    // откуда:район
    @Column(name = "fk_frm_distr")
    private Integer fkFrmDistr;

    // откуда:нас.пункт
    @Column(name = "frm_town")
    private String frmTown;

    // откуда:дата прибытия
    @Column(name = "frm_dat")
    private Date dtFrom;

    // откуда:код улицы
    @Column(name = "fk_frm_kul")
    private String fkFrmKul;

    // откуда:№ дома
    @Column(name = "frm_nd")
    private String frmNd;

    // откуда:№ кв.
    @Column(name = "frm_kw")
    private String frmKw;

    // место работы и должность
    @Column(name = "w_place")
    private String workPlace;

    // причина выписки
    @Column(name = "fk_ub")
    private Integer fkUb;

    // откуда:страна
    @Column(name = "fk_to_cntr")
    private Integer fkToCntr;

    // куда:область
    @Column(name = "fk_to_regn")
    private Integer fkToRegion;

    // куда:район
    @Column(name = "fk_to_distr")
    private Integer fkToDist;

    // куда:нас.пункт
    @Column(name = "to_town")
    private String toTown;

    // куда:код улицы
    @Column(name = "fk_to_kul")
    private String fkToKul;

    // куда:№ дома
    @Column(name = "to_nd")
    private String toNd;

    // куда:№ кв.
    @Column(name = "to_kw")
    private String toKw;

    // гражданство
    @Column(name = "fk_citiz")
    private Integer fkCitiz;

    // военнообязанность
    @Column(name = "fk_milit")
    private Integer fkMilit;

    // военкомат
    @Column(name = "fk_milit_regn")
    private Integer fkMilitRegn;

    // дата начала действия статуса (для врем. отсут, врем зарег)
    @Column(name = "status_datb")
    private Date dtStatusBegin;

    // организация-задолжник
    @Column(name = "fk_deb_org")
    private Integer fkDebOrg;

    // доля приватизированной площади в STR (например 1/5)
    @Column(name = "priv_proc")
    private String privProc;

    // СНИЛС
    @Column(name = "dok_snils")
    private String dokSnils;

    // использовать информацию для привязки к разделенному лиц.счету (1-да, 0-нет)
    @Column(name = "use_gis_divide_els")
    private Integer useGisDivideEls;


    @Override
    public boolean isTheSame(Compress compr) {
        AkartPr akartPr = (AkartPr) compr;

        if (idPerson != null ? !idPerson.equals(akartPr.idPerson) : akartPr.idPerson != null) return false;
        if (kart != null ? !kart.equals(akartPr.kart) : akartPr.kart != null) return false;
        if (fio != null ? !fio.equals(akartPr.fio) : akartPr.fio != null) return false;
        if (status != null ? !status.equals(akartPr.status) : akartPr.status != null) return false;
        if (dtBirth != null ? !dtBirth.equals(akartPr.dtBirth) : akartPr.dtBirth != null) return false;
        if (pol != null ? !pol.equals(akartPr.pol) : akartPr.pol != null) return false;
        if (doc != null ? !doc.equals(akartPr.doc) : akartPr.doc != null) return false;
        if (docSer != null ? !docSer.equals(akartPr.docSer) : akartPr.docSer != null) return false;
        if (docNum != null ? !docNum.equals(akartPr.docNum) : akartPr.docNum != null) return false;
        if (docDt != null ? !docDt.equals(akartPr.docDt) : akartPr.docDt != null) return false;
        if (docIssued != null ? !docIssued.equals(akartPr.docIssued) : akartPr.docIssued != null) return false;
        if (docDiv != null ? !docDiv.equals(akartPr.docDiv) : akartPr.docDiv != null) return false;
        if (docInn != null ? !docInn.equals(akartPr.docInn) : akartPr.docInn != null) return false;
        if (dtReg != null ? !dtReg.equals(akartPr.dtReg) : akartPr.dtReg != null) return false;
        if (dtUnreg != null ? !dtUnreg.equals(akartPr.dtUnreg) : akartPr.dtUnreg != null) return false;
        if (relatId != null ? !relatId.equals(akartPr.relatId) : akartPr.relatId != null) return false;
        if (dtStatus != null ? !dtStatus.equals(akartPr.dtStatus) : akartPr.dtStatus != null) return false;
        if (dtChng != null ? !dtChng.equals(akartPr.dtChng) : akartPr.dtChng != null) return false;
        if (fam != null ? !fam.equals(akartPr.fam) : akartPr.fam != null) return false;
        if (im != null ? !im.equals(akartPr.im) : akartPr.im != null) return false;
        if (ot != null ? !ot.equals(akartPr.ot) : akartPr.ot != null) return false;
        if (fkDocTp != null ? !fkDocTp.equals(akartPr.fkDocTp) : akartPr.fkDocTp != null) return false;
        if (fkNac != null ? !fkNac.equals(akartPr.fkNac) : akartPr.fkNac != null) return false;
        if (birthPlace != null ? !birthPlace.equals(akartPr.birthPlace) : akartPr.birthPlace != null) return false;
        if (fkFrmCntr != null ? !fkFrmCntr.equals(akartPr.fkFrmCntr) : akartPr.fkFrmCntr != null) return false;
        if (fkFrmRegion != null ? !fkFrmRegion.equals(akartPr.fkFrmRegion) : akartPr.fkFrmRegion != null) return false;
        if (fkFrmDistr != null ? !fkFrmDistr.equals(akartPr.fkFrmDistr) : akartPr.fkFrmDistr != null) return false;
        if (frmTown != null ? !frmTown.equals(akartPr.frmTown) : akartPr.frmTown != null) return false;
        if (dtFrom != null ? !dtFrom.equals(akartPr.dtFrom) : akartPr.dtFrom != null) return false;
        if (fkFrmKul != null ? !fkFrmKul.equals(akartPr.fkFrmKul) : akartPr.fkFrmKul != null) return false;
        if (frmNd != null ? !frmNd.equals(akartPr.frmNd) : akartPr.frmNd != null) return false;
        if (frmKw != null ? !frmKw.equals(akartPr.frmKw) : akartPr.frmKw != null) return false;
        if (workPlace != null ? !workPlace.equals(akartPr.workPlace) : akartPr.workPlace != null) return false;
        if (fkUb != null ? !fkUb.equals(akartPr.fkUb) : akartPr.fkUb != null) return false;
        if (fkToCntr != null ? !fkToCntr.equals(akartPr.fkToCntr) : akartPr.fkToCntr != null) return false;
        if (fkToRegion != null ? !fkToRegion.equals(akartPr.fkToRegion) : akartPr.fkToRegion != null) return false;
        if (fkToDist != null ? !fkToDist.equals(akartPr.fkToDist) : akartPr.fkToDist != null) return false;
        if (toTown != null ? !toTown.equals(akartPr.toTown) : akartPr.toTown != null) return false;
        if (fkToKul != null ? !fkToKul.equals(akartPr.fkToKul) : akartPr.fkToKul != null) return false;
        if (toNd != null ? !toNd.equals(akartPr.toNd) : akartPr.toNd != null) return false;
        if (toKw != null ? !toKw.equals(akartPr.toKw) : akartPr.toKw != null) return false;
        if (fkCitiz != null ? !fkCitiz.equals(akartPr.fkCitiz) : akartPr.fkCitiz != null) return false;
        if (fkMilit != null ? !fkMilit.equals(akartPr.fkMilit) : akartPr.fkMilit != null) return false;
        if (fkMilitRegn != null ? !fkMilitRegn.equals(akartPr.fkMilitRegn) : akartPr.fkMilitRegn != null) return false;
        if (dtStatusBegin != null ? !dtStatusBegin.equals(akartPr.dtStatusBegin) : akartPr.dtStatusBegin != null)
            return false;
        if (fkDebOrg != null ? !fkDebOrg.equals(akartPr.fkDebOrg) : akartPr.fkDebOrg != null) return false;
        if (privProc != null ? !privProc.equals(akartPr.privProc) : akartPr.privProc != null) return false;
        if (dokSnils != null ? !dokSnils.equals(akartPr.dokSnils) : akartPr.dokSnils != null) return false;
        return useGisDivideEls != null ? useGisDivideEls.equals(akartPr.useGisDivideEls) : akartPr.useGisDivideEls == null;

    }

    // сравнивать по Id зарегистрированного
    @Override
    public String getKey() {
        return getIdPerson().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AkartPr))
            return false;

        AkartPr other = (AkartPr)o;

        if (id == other.getId()) return true;
        if (id == null) return false;

        // equivalence by id
        return id.equals(other.getId());
    }

    @Override
    public int getHash() {
        int result = idPerson != null ? idPerson.hashCode() : 0;
        result = 31 * result + (kart.getLsk() != null ? kart.getLsk().hashCode() : 0);
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dtBirth != null ? dtBirth.hashCode() : 0);
        result = 31 * result + (pol != null ? pol.hashCode() : 0);
        result = 31 * result + (doc != null ? doc.hashCode() : 0);
        result = 31 * result + (docSer != null ? docSer.hashCode() : 0);
        result = 31 * result + (docNum != null ? docNum.hashCode() : 0);
        result = 31 * result + (docDt != null ? docDt.hashCode() : 0);
        result = 31 * result + (docIssued != null ? docIssued.hashCode() : 0);
        result = 31 * result + (docDiv != null ? docDiv.hashCode() : 0);
        result = 31 * result + (docInn != null ? docInn.hashCode() : 0);
        result = 31 * result + (dtReg != null ? dtReg.hashCode() : 0);
        result = 31 * result + (dtUnreg != null ? dtUnreg.hashCode() : 0);
        result = 31 * result + (relatId != null ? relatId.hashCode() : 0);
        result = 31 * result + (dtStatus != null ? dtStatus.hashCode() : 0);
        result = 31 * result + (dtChng != null ? dtChng.hashCode() : 0);
        result = 31 * result + (fam != null ? fam.hashCode() : 0);
        result = 31 * result + (im != null ? im.hashCode() : 0);
        result = 31 * result + (ot != null ? ot.hashCode() : 0);
        result = 31 * result + (fkDocTp != null ? fkDocTp.hashCode() : 0);
        result = 31 * result + (fkNac != null ? fkNac.hashCode() : 0);
        result = 31 * result + (birthPlace != null ? birthPlace.hashCode() : 0);
        result = 31 * result + (fkFrmCntr != null ? fkFrmCntr.hashCode() : 0);
        result = 31 * result + (fkFrmRegion != null ? fkFrmRegion.hashCode() : 0);
        result = 31 * result + (fkFrmDistr != null ? fkFrmDistr.hashCode() : 0);
        result = 31 * result + (frmTown != null ? frmTown.hashCode() : 0);
        result = 31 * result + (dtFrom != null ? dtFrom.hashCode() : 0);
        result = 31 * result + (fkFrmKul != null ? fkFrmKul.hashCode() : 0);
        result = 31 * result + (frmNd != null ? frmNd.hashCode() : 0);
        result = 31 * result + (frmKw != null ? frmKw.hashCode() : 0);
        result = 31 * result + (workPlace != null ? workPlace.hashCode() : 0);
        result = 31 * result + (fkUb != null ? fkUb.hashCode() : 0);
        result = 31 * result + (fkToCntr != null ? fkToCntr.hashCode() : 0);
        result = 31 * result + (fkToRegion != null ? fkToRegion.hashCode() : 0);
        result = 31 * result + (fkToDist != null ? fkToDist.hashCode() : 0);
        result = 31 * result + (toTown != null ? toTown.hashCode() : 0);
        result = 31 * result + (fkToKul != null ? fkToKul.hashCode() : 0);
        result = 31 * result + (toNd != null ? toNd.hashCode() : 0);
        result = 31 * result + (toKw != null ? toKw.hashCode() : 0);
        result = 31 * result + (fkCitiz != null ? fkCitiz.hashCode() : 0);
        result = 31 * result + (fkMilit != null ? fkMilit.hashCode() : 0);
        result = 31 * result + (fkMilitRegn != null ? fkMilitRegn.hashCode() : 0);
        result = 31 * result + (dtStatusBegin != null ? dtStatusBegin.hashCode() : 0);
        result = 31 * result + (fkDebOrg != null ? fkDebOrg.hashCode() : 0);
        result = 31 * result + (privProc != null ? privProc.hashCode() : 0);
        result = 31 * result + (dokSnils != null ? dokSnils.hashCode() : 0);
        result = 31 * result + (useGisDivideEls != null ? useGisDivideEls.hashCode() : 0);
        return result;
    }
}

