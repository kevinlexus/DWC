package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** DTO для хранения параметров для расчета начисления
 *
 */
@Getter @Setter
public class ChrgCount {

  // объемы по услуге
  Map<Usl, List<ChrgVol>> mapChrgVol = new HashMap<>(10);
  // максимальное кол-во проживающих по услуге
  Map<Usl, Integer> mapKprMax = new HashMap<>(10);

}
