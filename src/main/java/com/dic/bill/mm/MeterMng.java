package com.dic.bill.mm;

import com.dic.bill.dto.CalcStore;
import com.dic.bill.dto.MeterData;
import com.dic.bill.dto.SumMeterVol;
import com.dic.bill.dto.UslMeterDateVol;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;
import com.dic.bill.model.scott.ObjPar;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeterMng {

    boolean getIsMeterDataExist(List<MeterData> lst, String guid, XMLGregorianCalendar ts);

    boolean getIsMeterActual(Meter meter, Date dt);

    boolean getIsMeterOpenForReceiveData(Meter meter);

    boolean getIsMeterOpenForSendData(Meter meter);

    boolean getCanSaveDataMeter(Eolink meterEol, Date dt);

    List<ObjPar> getValuesByMeter(Meter meter, int status, String period);

    List<Meter> findMeter(int i, int i1) throws InterruptedException;

    Optional<Meter> getActualMeterByKoUsl(Ko ko, String usl, Date dt);

    Optional<Meter> getActualMeterByKo(Ko koPremis, String usl, Date dt);

    List<UslMeterDateVol> getPartDayMeterVol(List<SumMeterVol> lstMeterVol, CalcStore calcStore);

    boolean isExistAnyMeter(List<SumMeterVol> lstMeterVol, String uslId, Date dt);

    int sendMeterVal(BufferedWriter writer, String lsk, String strUsl, String value, String period, Integer userId) throws IOException;
}
