package com.dic.bill.mm.impl;

import com.dic.bill.dao.SpkDAO;
import com.dic.bill.dto.CountPers;
import com.dic.bill.dto.SocStandart;
import com.dic.bill.mm.KartPrMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Slf4j
@Service
public class KartPrMngImpl implements KartPrMng {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Autowired
    SpkDAO spkDAO;

    /**
     * Получить кол-во проживающих по лиц.счету и услуге, на дату
     *
     * @param kartMain        - основной лиц.счет
     * @param nabor           - строка набора услуг
     * @param parVarCntKpr    - параметр, тип расчета, 0 - Кис, 1 - Полыс, 2 - ТСЖ
     * @param parCapCalcKprTp - параметр учета проживающих для капремонта (0,1-учёт ВЗ, 2=льготы отключены)
     * @param dt              - дата расчета
     * @param isMeterExist    - наличие счетчика в расчетный день (работает только в КИС)
     */
    @Override
    public CountPers getCountPersByDate(Kart kartMain, Nabor nabor, int parVarCntKpr, int parCapCalcKprTp,
                                        Date dt, boolean isMeterExist) {
        boolean isOwnerOlder70 = false;
        boolean isYanger70 = false;
        boolean isNotOwnerOlder70 = false;
        CountPers countPers = new CountPers();

        // перебрать проживающих
        for (KartPr p : kartMain.getKartPr()) {
            // получить статусы
            int status = 0;
            int statusTemp = 0;

            // счетчики кол-ва строк статусов в периоде, для индикации ошибок
            int statusCnt = 0;
            int statusTempCnt = 0;

            for (StatePr t : p.getStatePr()) {
                if (t.getKartPr().equals(p) && Utl.between(dt, t.getDtFrom(), t.getDtTo())) {
                    if (t.getStatusPr().getTp().getCd().equals("PROP")) {
                        status = t.getStatusPr().getId();
                        statusCnt++;
                    } else if (t.getStatusPr().getTp().getCd().equals("PROP_REG")) {
                        statusTemp = t.getStatusPr().getId();
                        statusTempCnt++;
                    }
                }
            }

            // контроль ошибок наличия дублей статусов
            if (statusCnt > 1 || statusTempCnt > 1) {
                kartMain.setFkErr(2);
            }
            if (parVarCntKpr == 0) {
                // Киселёвск
                if ((status == 4 || status == 0) && statusTemp == 3) {
                    // выписан или пустой основной статус и "врем.зарег." или "для начисления"
                    countPers.kpr++;
                    countPers.kprWr++;
                    countPers.kprNorm++;
                } else if ((status == 4 || status == 0) && statusTemp == 6) {
                    // выписан или пустой основной статус и "врем.зарег." или "для начисления"
                    countPers.kprWr++;
                    countPers.kprNorm++;
                } else if ((status == 1 || status == 5) && (statusTemp == 0)) {
                    // прописан или статус=для_начисления без доп.статусов
                    // "врем.зарег." или "для начисления"
                    countPers.kpr++;
                    countPers.kprNorm++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && statusTemp == 2) {
                    // прописан или статус=для_начисления и временно отсут.
                    if (nabor.getUsl().isHousing()) {
                        // жилищная услуга
                        countPers.kpr++;
                    } else if (Utl.in(nabor.getUsl().getFkCalcTp(), 17, 18, 19, 55, 56, 57)) {
                        // х.в., г.в., водоотвед - КИС согласно ТЗ на 22.10.2019
                        // расценка по ВО - на уровне для населения
                        countPers.kpr++;
                    }
                    countPers.kprOt++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && (statusTemp == 3 || statusTemp == 6)) {
                    // прописан или статус=для_начисления без доп.статусов или ошибочные статусы
                    // "врем.зарег." или "для начисления"
                    countPers.kpr++;
                    countPers.kprNorm++;
                }
            } else if (parVarCntKpr == 1) {
                // Полысаево
                if ((status == 4 || status == 0) && statusTemp == 3) {
                    // выписан или пустой основной статус и "врем.зарег."
                    if (!nabor.getUsl().isHousing()) {
                        // коммунальная услуга
                        countPers.kpr++;
                        countPers.kprNorm++;
                    }
                    countPers.kprWr++;
                    //countPers.kprMax++;
                } else if ((status == 4 || status == 0) && statusTemp == 6) {
                    // выписан или пустой основной статус и "для начисления"
                    countPers.kprNorm++;
                } else if ((status == 1 || status == 5) && statusTemp == 0) {
                    // прописан или статус=для_начисления без доп.статусов
                    countPers.kpr++;
                    countPers.kprNorm++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && statusTemp == 2) {
                    // прописан или статус=для_начисления и временно отсут.
                    countPers.kpr++;
                    countPers.kprOt++;
                    //countPers.kprMax++;
                    if (nabor.getUsl().isHousing() ||
                            Utl.in(nabor.getUsl().getFkCalcTp(), 17, 18, 19, 55, 56, 57)) {
                        // жилищная услуга или х.в., г.в., кан. ред.25.10.2019
                        countPers.kprNorm++;
                    }
                } else if ((status == 1 || status == 5) && (statusTemp == 3 || statusTemp == 6)) {
                    // прописан или статус=для_начисления или временно зарег. (ошибка, не бывает такого)
                    countPers.kpr++;
                    countPers.kprNorm++;
                    //countPers.kprMax++;
                }
            } else if (parVarCntKpr == 2) {
                // ТСЖ
                if ((status == 4 || status == 0) && (statusTemp == 3 || statusTemp == 6)) {
                    // выписан или пустой основной статус и для_начисления или временно зарег.
                    if (!nabor.getUsl().isHousing()) {
                        // коммунальная услуга
                        countPers.kprNorm++;
                    }
                    countPers.kpr++;
                    countPers.kprWr++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && (statusTemp == 0)) {
                    // прописан или статус=для_начисления без доп.статусов
                    countPers.kpr++;
                    countPers.kprNorm++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && statusTemp == 2) {
                    // прописан или статус=для_начисления и временно отсут.
                    countPers.kpr++;
                    countPers.kprOt++;
                    //countPers.kprMax++;
                } else if ((status == 1 || status == 5) && (statusTemp == 3 || statusTemp == 6)) {
                    // прописан или статус=для_начисления и временно зарег. (ошибка, не бывает такого)
                    countPers.kpr++;
                    countPers.kprNorm++;
                    //countPers.kprMax++;
                }
            }

            if (nabor.getUsl().getFkCalcTp().equals(37) && parCapCalcKprTp != 2) {
                // Капремонт
                // получить возраст на дату расчета, если дата рождения пустая - поставить текущую

                int yearsOld = Utl.getDiffYears(p.getDtBirdth() == null ? dt : p.getDtBirdth(), dt);
                if (yearsOld >= 70) {
                    if (status == 1) { // ПЗ
                        if (p.getRelation() != null && Utl.in(p.getRelation().getCd(), "Квартиросъемщик", "Собственник")) {
                            isOwnerOlder70 = true;
                        } else {
                            isNotOwnerOlder70 = true;
                        }
                    }
                } else {
                    // Код взят из C_KART строка 745
                    if (status == 1) { // ПЗ
                        isYanger70 = true;
                    } else if (parCapCalcKprTp == 0 &&
                            (status == 4 || status == 0) && statusTemp == 3) { // ВЗ
                        isYanger70 = true;
                    }
                }
            }
        }

        if (nabor.getUsl().getFkCalcTp().equals(37) && parCapCalcKprTp != 2) {
            // капремонт, если старше 70 и собственник и нет других проживающих, моложе 70
            if (isOwnerOlder70 && !isYanger70) {
                Spk spk;
                if (!isNotOwnerOlder70) {
                    // льгота одинокому собственнику, старше 70 лет
                    spk = spkDAO.getByCd("PENS_SINGLE_70");
                } else {
                    // льгота собственнику, старше 70 лет, проживающему совместно с другими НЕ собственниками, старше 70 лет
                    spk = spkDAO.getByCd("PENS_70_WITH_70");
                }
                countPers.capPriv = spk;
            }
        }
        return countPers;
    }

