package com.dic.bill;

import javax.annotation.Generated;

//import com.dic.bill.model.scott.SessionDirect;

import com.dic.bill.model.scott.House;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Vvod;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Конфигуратор запроса
 * @author Lev
 *
 */
@Getter@Setter
public class RequestConfig {

	// Id запроса
	int rqn;
	// тип выполнения 0-начисление, 1-задолженность и пеня, 2 - распределение объемов по вводу
	int tp=0;
	// уровень отладки
	int debugLvl=0;
	// дата на которую сформировать
	Date genDt=null;
	// выполнять многопоточно
	boolean isMultiThreads=false;

	// объекты формирования:
	// квартира
	Ko ko = null;
	// дом
	House house = null;
	// ввод
	Vvod vvod = null;

	public static final class RequestConfigBuilder {
		// Id запроса
        int rqn;
		// тип операции 0-начисление, 1-задолженность и пеня
        int tp;

		private RequestConfigBuilder() {
		}

		public static RequestConfigBuilder aRequestConfig() {
			return new RequestConfigBuilder();
		}

		public RequestConfigBuilder withRqn(int rqn) {
			this.rqn = rqn;
			return this;
		}

		public RequestConfigBuilder withTp(int tp) {
			this.tp = tp;
			return this;
		}

		public RequestConfig build() {
			RequestConfig requestConfig = new RequestConfig();
			requestConfig.setRqn(rqn);
			requestConfig.setTp(tp);
			return requestConfig;
		}
	}
}
