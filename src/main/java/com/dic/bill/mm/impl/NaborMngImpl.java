package com.dic.bill.mm.impl;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.mm.NaborMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
		return ko.getKart().stream().filter(t->t.isActual()) // только действующие
				.flatMap(k -> k.getNabor().stream())
				.filter(t->t.isValid() && t.getUsl().isMain())
				.sorted((Comparator.comparing(o -> o.getUsl().getUslOrder())))
				.collect(Collectors.toList());
	}

	/**
	 * Тестирование кэша
	 * @param str1
	 * @param int2
	 * @param dt3
	 * @return
	 */
	@Override
	@Cacheable(cacheNames="NaborMng.getCached", key="{#str1, #int2, #dt3}" )
	public Integer getCached(String str1, Integer int2, Date dt3) {
		log.info("Зашел в кэш str1={}, int2={}, dt3={}",
				str1, int2, dt3);
		return 5;
	}

	/**
	 * Получить детализированные услуги и расценки
	 * @param kartMain - основной лицевой счет
	 * @param nabor - строка по услуге
	 */
	@Override
	@Cacheable(cacheNames="NaborMng.getDetailUslPrice", key="{#kartMain.getStatus(), #nabor.getUsl(), " +
			"#nabor.getOrg(), #nabor.getKoeff(), #nabor.getNorm()}" )
	public DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg {
		log.info("ЗАШЕЛ В МЕТОД, НО НЕ В КЭШ kartMain.getStatus()={}, #nabor.getUsl()={}, " +
				"#nabor.getOrg()={}, #nabor.getKoeff()={}, #nabor.getNorm()={}",
				kartMain.getStatus().getId(), nabor.getUsl().getId(),
				nabor.getOrg().getId(), nabor.getKoeff(), nabor.getNorm());
		Kart kart = nabor.getKart();
		DetailUslPrice detail = new DetailUslPrice();
			Usl usl = nabor.getUsl();
			Usl uslOverSoc = usl.getUslOverSoc();
			Usl uslEmpt = usl.getUslEmpt();
		if (kartMain.isResidental()) {
			// только по жилым, по нежилым - вернуть 0 расценки
			// услуга но норме (обычная)
			detail.price = multiplyPrice(nabor, 1);

			if (uslOverSoc != null) {
				// найти услугу свыше соц.нормы, если есть
				Nabor naborOverSoc = kart.getNabor().stream()
						.filter(t -> t.getUsl().equals(uslOverSoc)).findFirst().orElse(null);
				if (naborOverSoc != null) {
					detail.uslOverSoc = naborOverSoc.getUsl();
					detail.priceOverSoc = multiplyPrice(naborOverSoc, 1);
				} else {
					// не найдено - принять цену такую же как основная
					detail.uslOverSoc = nabor.getUsl();
					detail.priceOverSoc = detail.price;
				}
			} else {
				// принять цену такую же как основная
				detail.uslOverSoc = nabor.getUsl();
				detail.priceOverSoc = detail.price;
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
						detail.priceEmpt = multiplyPrice(naborEmpt, 1);
					}
				} else {
					// не найдено - получить цену из 3 колонки (для 0 зарег. без дополнительной услуги для 0 зарег.)
					detail.uslEmpt = nabor.getUsl();
					detail.priceEmpt = multiplyPrice(nabor, 3);
				}
			} else {
				// не найдено - получить цену из 3 колонки (для 0 зарег. без дополнительной услуги для 0 зарег.)
				if (detail.uslOverSoc != null) {
					detail.uslEmpt = detail.uslOverSoc;
					detail.priceEmpt = detail.priceOverSoc;
				} else {
					detail.uslEmpt = nabor.getUsl();
					detail.priceEmpt = multiplyPrice(nabor, 3);
				}
			}
		} else {
			// нежилые помещения
			detail.uslOverSoc = nabor.getUsl();
			detail.uslEmpt = nabor.getUsl();
		}
		return detail;
	}

	// умножить расценку на коэффициент, округлить

	/**
	 *
	 * @param nabor - строка набора
	 * @param priceColumn - колонку цены, которую использовать
	 * @return - итогововая цена со всеми коэффициентами
	 */
	private BigDecimal multiplyPrice(Nabor nabor, int priceColumn) throws ErrorWhileChrg {
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
		BigDecimal price = BigDecimal.ZERO;
		if (priceColumn == 1) {
			price = basePrice.getPrice();
		} else if (priceColumn == 2) {
			price = basePrice.getPriceAddit();
		} else if (priceColumn == 3) {
			price = basePrice.getPriceEmpt();
		}
		return Utl.nvl(price, BigDecimal.ZERO).multiply(coeff)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Получить базовую запись расценки, зависящей от организации
	 * @param nabor - строка набора услуги
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