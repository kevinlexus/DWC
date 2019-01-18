package com.dic.bill.dto;

import com.dic.bill.model.scott.Ko;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO для хранения параметров для расчета начисления по квартире
 */
@Slf4j
@Getter
@Setter
public class ChrgCount {

    // квартира
    private Ko ko;

    // сгруппированные параметры расчета: Фактическая услуга, цена, тип объема и т.п.
    private List<UslPriceVolKart> lstUslPriceVolKart = new ArrayList<>(10);

    // все действующие счетчики объекта и их объемы
    private List<SumMeterVol> lstMeterVol;

    /**
     * добавить новый период UslPriceVolKart
     *
     * @param u - новый период с объемами
     *          u.dtFrom - текущая дата!
     *          u.dtTo - текущая дата!
     *          u.vol - объем в доле дня от месяца!
     */
    public void groupUslPriceVolKart(UslPriceVolKart u) {
        Date prevDt = Utl.addDays(u.dtFrom, -1);
        // искать по лиц.счету, предыдущей дате, основному ключу
        UslPriceVolKart prevUslPriceVolKart = lstUslPriceVolKart.stream().filter(t -> t.kart.equals(u.kart)
                && t.dtTo.equals(prevDt)
                && t.usl.equals(u.usl) &&
                t.org.equals(u.org) && t.isMeter == u.isMeter && t.isResidental == u.isResidental
                && t.isEmpty == u.isEmpty && t.socStdt.equals(u.socStdt)
                && t.price.equals(u.price) && t.priceOverSoc.equals(u.priceOverSoc)
                && t.priceEmpty.equals(u.priceEmpty)).findFirst().orElse(null);
        if (prevUslPriceVolKart == null) {
            // добавить новый элемент
            lstUslPriceVolKart.add(u);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденный период
            prevUslPriceVolKart.area = prevUslPriceVolKart.area.add(u.area);
            prevUslPriceVolKart.areaOverSoc = prevUslPriceVolKart.areaOverSoc.add(u.areaOverSoc);

            prevUslPriceVolKart.vol = prevUslPriceVolKart.vol.add(u.vol);
            prevUslPriceVolKart.volOverSoc = prevUslPriceVolKart.volOverSoc.add(u.volOverSoc);

            prevUslPriceVolKart.kpr = prevUslPriceVolKart.kpr.add(u.kpr);
            prevUslPriceVolKart.kprWr = prevUslPriceVolKart.kprWr.add(u.kprWr);
            prevUslPriceVolKart.kprOt = prevUslPriceVolKart.kprOt.add(u.kprOt);

            // продлить дату окончания
            prevUslPriceVolKart.dtTo = u.dtTo;
        }
    }

}
