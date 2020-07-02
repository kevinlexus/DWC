package com.dic.bill;

/**
 * Интерфейс сжимаемых таблиц
 * @author lev
 *
 */
public interface Compress {


	Integer getMgFrom();
	void setMgFrom(Integer period);

	Integer getMgTo();
	void setMgTo(Integer period);

	// ключ по которому определяются строки для сжатия
	String getKey();

	int getHash();
	boolean isTheSame(Compress compr);

}
