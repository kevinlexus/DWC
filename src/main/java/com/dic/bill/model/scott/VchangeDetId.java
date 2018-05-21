package com.dic.bill.model.scott;

import java.util.Date;

// суррогатный первичный ключ
@SuppressWarnings("serial")
public class VchangeDetId  implements java.io.Serializable {
	private Kart kart;
	private String mgchange;
	private Date dt;
	private Usl usl;
	private Org org;
}