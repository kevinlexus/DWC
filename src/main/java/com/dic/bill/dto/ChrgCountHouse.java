package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/** DTO для хранения параметров для расчета начисления по дому
 *
 */
@Getter @Setter
public class ChrgCountHouse {

  // параметры расчета: Фактическая услуга, цена, тип объема и т.п.
  private List<UslPriceVolHouse> lstUslPriceVol = new ArrayList<>(10);

  // список лиц.счетов по услугам
  private Map<Usl, Set<Kart>> mapUslLsk = new HashMap<>();

  /**
   * добавить новый период UslPriceVol
   * @param kart - лиц.счет
   * @param u - новый период со значениями
   * u.vol - объем в доле дня от месяца!
   */
  public void groupUslPriceVol(Kart kart, UslPriceVol u) {
    // кол-во лиц.счетов
    Set<Kart> setLsk = mapUslLsk.get(u.usl);
    if (setLsk == null) {
      mapUslLsk.put(u.usl, new HashSet<Kart>(Arrays.asList(kart)));
    } else {
      setLsk.add(kart);
    }

    // искать по основному ключу
    UslPriceVolHouse prev = lstUslPriceVol.stream().filter(t ->
            t.usl.equals(u.usl) &&
            t.isCounter == u.isCounter &&
            t.isEmpty == u.isEmpty
    ).findFirst().orElse(null);
    if (prev == null) {
      // добавить новый элемент
      UslPriceVolHouse uslPriceVolHouse = new UslPriceVolHouse();

      uslPriceVolHouse.usl=u.usl;

      // кол-во лиц.счетов
      uslPriceVolHouse.cntLsk=1;
      // площадь по услуге
      uslPriceVolHouse.area=u.area;
      uslPriceVolHouse.areaOverSoc=u.areaOverSoc;
      uslPriceVolHouse.areaEmpty=u.areaEmpty;
      // объем
      uslPriceVolHouse.vol=u.vol;
      uslPriceVolHouse.volOverSoc=u.volOverSoc;
      uslPriceVolHouse.volEmpty=u.volEmpty;
      // кол-во проживающих
      uslPriceVolHouse.kpr=u.kpr;
      uslPriceVolHouse.kprWr=u.kprWr;
      uslPriceVolHouse.kprOt=u.kprOt;

      lstUslPriceVol.add(uslPriceVolHouse);
    } else {
        // такой же по ключевым параметрам, добавить данные в найденный период
        prev.cntLsk++;
        // площадь по услуге
        prev.area=prev.area.add(u.area);
        prev.areaOverSoc=prev.areaOverSoc.add(u.areaOverSoc);
        prev.areaEmpty=prev.areaEmpty.add(u.areaEmpty);
        // объем
        prev.vol=prev.vol.add(u.vol);
        prev.volOverSoc=prev.volOverSoc.add(u.volOverSoc);
        prev.volEmpty=prev.volEmpty.add(u.volEmpty);
        // кол-во проживающих
        prev.kpr=prev.kpr.add(u.kpr);
        prev.kprWr=prev.kprWr.add(u.kprWr);
        prev.kprOt=prev.kprOt.add(u.kprOt);
    }
  }

}
