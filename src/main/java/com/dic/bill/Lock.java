package com.dic.bill;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Блокировщик объектов
 * @author Lev
 * @version 1.00
 *
 */
@Slf4j
public class Lock {
	// список блокировок по лицевым счетам
	public List<Integer> lstLsk;
	// список блокировок по долго продолжающимся процессам
	public List<String> procLock;
	// маркеры выполнения долго продолжающихся процессов
	public List<String> procExec;

	// конструктор
	public Lock() {
		lstLsk = new ArrayList<>();
		procLock = new ArrayList<String>();
		procExec = new ArrayList<String>();
	}

	// блокировка процесса
	public synchronized Boolean setLockProc(Integer rqn, String procName) {
		if (this.procLock.contains(procName)) {
			// запрет блокировки
			log.info("==LOCK== ERROR RQN={}, запрет блокировки процесса={}, "
					+ "идёт блокировка другим потоком!", rqn, procName);
			return false;
		} else {
			// выполнить блокировку
			this.procLock.add(procName);
			// установить маркер работающего процесса
			this.procExec.add(procName);
			log.info("==EXEC== RQN={}, блокировка выполнена: процесс={}", rqn, procName);
			return true;
		}

	}

	// проверить что вызвана остановка процесса
	public boolean isStopped(String procName) {
		if (this.procExec.contains(procName)) {
			// не вызвана
			return false;
		} else {
			// вызвана
			return true;
		}
	}

	// разблокировать процесс
	public synchronized void unlockProc(Integer rqn, String procName) {
		log.info("==UNLOCK== RQN={}, блокировка процесса={} снята", rqn, procName);
		// снять блокировку
		this.procLock.remove(procName);
		// снять маркер работающего процесса
		this.procExec.remove(procName);
	}

	// остановить процесс
	public synchronized void stopProc(Integer rqn, String procName) {
		log.info("==STOP== RQN={} Процесс {} остановлен ПРИНУДИТЕЛЬНО!", rqn, procName);
		this.procExec.remove(procName);
	}

	// блокировка по лиц.счету
	public synchronized Boolean setLockLsk(Integer rqn, Integer klskId) {
		if (this.lstLsk.contains(klskId)) {
			// запрет блокировки
			log.info("==LOCK== ERROR RQN={}, запрет блокировки по klskId={}, идёт блокировка другим потоком!", klskId);
			return false;
		} else {
			// выполнить блокировку
			this.lstLsk.add(klskId);
			log.info("==LOCK== RQN={}, блокировка выполнена: klskId={}", rqn, klskId);
			return true;
		}

	}

	// разблокировать лиц.счет
	public synchronized void unlockLsk(Integer rqn, Integer klskId) {
		this.lstLsk.remove(klskId);
		log.info("==UNLOCK== RQN={}, блокировка снята: klskId={}", rqn, klskId);
	}

}
