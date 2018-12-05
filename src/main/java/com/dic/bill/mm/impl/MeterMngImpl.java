package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
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
	 * @param koId - Ko.id объекта, где установлен счетчик
	 * @param usl - код услуги
	 * @param dt - дата на которую получить
	 * @return
	 */
	@Override
	public Meter getActualMeterByKoUsl(Integer koId, String usl, Date dt) {
		for (Meter meter : meterDao.findActualByKoUsl(koId, usl, dt)) {
			return meter;
		}
		// не найдено
		return null;
	}
}