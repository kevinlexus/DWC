package com.dic.bill.dto;

import com.dic.bill.model.scott.Ko;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO для хранения параметров для расчета начисления по дому
 */
@Getter
@Setter
public class ChrgCountHouse {

    // список квартир и лиц.счетов внутри ChrgCount.lstUslPriceVol с объемами
    private List<ChrgCount> lstChrgCount = new ArrayList();

    // добавить информацию по квартире
    public void addUslPriceVol(ChrgCount chrgCount) {
        lstChrgCount.add(chrgCount);
    }
}
