package com.dic.bill.dto;

import com.dic.bill.model.scott.Usl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** DTO для хранения кол-ва проживающих
 *
 */
public class CountPers {

  // кол-во постоянно проживающих
  public int kpr = 0;
  // кол-во временно зарегистрированных
  public int kprWr = 0;
  // кол-во временно отсутствующих
  public int kprOt = 0;
  // кол-во для расчета объема для нормативного начисления
  public int kprNorm = 0;
  // максимальное кол-во проживающих
  public int kprMax = 0;
  // является ли пустым лиц.счет?
  public boolean isEmpty = true;

  // одиноко проживающий старше 70 лет (для капремонта)
  public boolean isSingleOwnerOlder70 = false;



}
