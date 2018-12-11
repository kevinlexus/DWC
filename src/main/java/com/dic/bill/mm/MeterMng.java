package com.dic.bill.mm;

import com.dic.bill.dto.MeterData;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;

import java.util.Date;
import java.util.List;

public interface MeterMng {

	Meter getActualMeterByKoUsl(Integer koId, String usl, Date dt);

    boolean getIsMeterDataExist(List<MeterData> lst, String guid, Date ts);
}
