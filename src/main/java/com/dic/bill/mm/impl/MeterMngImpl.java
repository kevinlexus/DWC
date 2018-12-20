package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.MeterData;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
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