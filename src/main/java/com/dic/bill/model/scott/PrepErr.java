package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Класс строки из temp_err, для показа сообщений об ошибках, по лиц.счетам и т.п.
@Entity
@Table(name = "PREP_ERR")
public class PrepErr {

	private int id;
	private String lsk, text;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	@Column(name = "LSK", nullable = true)
	public String getLsk() {
		return lsk;
	}
	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	@Column(name = "TEXT", nullable = true)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}