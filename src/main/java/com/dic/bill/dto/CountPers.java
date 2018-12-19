package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** DTO для хранения кол-ва проживающих
 *
 */
@Getter @Setter
public class CountPers {

  // кол-во постоянно проживающих
  BigDecimal kpr = BigDecimal.ZERO;
  // кол-во временно зарегистрированных
  BigDecimal kprWr = BigDecimal.ZERO;
  // кол-во временно отсутствующих
  BigDecimal kprOt = BigDecimal.ZERO;
  // кол-во для расчета объема для нормативного начисления
  BigDecimal kprNorm = BigDecimal.ZERO;
  // максимальное кол-во проживающих
  BigDecimal kprMax = BigDecimal.ZERO;

}
