package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.CalcStore;
import com.dic.bill.dto.ChrgCount;
import com.dic.bill.dto.MeterData;
import com.dic.bill.dto.SumMeterVol;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeterMngImpl implements MeterMng {

	@Autowired
	private MeterDAO meterDao;


	/**
	 * Получить первый попавшийся актуальный счетчик по параметрам
	 * @param ko - Ko объекта, где установлен счетчик
	 * @param usl - код услуги
	 * @param dt - дата на которую получить
	 * @return
	 */

	@Override
	public Meter getActualMeterByKoUsl(Ko ko, String usl, Date dt) {
		for (Meter meter : meterDao.findActualByKoUsl(ko.getId(), usl, dt)) {
			return meter;
		}
		// не найдено
		return null;
	}


	/**
	 * Получить объем в доле одного дня по счетчикам квартиры
	 * @param chrgCount -  хранилище параметров для расчета начисления
 	 * @param calcStore - хранилище необходимых данных для расчета пени, начисления
	 * @return - объем в доле 1 дня к периоду наличия рабочего счетчика
	 */
	public Map<String, BigDecimal> getPartDayMeterVol(ChrgCount chrgCount, CalcStore calcStore) {
		Calendar c = Calendar.getInstance();
		// distinct список кодов услуг найденных счетчиков
		List<String> lstMeterUslId = chrgCount.getLstMeterVol().stream()
				.map(t -> t.getUslId()).distinct().collect(Collectors.toList());
		Map<String, BigDecimal> mapDayMeterVol = new HashMap<String, BigDecimal>();

		// перебрать услуги
		for (String uslId : lstMeterUslId) {
			int workDays=0;
			// перебрать дни текущего месяца
			for (c.setTime(calcStore.getCurDt1()); !c.getTime().after(calcStore.getCurDt2());
				 									c.add(Calendar.DATE, 1)) {
				Date curDt = c.getTime();
				// найти любой действующий счетчик, прибавить день
				SumMeterVol meterVol = chrgCount.getLstMeterVol().stream().filter(t -> t.getUslId().equals(uslId) &&
						Utl.between(curDt, t.getDtFrom(), t.getDtTo())).findFirst().orElse(null);
				if (meterVol != null) {
					workDays++;
				}
			}
			// сумма объема по всем счетчикам данной услуги
			BigDecimal vol = chrgCount.getLstMeterVol().stream().filter(t -> t.getUslId().equals(uslId))
					.map(t->t.getVol())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			// доля объема на 1 рабочий день наличия счетчика
			BigDecimal partDayVol;
			if (workDays != 0) {
				partDayVol = vol.divide(BigDecimal.valueOf(workDays), 20, RoundingMode.HALF_UP);
			} else {
				// вообще не было активных счетчиков в периоде
				partDayVol = null;
			}
/*
			log.info("Расчет объема на 1 день: usl.id={}, дней работы={}, общий объем={}, доля на 1 день={}",
					uslId, workDays, vol, partDayVol);
*/
			mapDayMeterVol.put(uslId, partDayVol);
		}

		return mapDayMeterVol;
	}

	/**
	 * Узнать, работал ли хоть один счетчик в данном дне
	 * @param uslId - код услуги
	 * @param dt - дата на которую проверить
	 * @return
	 */
	@Override
	public boolean isExistAnyMeter(ChrgCount chrgCount, String uslId, Date dt) {
		SumMeterVol meterVol = chrgCount.getLstMeterVol().stream().filter(t -> t.getUslId().equals(uslId) &&
				Utl.between(dt, t.getDtFrom(), t.getDtTo())).findFirst().orElse(null);
		return meterVol!=null ? true : false;
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
		MeterData meterData = lst.stream().filter(t -> t.getGuid().equals(guid) && t.getTs().compareTo(dt)==0)
				.findFirst().orElse(null);
		return meterData != null? true :false;
	}


	/**
	 * Проверить, является ли счетчик в Директ актуальным
	 * @param meter - счетчик
	 * @param dt - проверочная дата
	 * @return
	 */
	@Override
	public boolean getIsMeterActual(Meter meter, Date dt) {
		return Utl.between(dt, meter.getDt1(), meter.getDt2());
	}

	/**
	 * Проверить, открыт ли счетчик в Директ принятия показаний от ГИС
	 * @param meter - счетчик
	 * @return
	 */
	@Override
	public boolean getIsMeterOpenForReceiveData(Meter meter) {
		if (meter.getGisConnTp()==null) {
			return false;
		} else if (meter.getGisConnTp().equals(1) || meter.getGisConnTp().equals(3)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Проверить, открыт ли счетчик в Директ для отправки показаний от ГИС
	 * @param meter - счетчик
	 * @return
	 */
	@Override
	public boolean getIsMeterOpenForSendData(Meter meter) {
		if (meter.getGisConnTp()==null) {
			return false;
		} else if (meter.getGisConnTp().equals(2) || meter.getGisConnTp().equals(3)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Проверить, возможно ли сохранить показания по счетчику в Директ
	 * @param meterEol - счетчик Eolink
	 * @param dt - проверочная дата
	 * @return
	 */
	@Override
	public boolean getCanSaveDataMeter(Eolink meterEol, Date dt) {
		Ko ko = meterEol.getKoObj();
		Meter meter = ko.getMeter();
		return getIsMeterActual(meter, dt) && getIsMeterOpenForReceiveData(meter);
	}

	/**
	 * ТЕСТОВЫЙ МЕТОД - ПРОВЕРЯЛ LockModeType.PESSIMISTIC_READ
	 * @param i
	 * @param i1
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public List<Meter> findMeter(int i, int i1) throws InterruptedException {
		List<Meter> lst = meterDao.findMeter(72802, 72805);
		lst.forEach(t-> {
			log.info("Meter.id={} n1={}", t.getId() , t.getN1());
		});
		Thread.sleep(15000);
		lst.forEach(t-> {
			log.info("Meter.id={} n1={}", t.getId() , t.getN1());
		});
		Thread.sleep(15000);
		lst.forEach(t-> {
			log.info("Meter.id={} n1={}", t.getId() , t.getN1());
		});
		Thread.sleep(15000);
		lst.forEach(t-> {
			log.info("Meter.id={} n1={}", t.getId() , t.getN1());
		});
		Thread.sleep(15000);
		lst.forEach(t-> {
			log.info("Meter.id={} n1={}", t.getId() , t.getN1());
		});
		return lst;
	}



}