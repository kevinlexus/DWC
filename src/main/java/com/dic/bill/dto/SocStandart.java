package com.dic.bill.dto;

import java.math.BigDecimal;

/** DTO для хранения составляющих соцнормы
 *
 */
public class SocStandart {

  // соцнорма на 1 проживающего
  public BigDecimal norm = BigDecimal.ZERO;
  // совокупный объем соцнормы
  public BigDecimal vol = BigDecimal.ZERO;
  // доля свыше соц.нормы
  public BigDecimal procNorm = BigDecimal.ZERO;

}
