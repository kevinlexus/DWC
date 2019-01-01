package com.dic.bill.mm.impl;

import com.dic.bill.dto.CountPers;
import com.dic.bill.mm.KartPrMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class KartPrMngImpl implements KartPrMng {

	@PersistenceContext
	private EntityManager em;


	/**
	 * Получить кол-во проживающих по лиц.счету и услуге, на дату
	 * @param kart - лиц.счет
	 * @param parVarCntKpr - параметр, тип расчета, 0 - Кис, 1 - Полыс, 2 - ТСЖ
	 * @param lstState - история статусов проживающих
	 * @param usl - услуга
	 * @param dt - дата расчета
	 */
	@Override
	public CountPers getCountPersByDate(Kart kart, Double parVarCntKpr, List<StatePr> lstState,
										Usl usl, Date dt) {
		// выполнить подстановку лицевого счета
		Kart foundKart;
		if (Utl.in(kart.getTp().getCd(), "LSK_TP_RSO", "LSK_TP_ADDIT") && kart.getParentKart() != null) {
			foundKart = kart.getParentKart();
		} else {
			foundKart = kart;
		}

		CountPers countPers = new CountPers();
		// перебрать проживающих
		for (KartPr p : foundKart.getKartPr()) {
			// получить статусы
			int status = 0;
			int statusTemp = 0;
			for (StatePr t : lstState) {
				if (t.getKartPr().equals(p) && Utl.between(dt, t.getDtFrom(), t.getDtTo())) {
					if (t.getStatusPr().getTp().getCd().equals("PROP")) {
						status=t.getStatusPr().getId();
					} else if (t.getStatusPr().getTp().getCd().equals("PROP_REG")) {
						statusTemp=t.getStatusPr().getId();
					}
				}
			}

			if (parVarCntKpr.equals(0D)) {
				// Киселёвск
				if ((status==4 || status==0) && statusTemp==3) {
					// выписан или пустой основной статус и "врем.зарег." или "для начисления"
					countPers.kpr++;
					countPers.kprWr++;
					countPers.kprNorm++;
				} else if ((status==4 || status==0) && statusTemp==6) {
					// выписан или пустой основной статус и "врем.зарег." или "для начисления"
					countPers.kprWr++;
					countPers.kprNorm++;
				} else if ((status==1 || status==5) && (statusTemp==0)) {
					// прописан или статус=для_начисления без доп.статусов
					// "врем.зарег." или "для начисления"
					countPers.kpr++;
					countPers.kprNorm++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					if (usl.isHousing()) {
						// жилищная услуга
						countPers.kpr++;
					}
					countPers.kprOt++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления без доп.статусов или ошибочные статусы
					// "врем.зарег." или "для начисления"
					countPers.kpr++;
					countPers.kprNorm++;
				}
			} else if (parVarCntKpr.equals(1D)) {
				// Полысаево
				if ((status==4 || status==0) && statusTemp==3) {
					// выписан или пустой основной статус и "врем.зарег."
					if (!usl.isHousing()) {
						// коммунальная услуга
						countPers.kpr++;
						countPers.kprNorm++;
					}
					countPers.kprWr++;
					countPers.kprMax++;
				} else if ((status==4 || status==0) && statusTemp==6) {
					// выписан или пустой основной статус и "для начисления"
					countPers.kprNorm++;
				} else if ((status==1 || status==5) && statusTemp==0) {
					// прописан или статус=для_начисления без доп.статусов
					countPers.kpr++;
					countPers.kprNorm++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					countPers.kpr++;
					countPers.kprOt++;
					countPers.kprMax++;
					if (usl.isHousing()) {
						// жилищная услуга
						countPers.kprNorm++;
					}
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления или временно зарег. (ошибка, не бывает такого)
					countPers.kpr++;
					countPers.kprNorm++;
					countPers.kprMax++;
				}
			} else if (parVarCntKpr.equals(2D)) {
				// ТСЖ
				if ((status==4 || status==0) && (statusTemp==3 || statusTemp==6)) {
					// выписан или пустой основной статус и для_начисления или временно зарег.
					if (!usl.isHousing()) {
						// коммунальная услуга
						countPers.kprNorm++;
					}
					countPers.kpr++;
					countPers.kprWr++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && (statusTemp==0)) {
					// прописан или статус=для_начисления без доп.статусов
					countPers.kpr++;
					countPers.kprNorm++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					countPers.kpr++;
					countPers.kprOt++;
					countPers.kprMax++;
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления и временно зарег. (ошибка, не бывает такого)
					countPers.kpr++;
					countPers.kprNorm++;
					countPers.kprMax++;
				}
			}

		}

		// дополнительно установка кол-во проживающих для объема
		// алгоритм взят из C_KART, строка 786
		if (countPers.kprNorm == 0) {
			if (Utl.in(kart.getTp().getCd(), "LSK_TP_RSO")) {
				// в РСО счетах
				if (parVarCntKpr.equals(0D)
						&& countPers.kprOt == 0 && !kart.getStatus().getCd().equals("MUN")) {
					// Киселёвск, нет временно отсутствующих и не муниципальный лиц.счет
					// поставить хоть одного проживающего, для объема
					countPers.kprNorm=1;
				}
			} else {
				// в Основных и прочих счетах
				if (parVarCntKpr.equals(0D)) {
					// Киселёвск
					if (usl.getFkCalcTp().equals(49) && !kart.getStatus().getCd().equals("MUN")) {
						// услуга по обращению с ТКО
						// не муницип. квартира
						countPers.kpr=1;
						countPers.kprNorm=1;
					}
				}

				if (parVarCntKpr.equals(1D) && countPers.kprOt == 0) {
					// Полысаево
					// поставить хоть одного проживающего, для объема
					countPers.kprNorm=1;
				}
			}
		}

		countPers.isEmpty = countPers.kpr == 0;
		return countPers;
	}

	/**
	 * Получить объем по нормативу
	 * @param nabor - строка услуги из набора
	 * @param countPers - объект, содержащий кол-во проживающих
	 * @return
	 */
	@Override
	public BigDecimal getSocStdtVol(Nabor nabor, CountPers countPers) {
		BigDecimal norm = BigDecimal.ZERO;
		BigDecimal socNorm = BigDecimal.ZERO;
		switch (nabor.getUsl().getFkCalcTp()) {
			case 17: // х.в.
			case 18: // г.в.
			case 19: {// водоотв.
				// соцнорма из набора
				socNorm = Utl.nvl(nabor.getNorm(), BigDecimal.ZERO);
				break;
			}
			case 31: { // эл.эн
				// соцнорма по справочнику
				socNorm = getElectrSocStdt(countPers);
				break;
			}
			case 11111111: {  // TODO отопление гкал (ТСЖ), тек содерж ТСЖ?
				socNorm = getCommonSocStdt(countPers);
				break;
			}
		}

		// кол-во прож. * соцнорму
		norm = socNorm.multiply(BigDecimal.valueOf(countPers.kprNorm));
		return norm;
	}

	/**
	 * Получить соцнорму по отоплению, тек.содержанию, по справочнику
	 * @param countPers - DTO кол-ва проживающих
	 * @return
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
				socNorm = new BigDecimal("20");
				break;
			}
		}
		return socNorm;
	}

	/**
	 * Получить соцнорму по электроэнергии, по справочнику
	 * @param countPers - DTO кол-ва проживающих
	 * @return
	 */
	private BigDecimal getElectrSocStdt(CountPers countPers) {
		BigDecimal socNorm;
		switch (countPers.kprNorm) {
            case 1 : {
                socNorm = new BigDecimal("130");
                break;
            }
            case 2 :
            case 3 : {
                socNorm = new BigDecimal("100");
                break;
            }
            case 4 : {
                socNorm = new BigDecimal("87.5");
                break;
            }
            case 5 : {
                socNorm = new BigDecimal("80");
                break;
            }
            default : {
                socNorm = new BigDecimal("75");
                break;
            }
        }
        return socNorm;
	}

}