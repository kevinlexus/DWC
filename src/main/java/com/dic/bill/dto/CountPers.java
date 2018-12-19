package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

/** DTO для хранения кол-ва проживающих
 *
 */
@Getter @Setter
public class CountPers {

  // услуга
  Usl usl;
  // кол-во постоянно проживающих
  int count;
  // кол-во временно отсутствующих
  int countTempAbsent;
  // кол-во временно зарегистрированных
  int countTempReg;

}
