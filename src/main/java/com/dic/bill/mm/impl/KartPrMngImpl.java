package com.dic.bill.mm.impl;

import com.dic.bill.dao.StatesPrDAO;
import com.dic.bill.dto.CountPers;
import com.dic.bill.mm.KartPrMng;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.KartPr;
import com.dic.bill.model.scott.StatePr;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
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

	@PersistenceContext
	private EntityManager em;


	/**
	 * Получить кол-во проживающих по лиц.счету и услуге, на дату
	 * @param kart - лиц.счет
	 * @param parVarCntKpr - параметр, тип расчета, 0 - Кис, 1 - Полыс, 2 - ТСЖ
	 * @param lstState - история статусов проживающих
	 * @param usl - услуга
	 * @param dt - дата расчета
	 * @throws WrongParam
	 */
	@Override
	public CountPers getCountPersByDate(Kart kart, Double parVarCntKpr, List<StatePr> lstState,
										Usl usl, Date dt) {
		CountPers countPers = new CountPers();
		// перебрать проживающих
		for (KartPr p : kart.getKartPr()) {
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
		countPers.isEmpty = countPers.kpr==0? true:false;
		return countPers;
	}

}