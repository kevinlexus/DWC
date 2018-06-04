package com.dic.bill;

import javax.annotation.Generated;

import com.dic.bill.model.scott.SessionDirect;

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
	// сессия Директа
	SessionDirect sessionDirect;


	@Generated("SparkTools")
	private RequestConfig(Builder builder) {
		this.rqn = builder.rqn;
		this.sessionDirect = builder.sessionDirect;
	}
	/**
	 * Creates builder to build {@link RequestConfig}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link RequestConfig}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private int rqn;
		private SessionDirect sessionDirect;

		private Builder() {
		}

		public Builder withRqn(int rqn) {
			this.rqn = rqn;
			return this;
		}

		public Builder withSessionDirect(SessionDirect sessionDirect) {
			this.sessionDirect = sessionDirect;
			return this;
		}

		public RequestConfig build() {
			return new RequestConfig(this);
		}
	}


}
