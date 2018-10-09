package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//Класс строки из temp_err, для показа сообщений об ошибках, по лиц.счетам и т.п.
@Entity
@Table(name = "PREP_ERR", schema = "SCOTT")
@Getter @Setter
public class PrepErr {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private int id;

	@Column(name = "LSK", nullable = true)
	private String lsk;

	@Column(name = "TEXT", nullable = true)
	private String text;

}