package com.dic.bill.model.scott;

// суррогатный первичный ключ
public class SaldoUslId  implements java.io.Serializable {
	private Kart kart; // лиц.счет
	private String usl; // код услуги
	private Integer org; // код организации
	private String mg; // период задолженности
	private String period; // период бухгалтерский

}