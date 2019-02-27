package com.dic.bill;

import java.util.*;

import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;

/**
 * Блокировщик объектов
 * @author Lev
 * @version 1.00
 *
 */
@Slf4j
public class Lock {
	// список блокировок по объекту (тип запроса RequestDirect.tpSel, Id объекта)
	private Map<Integer, List<Long>> mapLock;
	// список блокировок по долго продолжающимся процессам
	private List<String> procLock;
	// маркеры выполнения долго продолжающихся процессов
	//public List<String> procExec;

	// конструктор
	public Lock() {
		mapLock = new HashMap<>();
		procLock = new ArrayList<>();
		//procExec = new ArrayList<String>();
	}

	// блокировка процесса
	public synchronized Boolean setLockProc(Integer rqn, String procName) {
		if (this.procLock.contains(procName)) {
			// запрет блокировки
			log.trace("==LOCK== ERROR RQN={}, запрет блокировки процесса={}, "
					+ "идёт блокировка другим потоком!", rqn, procName);
			return false;
		} else {
			// выполнить блокировку
			this.procLock.add(procName);
			// установить маркер работающего процесса
			//this.procExec.add(procName);
			log.trace("==EXEC== RQN={}, блокировка выполнена: процесс={}", rqn, procName);
			return true;
		}

	}

	// проверить что вызвана остановка процесса
	public boolean isStopped(String procName) {
		return !this.procLock.contains(procName);
	}

	// разблокировать процесс
	public synchronized void unlockProc(Integer rqn, String procName) {
		log.trace("==UNLOCK== RQN={}, блокировка процесса={} снята", rqn, procName);
		// снять блокировку
		this.procLock.remove(procName);
	}

	// остановить все процессы
	public synchronized void stopAllProc(Integer rqn) {
		log.trace("==STOP== RQN={} Все процессы остановлены ПРИНУДИТЕЛЬНО!", rqn);
		this.procLock.clear();
	}

	/**
	 * Блокировка по ID объекта
	 * @param rqn - номер запроса
	 * @param tpSel - тип объекта блокировки (2-по вводу, 1-по помещению), ориентироваться на RequestConfigDirect.tpSel
	 * @param id - ID объекта
	 * @return
	 */
	private synchronized Boolean setLockId(Integer rqn, Integer tpSel, Long id) {
		List<Long> rowTpSel = this.mapLock.get(tpSel);
		if (rowTpSel!= null) {
			if (rowTpSel.contains(id)) {
				// запрет блокировки
				log.trace("==LOCK== ERROR RQN={}, запрет блокировки по tpSel={}, id={}, " +
						"идёт блокировка другим потоком!", rqn, tpSel, id);
				return false;
			} else {
				log.trace("==LOCK== RQN={}, блокировка выполнена: tpSel={}, id={}", rqn, tpSel, id);
				rowTpSel.add(id);
				return true;
			}
		} else {
			this.mapLock.put(tpSel, new ArrayList<Long>(){{add(id);}});
			log.trace("==LOCK== RQN={}, блокировка выполнена: tpSel={}, id={}", rqn, tpSel, id);
			return true;
		}
	}

	/**
	 * Блокировка по ID объекта с тайм-аутом
	 * @param rqn - номер запроса
	 * @param tpSel - тип объекта блокировки (2-по вводу, 1-по помещению), ориентироваться на RequestConfigDirect.tpSel
	 * @param id - ID объекта
	 * @param timeout - время в секундах на блокировку
	 * @return
	 */
	public boolean aquireLockId(Integer rqn, Integer tpSel, Long id, Integer timeout) {
		int waitTick = 0;
		while (!this.setLockId(rqn, tpSel, id)) {
			waitTick++;
			if (waitTick > timeout) { // ожидать блокировку
				log.error(
						"********ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!ВНИМАНИЕ!");
				log.error(
						"********НЕВОЗМОЖНО РАЗБЛОКИРОВАТЬ id={} В ТЕЧЕНИИ 60 сек!{}", id);
				return false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(Utl.getStackTraceString(e));
				return false;
			}
		}
		return true;
	}

	// разблокировать объект
	public synchronized void unlockId(Integer rqn, Integer tpSel, Long id) {
		List<Long> rowTpSel = this.mapLock.get(tpSel);
		rowTpSel.remove(id);
		log.trace("==UNLOCK== RQN={}, блокировка снята: tpSel={}, id={}", rqn, tpSel, id);
	}

}