    /**
     * Получить объем по нормативу на всех проживающих
     *
     * @param factVol   факт.объем услуги, для установления соотношения нормы/сверхнормы
     * @param nabor     строка услуги из набора
     * @param countPers объект, содержащий кол-во проживающих
     */
    @Override
    public SocStandart getSocStdtVol(BigDecimal factVol, Nabor nabor, CountPers countPers) throws ErrorWhileChrg {
        SocStandart socStandart = new SocStandart();
        BigDecimal norm;
        socStandart.vol = BigDecimal.ZERO;
        switch (nabor.getUsl().getFkCalcTp()) {
            case 17: // х.в.
            case 18: // г.в.
            case 19: // водоотв.
            case 55: // х.в. с соц.н./свыше
            case 56: // г.в. с соц.н./свыше
            case 57: // водоотв. с соц.н./свыше
            case 52: // ХВС для гвс
            case 53: // компонент тепл.энерг.для г.в.
            {
                // соцнорма из набора
                norm = Utl.nvl(nabor.getNorm(), BigDecimal.ZERO);
                break;
            }
            case 54: // отопление гкал. по норме/свыше
            case 25: {  // тек содерж
                norm = getCommonSocStdt(countPers);
                break;
            }
            case 31: { // эл.эн
                // соцнорма по справочнику
                norm = getElectrSocStdt(countPers);
                break;
            }
            default: {
                throw new ErrorWhileChrg("ОШИБКА! По услуге fkCalcTp=" + nabor.getUsl().getFkCalcTp() +
                        " не определён блок switch в KartPrMngImpl.getSocStdtVol");
            }
        }

        // кол-во прож. * соцнорму
        socStandart.norm = norm;
        socStandart.vol = norm.multiply(BigDecimal.valueOf(countPers.kprNorm));

        socStandart.procNorm = BigDecimal.ONE;
        // отопление гкал. по норме/свыше, х.в. г.в., водоотв.
        if (!countPers.isEmpty && factVol.compareTo(BigDecimal.ZERO)>0 && Utl.in(nabor.getUsl().getFkCalcTp(), 54, 55, 56, 57)) {
            BigDecimal proc = socStandart.vol.divide(factVol, 10, RoundingMode.HALF_UP);
            if (proc.compareTo(BigDecimal.ONE) < 1) {
                socStandart.procNorm = proc;
            }
        }

        return socStandart;
    }

