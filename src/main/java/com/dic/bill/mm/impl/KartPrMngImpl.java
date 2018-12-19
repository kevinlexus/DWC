package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.dao.StatesPrDAO;
import com.dic.bill.dto.CountPers;
import com.dic.bill.mm.KartMng;
import com.dic.bill.mm.KartPrMng;
import com.dic.bill.mm.SprParamMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class KartPrMngImpl implements KartPrMng {

	@Autowired
	private StatesPrDAO statesPrDao;
	@Autowired
	private SprParamMng sprParamMng;

	@PersistenceContext
	private EntityManager em;


	/**
	 * Получить кол-во проживающих по лиц.счету и услуге, на дату
	 * @param kart - лиц.счет
	 * @param lstNabor - набор услуг
	 * @param dt - дата выборки
	 * @return
	 */
	@Override
	public void getCountPersByDate(Kart kart, Double parVarCntKpr, List<StatePr> lstState,
								   Usl usl, Date dt) throws WrongParam {
		// параметр подсчета кол-во проживающих (0-для кис, 1-Полыс., 1 - для ТСЖ (пока, может поправить)
		//Double parVarCntKpr = sprParamMng.getN1("VAR_CNT_KPR");
		CountPers countPers = new CountPers();
		for (KartPr p : kart.getKartPr()) {

			// получить статусы
			int status = 0;
			int statusTemp = 0;
			for (StatePr t : lstState) {
				if (Utl.between(dt, t.getDtFrom(), t.getDtTo())) {
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
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprWr(countPers.getKprWr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
				} else if ((status==4 || status==0) && statusTemp==6) {
					// выписан или пустой основной статус и "врем.зарег." или "для начисления"
					countPers.setKprWr(countPers.getKprWr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && (statusTemp==0)) {
					// прописан или статус=для_начисления без доп.статусов
					// "врем.зарег." или "для начисления"
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					if (usl.isHousing()) {
						// жилищная услуга
						countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					}
					countPers.setKprOt(countPers.getKprOt().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления без доп.статусов или ошибочные статусы
					// "врем.зарег." или "для начисления"
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
				}
			} else if (parVarCntKpr.equals(1D)) {
				// Полысаево
				if ((status==4 || status==0) && statusTemp==3) {
					// выписан или пустой основной статус и "врем.зарег."
					if (!usl.isHousing()) {
						// коммунальная услуга
						countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
						countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					}
					countPers.setKprWr(countPers.getKprWr().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==4 || status==0) && statusTemp==6) {
					// выписан или пустой основной статус и "для начисления"
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && statusTemp==0) {
					// прописан или статус=для_начисления без доп.статусов
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprOt(countPers.getKprOt().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
					if (usl.isHousing()) {
						// жилищная услуга
						countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					}
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления или временно зарег. (ошибка, не бывает такого)
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				}
			} else if (parVarCntKpr.equals(2D)) {
				// ТСЖ
				if ((status==4 || status==0) && (statusTemp==3 || statusTemp==6)) {
					// выписан или пустой основной статус и для_начисления или временно зарег.
					if (!usl.isHousing()) {
						// коммунальная услуга
						countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					}
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprWr(countPers.getKprWr().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && (statusTemp==0)) {
					// прописан или статус=для_начисления без доп.статусов
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && statusTemp==2) {
					// прописан или статус=для_начисления и временно отсут.
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprOt(countPers.getKprOt().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				} else if ((status==1 || status==5) && (statusTemp==3 || statusTemp==6)) {
					// прописан или статус=для_начисления и временно зарег. (ошибка, не бывает такого)
					countPers.setKpr(countPers.getKpr().add(BigDecimal.valueOf(1)));
					countPers.setKprNorm(countPers.getKprNorm().add(BigDecimal.valueOf(1)));
					countPers.setKprMax(countPers.getKprMax().add(BigDecimal.valueOf(1)));
				}
			}

		}
	}

//	private CountPers

}