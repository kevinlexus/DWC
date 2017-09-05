package com.dic.bill.utils;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

//класс сессии
public class DSess {

	public Session sess;
	   
	//Конструктор
	public DSess(Boolean newSess) {
		
		if (newSess) {
			//вернуть новую сессию
			sess=HibernateUtil.getSessionFactory().openSession();
		} else {
			//получить текущую сессию, привязанную к текущему потоку, как сказано в http://stackoverflow.com/questions/8046662/hibernate-opensession-vs-getcurrentsession
			sess = HibernateUtil.getSessionFactory().getCurrentSession();        //и в http://myjourneyonjava.blogspot.ru/2013/12/difference-between-opensession-and.html
		}
	}
	

	//закрыть сессию
	public void closeSess() {
		if (sess.isOpen()) {
		  sess.close();
		}
	}	

	//начать транзакцию, получить текущую сессию
	public void beginTrans() {
		sess.beginTransaction();
	}

	//закоммитить транзакцию
	public void commitTrans() {
		sess.getTransaction().commit();
	}
	
	//закоммитить open транзакцию
	public void commitOpenTrans() {
		sess.flush();
		sess.getTransaction().commit();
	}

	//откатить транзакцию
	public void rollbackTrans() {
		sess.getTransaction().rollback();
	}	

	//вернуть наличие транзакции
	public boolean isActiveTrans() {
		if (sess.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
			return true;
		} else {
			return false;
		}
	}
	
}
