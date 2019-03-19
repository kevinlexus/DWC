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
     * Вернуть ввод по списку действующих лицевых-услуг
     * @param lstNabor - список услуг
     * @param usl - услуга
     */
    @Override
    public Vvod getVvod(List<Nabor> lstNabor, Usl usl) {
        return lstNabor.stream()
                .filter(t -> t.getVvod() != null
                        && t.getVvod().getUsl() != null
                        && t.getVvod().getUsl().equals(usl))
                .map(Nabor::getVvod)
                .findFirst()
                .orElse(null);
    }

    /**
     * Вернуть тип распределения по вводу
     * @param lstNabor - список услуг
     * @param usl - услуга
     */
    @Override
    public Integer getVvodDistTp(List<Nabor> lstNabor, Usl usl) {
        Vvod vvod = getVvod(lstNabor, usl);
        if (vvod != null && vvod.getDistTp() != null) {
            return vvod.getDistTp();
        } else {
            // не найден ввод
            return -1;
        }
    }

    /**
     * Получить детализированные услуги и расценки
     *
     * @param kartMain - основной лицевой счет
     * @param nabor    - строка по услуге
     */
    @Override
/*    public DetailUslPrice getDetailUslPrice(Kart kartMain, Nabor nabor) throws ErrorWhileChrg {
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
                Nabor naborOverSoc = null;
                for (Nabor t : kart.getNabor()) {
                    if (t.getUsl().equals(uslOverSoc)) {
                        naborOverSoc = t;
                    }
                }
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
//                Nabor naborEmpt = kart.getNabor().stream()
//                        .filter(t -> t.getUsl().equals(uslEmpt)).findFirst().orElse(null);
                Nabor naborEmpt = null;
                for (Nabor t : kart.getNabor()) {
                    if (t.getUsl().equals(uslEmpt)) {
                        naborEmpt = t;
                    }
                }
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
    }*/
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
        if (kartMain.isResidental() && !usl.getFkCalcTp().equals(34)) {
            // только по жилым, по нежилым и повыш.коэфф для Полыс. - вернуть 0 расценки
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
                    detail.uslOverSoc = usl;
                    detail.priceOverSoc = detail.price;
                }
            } else {
                // принять цену такую же как основная
                detail.uslOverSoc = usl;
                detail.priceOverSoc = detail.price;
            }

            if (uslEmpt != null) {
                // найти услугу без проживающих в наборе, если есть
                Nabor naborEmpt = kart.getNabor().stream()
                        .filter(t -> t.getUsl().equals(uslEmpt)).findFirst().orElse(null);
                if (naborEmpt != null) {
                    // найдена услуга без проживающих
                    if (usl.getFkCalcTp().equals(31) && kartMain.getStatus().getId().equals(1)) {
                        // электроэнергия, - если муниципальная квартира - принять цену по соцнорме
                        // взято из C_CHARGE, строка 2024
                        detail.uslEmpt = naborEmpt.getUsl();
                        detail.priceEmpt = detail.price;
                    } else {
                        detail.uslEmpt = naborEmpt.getUsl();
                        detail.priceEmpt = priceMng.multiplyPrice(naborEmpt, 1);
                    }
                } else if (usl.getFkCalcTp().equals(18)) {
                    // г.в. - не найдено в наборе услуги без прожив.- занулить цену
                    detail.uslEmpt = usl;
                    detail.priceEmpt = BigDecimal.ZERO;
                } else {
                    // не найдено в наборе - получить цену из 3 колонки (для 0 зарег. без дополнительной услуги для 0 зарег.)
                    detail.uslEmpt = usl;
                    detail.priceEmpt = priceMng.multiplyPrice(nabor, 3);
                }
            } else {
                // не найдено в наборе
                if (usl.getId().equals("132")/* || usl.getFkCalcTp().equals(34)*/) {
                    // особый расчет цены
                    detail.uslEmpt = usl;
                    detail.priceEmpt = priceMng.multiplyPrice(nabor, 3);
                } else if (Utl.in(usl.getFkCalcTp(), 12, 24, 25, 32, 36)) {
                    // особый расчет цены
                    detail.uslEmpt = usl;
                    detail.priceEmpt = detail.price;
                } else if (detail.uslOverSoc != null && !usl.equals(uslOverSoc)) {
                    // услуга св.соц. НЕ та же что и основная - получить цену из услуги св.соц.
                    detail.uslEmpt = detail.uslOverSoc;
                    detail.priceEmpt = detail.priceOverSoc;
                } else {
                    // прочие случаи - получить цену из 3 колонки
                    detail.uslEmpt = usl;
                    detail.priceEmpt = priceMng.multiplyPrice(nabor, 3);
                }
            }
        } else {
            // нежилые помещения
            detail.uslOverSoc = usl;
            detail.uslEmpt = usl;
        }
        return detail;
    }

}