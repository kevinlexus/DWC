package com.dic.bill.mm.impl;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.mm.NaborMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaborMngImpl implements NaborMng {


	/**
	 * Получить действующий набор услуг по данной дате, по квартире
	 * @param ko - квартира
	 * @param curDt - текущая дата (на будущее, для вкл./выкл. услуги в течении месяца)
	 */
	@Override
	public List<Nabor> getValidNabor(Ko ko, Date curDt) {
		// перебрать все открытые лиц счета по квартире, получить все наборы услуг, отсортировать по порядку расчета начисления
		List<Nabor> lst = ko.getKart().stream().filter(k->k.isActual())
				.flatMap(k -> k.getNabor().stream())
				.sorted((Comparator.comparing(o -> o.getUsl().getUslOrder())))
				.collect(Collectors.toList());
		return lst;
	}

	/**
	 * Получить детализированные услуги и расценки
	 * @param kartMain - основной лицевой счет
	 * @param nabor - строка по услуге
	 * @return
	 */
	@Override
	public DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg {
		Kart kart = nabor.getKart();
		DetailUslPrice detail = new DetailUslPrice();
		Usl usl = nabor.getUsl();
		Usl uslOverNorm = usl.getUslOverNorm();
		Usl uslEmpt = usl.getUslEmpt();
		// услуга но норме (обычная)
		detail.price = multiplyPrice(nabor);

		if (uslOverNorm != null) {
			// найти услугу свыше соц.нормы, если есть
			Nabor naborOverNorm = kart.getNabor().stream()
					.filter(t -> t.getUsl().equals(uslOverNorm)).findFirst().orElse(null);
			if (naborOverNorm != null) {
				detail.uslOverNorm = naborOverNorm.getUsl();
				detail.priceOverSoc = multiplyPrice(naborOverNorm);
			} else {
				// не найдено - принять цену такую же как основная
				detail.uslOverNorm = usl;
				detail.priceOverSoc = detail.price;
			}
		}

		if (uslEmpt != null) {
			// найти услугу без проживающих, если есть
			Nabor naborEmpt = kart.getNabor().stream()
					.filter(t -> t.getUsl().equals(uslEmpt)).findFirst().orElse(null);
			if (naborEmpt != null) {
				// найдена услуга без проживающих
				if (nabor.getUsl().getFkCalcTp().equals(31) && kartMain.getStatus().getId().equals(1)) {
					// электроэнергия, - если муниципальная квартира - принять цену по соцнорме
					// взято из C_CHARGE, строка 2024
					detail.uslEmpt = naborEmpt.getUsl();
					detail.priceEmpt = detail.price;
				} else {
					detail.uslEmpt = naborEmpt.getUsl();
					detail.priceEmpt = multiplyPrice(naborEmpt);
				}
			} else {
				// не найдено - принять цену такую же как свыше соц.норма
				if (detail.uslOverNorm != null) {
					detail.uslEmpt = uslOverNorm;
					detail.priceEmpt = detail.priceOverSoc;
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
	private BigDecimal multiplyPrice(Nabor nabor) throws ErrorWhileChrg {
		BigDecimal coeff = Utl.nvl(nabor.getKoeff(), BigDecimal.ZERO);
		BigDecimal norm = Utl.nvl(nabor.getNorm(), BigDecimal.ZERO);
		if (nabor.getUsl().getSptarn().equals(3) ||
				nabor.getUsl().getFkCalcTp()!=null && Utl.in(nabor.getUsl().getFkCalcTp(), 24)) {
			// если поле norm является коэффициентом к расценке или тип расчета услуги fkCalcTp=24
			// (Бред! может поменять потом sptarn на 3 для таких услуг?) FIXME
			coeff=coeff.multiply(norm);
		}
		// получить базовую расценку, зависящую от организации
		Price basePrice = getBasePrice(nabor);
		if (basePrice == null) {
			throw new ErrorWhileChrg("ОШИБКА! Не найдена цена по услуге usl.id="+nabor.getUsl().getId());
		}
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
		for (Price price : nabor.getUsl().getPrice()) {
			if (price.getOrg()!=null && price.getOrg().equals(nabor.getOrg())) {
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