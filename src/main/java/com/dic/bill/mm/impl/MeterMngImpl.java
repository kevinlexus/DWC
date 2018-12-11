package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.MeterData;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.scott.Meter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
	 * Прроверить наличие в списке показания по счетчику
	 * @param lst - список показаний
	 * @param guid - GUID прибора учета
	 * @param ts - временная метка
	 * @return
	 */
	@Override
	public boolean getIsMeterDataExist(List<MeterData> lst, String guid, Date ts) {
		MeterData meterData = lst.stream().filter(t -> t.getGuid().equals(guid) && t.getTs().equals(ts))
				.findFirst().orElse(null);
		return meterData != null? true :false;
	}

}