package com.dic.bill.dto;

import com.dic.bill.model.exs.Ulist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SumChrgRecAlter implements SumChrgRec {
    // Id услуги из ГИС
    Integer ulistId;
    // начисление
    Double chrg;
    // перерасчет
    Double chng;
    // объем
    Double vol;
    // цена
    Double price;
    // норматив (обычно - коэфф. для услуги повыш.коэфф)
    Double norm;
    // общая площадь (работает совместно с вспомогательным коэффициентом)
    Double sqr;
    // услуга из справочника ГИС
    Ulist ulist;
    // услуга из справочника ГИС
}