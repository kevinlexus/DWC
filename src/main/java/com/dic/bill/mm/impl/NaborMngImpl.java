package com.dic.bill.mm.impl;

import com.dic.bill.dto.DetailUslPrice;
import com.dic.bill.mm.NaborMng;
import com.dic.bill.mm.PriceMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PriceMng priceMng;

    /**
     * Получить действующий набор услуг по данной дате, по квартире
     *
     * @param ko    - квартира
     * @param curDt - текущая дата (на будущее, для вкл./выкл. услуги в течении месяца)
     */
    @Override
    public List<Nabor> getValidNabor(Ko ko, Date curDt) {
        // перебрать все открытые лиц счета по квартире, получить все наборы услуг, отсортировать по порядку расчета начисления
        return ko.getKart().stream().filter(Kart::isActual) // только действующие
                .flatMap(k -> k.getNabor().stream())
                .filter(t -> t.isValid() && t.getUsl().isMain())
                .sorted((Comparator.comparing(o -> o.getUsl().getUslOrder())))
                .collect(Collectors.toList());
    }

    /**
     * Тестирование кэша
     *
     * @param str1
     * @param int2
     * @param dt3
     * @return
     */
    @Override
    @Cacheable(cacheNames = "NaborMng.getCached", key = "{#str1, #int2, #dt3}")
    public Integer getCached(String str1, Integer int2, Date dt3) {
        log.info("Зашел в кэш str1={}, int2={}, dt3={}",
                str1, int2, dt3);
        return 5;
    }

    /**
     * Получить детализированные услуги и расценки
     *
     * @param kartMain - основной лицевой счет
     * @param nabor    - строка по услуге
     */
    @Override
/*  нельзя кэшировать изза того что надо найти услугу свыше соц нормы, и там получить коэфф
    @Cacheable(cacheNames="NaborMng.getDetailUslPrice", key="{#kartMain.getStatus(), #nabor.getUsl(), " +
			"#nabor.getOrg(), #nabor.getKoeff(), #nabor.getNorm()}" )
*/
    public DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg {
        Kart kart = nabor.getKart();
        DetailUslPrice detail = new DetailUslPrice();
        Usl usl = nabor.getUsl();
        Usl uslOverSoc = usl.getUslOverSoc();
        Usl uslEmpt = usl.getUslEmpt();
        if (kartMain.isResidental()) {
            // только по жилым, по нежилым - вернуть 0 расценки
            // услуга но норме (обычная)
            detail.price = priceMng.multiplyPrice(nabor, 1);

            if (uslOverSoc != null) {
                // найти услугу свыше соц.нормы, если есть
                Nabor naborOverSoc = kart.getNabor()
                        .stream()
                        .filter(t -> t.getUsl().equals(uslOverSoc)).findFirst().orElse(null);
                if (naborOverSoc != null) {
                    detail.uslOverSoc = naborOverSoc.getUsl();
                    detail.priceOverSoc = priceMng.multiplyPrice(naborOverSoc, 1);
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
                        detail.priceEmpt = priceMng.multiplyPrice(naborEmpt, 1);
                    }
                } else {
                    // не найдено - получить цену из 3 колонки (для 0 зарег. без дополнительной услуги для 0 зарег.)
                    detail.uslEmpt = nabor.getUsl();
                    detail.priceEmpt = priceMng.multiplyPrice(nabor, 3);
                }
            } else {
                // не найдено - получить цену из 3 колонки (для 0 зарег. без дополнительной услуги для 0 зарег.)
                if (detail.uslOverSoc != null) {
                    detail.uslEmpt = detail.uslOverSoc;
                    detail.priceEmpt = detail.priceOverSoc;
                } else {
                    detail.uslEmpt = nabor.getUsl();
                    detail.priceEmpt = priceMng.multiplyPrice(nabor, 3);
                }
            }
        } else {
            // нежилые помещения
            detail.uslOverSoc = nabor.getUsl();
            detail.uslEmpt = nabor.getUsl();
        }
        return detail;
    }

}