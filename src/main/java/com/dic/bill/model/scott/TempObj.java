package com.dic.bill.model.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//Класс строки из temp_obj, для хранения id любых объектов, для любых нужд

@Entity
@Table(name = "TEMP_OBJ")
public class TempObj {

	private int id;
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}