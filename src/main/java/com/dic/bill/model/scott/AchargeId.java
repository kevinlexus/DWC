package com.dic.bill.model.scott;


// суррогатный первичный ключ
public class AchargeId  implements java.io.Serializable {
	private String lsk; // лиц.счет
	private Integer type; // Тип записи, 0 - начисление, 1 - оплата
	private String usl; // код услуги
	private String mg; // период задолженности
}