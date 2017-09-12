package com.dic.bill;

/**
 * Интерфейс сжимаемых таблиц
 * @author lev
 *
 */
public interface Compress {

	public boolean same(Object obj);

	public Integer getMg1();
	public void setMg1(Integer period);

	public Integer getMg2();
	public void setMg2(Integer period);
	
	// не во всех таблицах есть USL, делать там поле Transient
	public String getUsl();
	
}
