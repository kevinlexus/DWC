package com.dic.bill.dto;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/** DTO для хранения параметров для расчета начисления
 *
 */
@Getter @Setter
public class ChrgCount {

  // параметры расчета: Фактическая услуга, цена, тип объема и т.п.
  private List<UslPriceVol> lstUslPriceVol = new ArrayList<>(10);

  // все действующие счетчики квартиры и их объемы
  private List<SumMeterVol> lstMeterVol;

  /**
   * добавить новый период UslPriceVol
   * @param u - новый период со значениями
   * u.dtFrom - текущая дата!
   * u.dtTo - текущая дата!
   * u.vol - объем в доле дня от месяца!
   */
  public void groupUslPriceVol(UslPriceVol u) {
    Date prevDt = Utl.addDays(u.dtFrom, -1);
    // искать по предыдущей дате, основному ключу
    UslPriceVol prev = lstUslPriceVol.stream().filter(t -> t.dtTo.equals(prevDt)
            && t.usl.equals(u.usl)).findFirst().orElse(null);
    if (prev == null) {
      // добавить новый элемент
      lstUslPriceVol.add(u);
    } else {
      if (prev.org.equals(u.org) && prev.isCounter == u.isCounter
              && prev.isEmpty == u.isEmpty && prev.socStdt.equals(u.socStdt)
              && prev.price.equals(u.price) && prev.priceOverSoc.equals(u.priceOverSoc)
              && prev.priceEmpty.equals(u.priceEmpty) ) {
        // такой же по ключевым параметрам, добавить данные в найденный период
        prev.area=prev.area.add(u.area);
        prev.areaOverSoc=prev.areaOverSoc.add(u.areaOverSoc);
        prev.areaEmpty=prev.areaEmpty.add(u.areaEmpty);

        prev.vol=prev.vol.add(u.vol);
        prev.volOverSoc=prev.volOverSoc.add(u.volOverSoc);
        prev.volEmpty=prev.volEmpty.add(u.volEmpty);

        prev.kpr=prev.kpr.add(u.kpr);
        prev.kprWr=prev.kprWr.add(u.kprWr);
        prev.kprOt=prev.kprOt.add(u.kprOt);

        // продлить дату окончания
        prev.dtTo=u.dtTo;
      } else {
        // найденный период отличается по ключевым полям
        // добавить новый период
        lstUslPriceVol.add(u);
      }
    }

  }

}
