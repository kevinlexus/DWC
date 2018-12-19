package com.dic.bill;

import javax.annotation.Generated;

//import com.dic.bill.model.scott.SessionDirect;

import lombok.Getter;
import lombok.Setter;

/**
 * Конфигуратор запроса
 * @author Lev
 *
 */
@Getter@Setter
public class RequestConfig {

	// Id запроса
	int rqn;
	// тип выполнения 0-начисление, 1-задолженность и пеня
	int tp;

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
