package com.dic.bill.mm;

import com.dic.bill.dto.MeterData;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public interface MeterMng {

	Meter getActualMeterByKoUsl(Ko ko, String usl, Date dt);

    boolean getIsMeterDataExist(List<MeterData> lst, String guid, XMLGregorianCalendar ts);

    boolean getIsMeterActual(Meter meter, Date dt);

    boolean getIsMeterOpenForReceiveData(Meter meter);

    boolean getIsMeterOpenForSendData(Meter meter);

    boolean getCanSaveDataMeter(Eolink meterEol, Date dt);

    List<Meter> findMeter(int i, int i1) throws InterruptedException;
}
