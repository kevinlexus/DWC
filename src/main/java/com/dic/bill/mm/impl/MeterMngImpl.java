package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.MeterData;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.scott.Meter;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MeterMngImpl implements MeterMng {

	@Autowired
	private MeterDAO meterDao;


	/**
	 * Получить первый попавшийся актуальный счетчик по параметрам
	 * @param koObjId - Ko.id объекта, где установлен счетчик
	 * @param usl - код услуги
	 * @param dt - дата на которую получить
	 * @return
	 */
	@Override
	public Meter getActualMeterByKoUsl(Integer koObjId, String usl, Date dt) {
		for (Meter meter : meterDao.findActualByKoUsl(koObjId, usl, dt)) {
			return meter;
		}
		// не найдено
		return null;
	}

	/**
	 * Проверить наличие в списке показания по счетчику
	 * @param lst - список показаний
	 * @param guid - GUID прибора учета
	 * @param ts - временная метка
	 * @return
	 */
	@Override
	public boolean getIsMeterDataExist(List<MeterData> lst, String guid, XMLGregorianCalendar ts) {
		Date dt = Utl.truncDateToSeconds(Utl.getDateFromXmlGregCal(ts));
		//log.info("********* guid={}, ts={}, ts.num={}", guid, dt, dt.getTime());
		/*for (MeterData meterData : lst) {
			log.info("********* MeterData: guid={} equal={}, ts={} equal={} ts.num={}",
					meterData.getGuid(), meterData.getGuid().equals(guid), meterData.getTs(),
					meterData.getTs().compareTo(dt), meterData.getTs().getTime());
		}*/
		MeterData meterData = lst.stream().filter(t -> t.getGuid().equals(guid) && t.getTs().compareTo(dt)==0)
				.findFirst().orElse(null);
		return meterData != null? true :false;
	}

}