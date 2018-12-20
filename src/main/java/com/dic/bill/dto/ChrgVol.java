package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** DTO для хранения параметров для расчета начисления
 *
 */
public class ChrgVol {

  // услуга
  public Usl usl;
  // пустая квартира?
  public boolean isEmpty;
  // наличие счетчика
  public boolean isCounter;
  // объем по услуге
  public BigDecimal vol = BigDecimal.ZERO;
  // кол-во постоянно проживающих
  public BigDecimal kpr = BigDecimal.ZERO;
  // кол-во временно зарегистрированных
  public BigDecimal kprWr = BigDecimal.ZERO;
  // кол-во временно отсутствующих
  public BigDecimal kprOt = BigDecimal.ZERO;
  // кол-во для расчета объема для нормативного начисления
  public BigDecimal kprNorm = BigDecimal.ZERO;
  // доля месяца
  public BigDecimal partMonth = BigDecimal.ZERO;

  public ChrgVol(Usl usl, boolean isEmpty, boolean isCounter) {
    this.usl = usl;
    this.isEmpty = isEmpty;
    this.isCounter = isCounter;
  }
}
