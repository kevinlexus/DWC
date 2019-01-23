package com.dic.bill.mm;

import com.dic.bill.dto.CalcStore;
import com.dic.bill.dto.MeterData;
import com.dic.bill.dto.SumMeterVol;
import com.dic.bill.dto.UslMeterDateVol;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public interface MeterMng {

    boolean getIsMeterDataExist(List<MeterData> lst, String guid, XMLGregorianCalendar ts);

    boolean getIsMeterActual(Meter meter, Date dt);

    boolean getIsMeterOpenForReceiveData(Meter meter);

    boolean getIsMeterOpenForSendData(Meter meter);

    boolean getCanSaveDataMeter(Eolink meterEol, Date dt);

    List<Meter> findMeter(int i, int i1) throws InterruptedException;

    Meter getActualMeterByKoUsl(Ko ko, String usl, Date dt);

    List<UslMeterDateVol> getPartDayMeterVol(List<SumMeterVol> lstMeterVol, CalcStore calcStore);

    boolean isExistAnyMeter(List<SumMeterVol> lstMeterVol, String uslId, Date dt);
}
