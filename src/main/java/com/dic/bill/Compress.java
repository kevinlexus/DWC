package com.dic.bill;

/**
 * Интерфейс сжимаемых таблиц
 * @author lev
 *
 */
public interface Compress {


	public Integer getMgFrom();
	public void setMgFrom(Integer period);

	public Integer getMgTo();
	public void setMgTo(Integer period);
	
	public Integer getKey();
	public Integer getHash();
	
	
}