    /**
     * Получить соцнорму по отоплению, тек.содержанию, по справочнику
     *
     * @param countPers - DTO кол-ва проживающих
     */
    private BigDecimal getCommonSocStdt(CountPers countPers) {
        BigDecimal socNorm;
        switch (countPers.kprNorm) {
            case 0: { // 0 проживающих - берется соцнорма на 1 человека
                socNorm = new BigDecimal("33");
                break;
            }
            case 1: {
                socNorm = new BigDecimal("33");
                break;
            }
            case 2: {
                socNorm = new BigDecimal("21");
                break;
            }
            default: {
                socNorm = new BigDecimal("18");
                break;
            }
        }
        return socNorm;
    }

    /**
     * Получить соцнорму по электроэнергии, по справочнику
     *
     * @param countPers - DTO кол-ва проживающих
     * @return
     */
    private BigDecimal getElectrSocStdt(CountPers countPers) {
        BigDecimal socNorm;
        switch (countPers.kprNorm) {
            case 1: {
                socNorm = new BigDecimal("130");
                break;
            }
            case 2:
            case 3: {
                socNorm = new BigDecimal("100");
                break;
            }
            case 4: {
                socNorm = new BigDecimal("87.5");
                break;
            }
            case 5: {
                socNorm = new BigDecimal("80");
                break;
            }
            default: {
                socNorm = new BigDecimal("75");
                break;
            }
        }
        return socNorm;
    }


}