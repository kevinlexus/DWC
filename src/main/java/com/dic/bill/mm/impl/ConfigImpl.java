package com.dic.bill.mm.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dic.bill.Lock;
import com.dic.bill.dao.ParamDAO;
import com.dic.bill.mm.Config;
import com.dic.bill.model.scott.Param;
import com.ric.cmn.Utl;

import lombok.extern.slf4j.Slf4j;

/**
 * Конфигуратор приложения
 * @author lev
 * @version 1.01
 */
@Service
@Slf4j
public class ConfigImpl implements Config {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ParamDAO paramDao;

	// даты текущего периода (не зависимо от перерасчета)
	Date curDt1;
	Date curDt2;

	// номер текущего запроса
	private int reqNum = 0;

	//private List<Integer> workLst; // обрабатываемые лицевые счета

	// Текущий период (для партицирования и проч.)
	String period;
	// Период +1 месяц
	String periodNext;
	// Период -1 месяц
	String periodBack;

	// Запретить начислять по лиц.счетам, если формируется глобальное начисление
	Boolean isRestrictChrgLsk = false;


	// блокировщик выполнения процессов
	private Lock lock;

	@PostConstruct
	private void setUp() {
		log.info("");
		log.info("-----------------------------------------------------------------");
		log.info("Версия модуля - {}", "1.0.2");

		log.info("Начало расчетного периода = {}", getCurDt1());
		log.info("Конец расчетного периода = {}", getCurDt2());
		log.info("-----------------------------------------------------------------");
		log.info("");

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		// блокировщик процессов
		setLock(new Lock());
	}

	// Получить Calendar текущего периода
	////@Cacheable(cacheNames="Config.getCalendarCurrentPeriod") Пока отключил 24.11.2017
	private List<Calendar> getCalendarCurrentPeriod() {
			List<Calendar> calendarLst = new ArrayList<Calendar>();

			Param param = em.find(Param.class, 1);
			if (param == null) {
				log.error("ВНИМАНИЕ! Установите SCOTT.PARAMS.ID=1");
			}

			Calendar calendar1, calendar2;
			calendar1 = new GregorianCalendar();
			calendar1.clear(Calendar.ZONE_OFFSET);

			calendar2 = new GregorianCalendar();
			calendar2.clear(Calendar.ZONE_OFFSET);


			// получить даты начала и окончания периода
			Date dt = Utl.getDateFromPeriod(param.getPeriod().concat("01"));
			Date dt1 = Utl.getFirstDate(dt);
			Date dt2 = Utl.getLastDate(dt1);

			calendar1.setTime(dt1);
			calendarLst.add(calendar1);

			calendar2.setTime(dt2);
			calendarLst.add(calendar2);

			return calendarLst;
	}

	@Override
	public String getPeriod() {
		return Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime());
	}

	@Override
	public String getPeriodNext() {
		return Utl.addMonths(Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime()), 1);
	}

	@Override
	public String getPeriodBack() {
		return Utl.addMonths(Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime()), -1);
	}

	@Override
	public Date getCurDt1() {
		return getCalendarCurrentPeriod().get(0).getTime();
	}

	@Override
	public Date getCurDt2() {
		return getCalendarCurrentPeriod().get(1).getTime();
	}

	// получить следующий номер запроса
	@Override
	public synchronized int incNextReqNum() {
		return this.reqNum++;
	}

	public Boolean getIsRestrictChrgLsk() {
		return isRestrictChrgLsk;
	}

	public void setIsRestrictChrgLsk(Boolean isRestrictChrgLsk) {
		this.isRestrictChrgLsk = isRestrictChrgLsk;
	}

	@Override
	public Lock getLock() {
		return lock;
	}

	private void setLock(Lock lock) {
		this.lock = lock;
	}

	/**
	 * Выполнить блокировку лицевого счета
	 */
	@Override
	public boolean aquireLock (int rqn, String lsk) {
		// блокировка лиц.счета
		int waitTick = 0;
		while (!getLock().setLockLsk(rqn, lsk)) {
			waitTick++;
			if (waitTick > 60) {
				log.error(
						"********ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!");
				log.error(
						"********НЕВОЗМОЖНО РАЗБЛОКИРОВАТЬ к lsk={} В ТЕЧЕНИИ 60 сек!{}", lsk);
				return false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}



}
