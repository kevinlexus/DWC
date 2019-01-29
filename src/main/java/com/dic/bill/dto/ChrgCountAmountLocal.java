package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

/**
 * DTO для хранения объемов по Квартире(локальное использование)
 */
@Getter
@Setter
@Slf4j
public class ChrgCountAmountLocal extends ChrgCountAmount {


    // с полной детализацией по услуге usl.cd="х.в. для гвс", используется только услугой fk_calc_tp = 47
    private List<UslPriceVolKart> lstUslPriceVolKartLinked = new ArrayList<>(10);

    /**
     * сгруппировать объемы для распределения по вводам
     *
     * @param u - объект объема
     */
    public void groupUslVol(UslPriceVolKart u) {
        // детализированный объем
        BigDecimal volDet = u.vol.add(u.volOverSoc);
        u.vol = u.vol.setScale(5, BigDecimal.ROUND_HALF_UP);
        u.volOverSoc = u.volOverSoc.setScale(5, BigDecimal.ROUND_HALF_UP);
        // округленный объем
        BigDecimal vol = u.vol.add(u.volOverSoc);

        // сгруппировать объемы по базовым параметрам, по лиц.счетам для распределения по вводам
        UslVolKartGrp prevUslVolKartGrp = getLstUslVolKartGrp().stream().filter(t -> t.kart.equals(u.kart)
                && t.usl.equals(u.usl)
        ).findFirst().orElse(null);
        if (prevUslVolKartGrp == null) {
            // добавить новый элемент
            UslVolKartGrp uslVolKartGrp = new UslVolKartGrp();
            uslVolKartGrp.kart = u.kart;
            // жилое помещение (берётся по Основному лиц.счету)
            uslVolKartGrp.isResidental = u.isResidental;
            uslVolKartGrp.usl = u.usl;
            uslVolKartGrp.vol = vol;
            uslVolKartGrp.volDet = volDet;

            uslVolKartGrp.area = u.area.add(u.areaOverSoc);
            uslVolKartGrp.kpr = u.kpr;
            if (u.isMeter) {
                // если хоть один раз был в периоде счетчик - поставить отметку
                uslVolKartGrp.isExistMeterCurrPeriod = true;
            }
            if (!u.isEmpty) {
                // если хоть один раз были в периоде зарегистрированы проживающие - поставить отметку
                uslVolKartGrp.isExistPersCurrPeriod = true;
            }
            getLstUslVolKartGrp().add(uslVolKartGrp);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKartGrp.vol = prevUslVolKartGrp.vol.add(vol);
            prevUslVolKartGrp.volDet = prevUslVolKartGrp.volDet.add(volDet);
            prevUslVolKartGrp.area = prevUslVolKartGrp.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKartGrp.kpr = prevUslVolKartGrp.kpr.add(u.kpr);
        }

        // если услуга usl.cd="х.в. для гвс", то сохранить для услуг типа Тепл.энергия для нагрева ХВС (Кис.)
        if (u.usl.getCd() != null && u.usl.getCd().equals("х.в. для гвс")) {
            lstUslPriceVolKartLinked.add(u);
        }
        // сгруппировать объемы по лиц.счетам для распределения по вводам
        UslVolKart prevUslVolKart = getLstUslVolKart().stream().filter(t -> t.kart.equals(u.kart)
                && t.usl.equals(u.usl) && t.isMeter == u.isMeter
                && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
        ).findFirst().orElse(null);
        if (prevUslVolKart == null) {
            // добавить новый элемент
            UslVolKart uslVolKart = new UslVolKart();
            uslVolKart.kart = u.kart;
            uslVolKart.usl = u.usl;
            uslVolKart.isResidental = u.isResidental;
            uslVolKart.isMeter = u.isMeter;
            uslVolKart.isEmpty = u.isEmpty;
            uslVolKart.vol = vol;
            uslVolKart.area = u.area.add(u.areaOverSoc);
            uslVolKart.kpr = u.kpr;
            getLstUslVolKart().add(uslVolKart);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKart.vol = prevUslVolKart.vol.add(vol);
            prevUslVolKart.area = prevUslVolKart.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKart.kpr = prevUslVolKart.kpr.add(u.kpr);
        }

        // сгруппировать по вводу
        UslVolVvod prevUslVolVvod = getLstUslVolVvod().stream().filter(t ->
                t.usl.equals(u.usl) && t.isMeter == u.isMeter
                        && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
        ).findFirst().orElse(null);
        if (prevUslVolVvod == null) {
            // добавить новый элемент
            UslVolVvod uslVolVvod = new UslVolVvod();
            uslVolVvod.isResidental = u.isResidental;
            uslVolVvod.isMeter = u.isMeter;
            uslVolVvod.isEmpty = u.isEmpty;
            uslVolVvod.usl = u.usl;
            uslVolVvod.vol = vol;
            uslVolVvod.area = u.area;
            getLstUslVolVvod().add(uslVolVvod);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolVvod.vol = prevUslVolVvod.vol.add(vol);
            prevUslVolVvod.area = prevUslVolVvod.area.add(u.area).add(u.areaOverSoc);
            prevUslVolVvod.kpr = prevUslVolVvod.kpr.add(u.kpr);
        }
    }

    /**
     * Округлить объемы по всем услугам
     */
    public void roundVol() {
        List<Usl> lstUsl = getLstUslVolKartGrp().stream().map(t -> t.usl).distinct().collect(Collectors.toList());
        for (Usl usl : lstUsl) {
            List<UslVolKartGrp> lst = getLstUslVolKartGrp().stream()
                    .filter(t -> t.usl.equals(usl))
                    .collect(Collectors.toList());
            for (UslVolKartGrp d : lst) {
                BigDecimal diff = d.volDet.setScale(5, BigDecimal.ROUND_HALF_UP)
                        .subtract(d.vol);
                d.vol = d.vol.add(diff);
                if (diff.compareTo(BigDecimal.ZERO) != 0) {
                    getLstUslVolKart().stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    lstUslPriceVolKartLinked.stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    getLstUslVolVvod().stream()
                            .filter(t -> t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                }
            }
        }
    }

}
