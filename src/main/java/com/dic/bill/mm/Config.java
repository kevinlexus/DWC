package com.dic.bill.mm;

import java.util.Date;

import com.dic.bill.Lock;

public interface Config {

	String getPeriod();

	String getPeriodNext();

	String getPeriodBack();

	Date getCurDt1();

	Date getCurDt2();

	Lock getLock();

	boolean aquireLock(int rqn, String lsk);

	int incNextReqNum();

}