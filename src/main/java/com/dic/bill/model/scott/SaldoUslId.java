package com.dic.bill.model.scott;

// суррогатный первичный ключ
public class SaldoUslId  implements java.io.Serializable {
	private String lsk; // лиц.счет
	private String usl; // код услуги
	private Integer org; // код организации
	private String mg; // период задолжности
	private String period; // период бухгалтерский
    
}