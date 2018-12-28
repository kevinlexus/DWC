package com.dic.bill.mm.impl;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.mm.NaborMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NaborMngImpl implements NaborMng {


	/**
	 * Получить действующий набор услуг по данной дате
	 * @param kart - лиц.счет
	 * @param curDt - текущая дата (на будущее, для вкл./выкл. услуги в течении месяца)
	 */
	@Override
	public List<Nabor> getValidNabor(Kart kart, Date curDt) {
		List<Nabor> lstNabor = new ArrayList<>(10);
		for (Nabor t : kart.getNabor()) {
			if (t.isValid()) {
				// добавить действующую услугу
				lstNabor.add(t);
			}
		}
		return lstNabor;
	}

	/**
	 * Получить детализированные услуги и расценки
	 * @param lst - список всех услуг в лиц.счете
	 * @param nabor - строка по услуге
	 * @return
	 */
	@Override
	public DetailUslPrice getDetailUslPrice(List<Nabor> lst, Nabor nabor) {
		DetailUslPrice detail = new DetailUslPrice();
		Usl usl = nabor.getUsl();
		Usl uslOverNorm = usl.getUslOverNorm();
		Usl uslEmpt = usl.getUslEmpt();
		// услуга но норме (обычная)
		detail.price = multiplyPrice(nabor);

		if (uslOverNorm != null) {
			// найти услугу свыше соц.нормы, если есть
			Nabor naborOverNorm = lst.stream()
					.filter(t -> t.getUsl().equals(uslOverNorm)).findFirst().orElse(null);
			if (naborOverNorm != null) {
				detail.uslOverNorm = naborOverNorm.getUsl();
				detail.priceOverNorm = multiplyPrice(naborOverNorm);
			} else {
				// не найдено - принять цену такую же как основная
				detail.uslOverNorm = usl;
				detail.priceOverNorm = detail.price;
			}
		}

		if (uslEmpt != null) {
			// найти услугу свыше соц.нормы, если есть
			Nabor naborEmpt = lst.stream()
					.filter(t -> t.getUsl().equals(uslOverNorm)).findFirst().orElse(null);
			detail.priceEmpt = multiplyPrice(naborEmpt);
			if (naborEmpt != null) {
				detail.uslEmpt = naborEmpt.getUsl();
				detail.priceOverNorm = multiplyPrice(naborEmpt);
			} else {
				// не найдено - принять цену такую же как свыше соц.норма
				if (detail.uslOverNorm != null) {
					detail.uslEmpt = uslOverNorm;
					detail.priceEmpt = detail.priceOverNorm;
				} else {
					// пустая услуга свыше соцнормы, принять как по соцнорме
					detail.uslEmpt = usl;
					detail.priceEmpt = detail.price;
				}
			}
		}
		return detail;
	}

	// умножить расценку на коэффициент
	private BigDecimal multiplyPrice(Nabor nabor) {
		BigDecimal coeff = Utl.nvl(nabor.getKoeff(), BigDecimal.ZERO);
		BigDecimal norm = Utl.nvl(nabor.getNorm(), BigDecimal.ZERO);
		if (nabor.getUsl().getSptarn().equals(3)) {
			// если поле norm является коэффициентом к расценке
			coeff=coeff.multiply(norm);
		}
		// получить базовую расценку, зависящую от организации
		Price basePrice = getBasePrice(nabor);
		// рассчитать цену
		return Utl.nvl(basePrice.getPrice(), BigDecimal.ZERO).multiply(coeff);
	}

	/**
	 * Получить базовую запись расценки, зависящей от организации
	 * @param nabor - строка набора услуги
	 * @return
	 */
	private Price getBasePrice(Nabor nabor) {
		Price foundPrice = null;
		for (Price price : nabor.getPrice()) {
			if (price.getOrg().equals(nabor.getOrg())) {
				return price;
			} else {
				foundPrice=price;
			}
		}
		// не найдено по организации, вернуть без организации
		return foundPrice;
	}

	/**
	 * Получить строку nabor по услуге
	 * @param lst - список услуг в лиц.счете
	 * @param usl - выбранная главная услуга
	 * @return
	 */
/*
	@Override
	public Nabor getNabor(List<Nabor> lst, Usl usl) {
		for (Nabor nabor : lst) {
			if (nabor.getUsl().equals(usl)) {
				return nabor;
			}
		}
		return null;
	}
*/

}