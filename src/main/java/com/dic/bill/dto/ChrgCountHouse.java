package com.dic.bill.dto;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** DTO для хранения параметров для расчета начисления по дому
 *
 */
@Getter @Setter
public class ChrgCountHouse {

  // параметры расчета: Фактическая услуга, цена, тип объема и т.п.
  private List<UslPriceVol> lstUslPriceVol = new ArrayList<>(10);

  /**
   * добавить новый период UslPriceVol
   * @param u - новый период со значениями
   * u.vol - объем в доле дня от месяца!
   */
  public void groupUslPriceVol(UslPriceVol u) {
    // искать по основному ключу
    UslPriceVol prev = lstUslPriceVol.stream().filter(t ->
            t.usl.equals(u.usl) &&
            t.isCounter == u.isCounter &&
            t.isEmpty == u.isEmpty
    ).findFirst().orElse(null);
    if (prev == null) {
      // добавить новый элемент
      lstUslPriceVol.add(u);
    } else {
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
    }
  }

}
