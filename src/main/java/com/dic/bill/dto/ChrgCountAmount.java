package com.dic.bill.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO для хранения параметров для расчета начисления по дому
 */
@Getter
@Setter
public class ChrgCountAmount {



    // сгруппированные по вводу объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolVvod> lstUslVolVvod = new ArrayList<>(10);

    // сгруппированные по квартире объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolKart> lstUslVolKart = new ArrayList<>(10);

    /**
     * сгруппировать объемы для распределения по вводам
     * @param u
     */
    public void groupUslVol(UslPriceVolKart u) {

    // сгруппировать объемы по лиц.счетам для распределения по вводам
    UslVolKart prevUslVolKart = lstUslVolKart.stream().filter(t -> t.kart.equals(u.kart)
            && t.usl.equals(u.usl) && t.isCounter == u.isCounter
            && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
            ).findFirst().orElse(null);
        if (prevUslVolKart == null) {
            // добавить новый элемент
            UslVolKart uslVolKart = new UslVolKart();
            uslVolKart.kart = u.kart;
            uslVolKart.usl = u.usl;
            uslVolKart.isResidental = u.isResidental;
            uslVolKart.isCounter = u.isCounter;
            uslVolKart.isEmpty = u.isEmpty;
            uslVolKart.vol = u.vol.add(u.volOverSoc);
            uslVolKart.area = u.area.add(u.areaOverSoc);
            uslVolKart.kpr = u.kpr;
            lstUslVolKart.add(uslVolKart);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденный период
            prevUslVolKart.vol = prevUslVolKart.vol.add(u.vol).add(u.volOverSoc);
            prevUslVolKart.area = prevUslVolKart.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKart.kpr = prevUslVolKart.kpr.add(u.kpr);
        }

        // сгруппировать по вводу
        UslVolVvod prevUslVolVvod = lstUslVolVvod.stream().filter(t->
                t.usl.equals(u.usl) && t.isCounter == u.isCounter
                && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
                ).findFirst().orElse(null);
        if (prevUslVolVvod == null) {
            // добавить новый элемент
            UslVolVvod uslVolVvod = new UslVolVvod();
            uslVolVvod.isResidental = u.isResidental;
            uslVolVvod.isCounter = u.isCounter;
            uslVolVvod.isEmpty = u.isEmpty;
            uslVolVvod.usl = u.usl;
            uslVolVvod.vol = u.vol;
            uslVolVvod.area = u.area;
            lstUslVolVvod.add(uslVolVvod);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденный период
            prevUslVolVvod.vol = prevUslVolVvod.vol.add(u.vol).add(u.volOverSoc);
            prevUslVolVvod.area = prevUslVolVvod.area.add(u.area).add(u.areaOverSoc);
            prevUslVolVvod.kpr = prevUslVolVvod.kpr.add(u.kpr);
        }
    }

}
