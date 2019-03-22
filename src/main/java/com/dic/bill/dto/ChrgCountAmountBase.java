package com.dic.bill.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Абстрактный, базовый DTO для хранения объемов
 */
@Getter
@Setter
@Slf4j
abstract class ChrgCountAmountBase {

    // сгруппированные до лиц.счетов, объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolKart> lstUslVolKart = new ArrayList<>(10);

    // сгруппированные по базовым параметрам, до лиц.счетов, объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolKartGrp> lstUslVolKartGrp = new ArrayList<>(10);

    // сгруппированные по вводу объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolVvod> lstUslVolVvod = new ArrayList<>(10);

    // результирующий объем по операции tp=4
    private BigDecimal resultVol = BigDecimal.ZERO;

    // очистить коллекции
    public void clear() {
        lstUslVolKart.clear();
        lstUslVolKartGrp.clear();
        lstUslVolVvod.clear();
    }
}
