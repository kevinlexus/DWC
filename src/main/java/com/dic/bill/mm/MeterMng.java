package com.dic.bill.mm;

import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;

import java.util.Date;

public interface MeterMng {

	Meter getActualMeterByKoUsl(Integer koId, String usl, Date dt);
}
