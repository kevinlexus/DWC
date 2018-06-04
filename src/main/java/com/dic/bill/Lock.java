package com.dic.bill;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Блокировщик объектов
 * @author Lev
 * @version 1.00
 *
 */
@Slf4j
public class Lock {
	public List<String> lstLsk;
	// конструктор
	public Lock() {
		lstLsk = new ArrayList<String>();
	}

	// блокировка по лиц.счету
	public synchronized Boolean setLockLsk(Integer rqn, String lsk) {
		if (this.lstLsk.contains(lsk)) {
			// запрет блокировки
			log.info("==ERROR LOCK== RQN={}, запрет блокировки по lsk={}, идёт блокировка другим потоком!", lsk);
			return false;
		} else {
			// выполнить блокировку
			this.lstLsk.add(lsk);
			log.info("==LOCK== RQN={}, блокировка выполнена: lsk={}", rqn, lsk);
			return true;
		}

	}

	// разблокировать лиц.счет
	public synchronized void unlockLsk(Integer rqn, String lsk) {
		log.info("==UNLOCK== RQN={}, блокировка снята: lsk={}", rqn, lsk);
		this.lstLsk.remove(lsk);
	}

}
