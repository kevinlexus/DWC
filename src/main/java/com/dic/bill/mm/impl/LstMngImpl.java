package com.dic.bill.mm.impl;

import java.util.List;

import com.dic.bill.dao.AddrTpDAO;
import com.dic.bill.dao.KoDAO;
import com.dic.bill.model.scott.Ko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dic.bill.dao.LstDAO;
import com.dic.bill.mm.LstMng;
import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst2;

@Service
public class LstMngImpl implements LstMng {

	@Autowired
	private LstDAO lstDao;
	@Autowired
	private KoDAO koDao;
	@Autowired
	private AddrTpDAO addrTpDao;

	@Cacheable(cacheNames="LstMngImpl.getByCD", unless = "#result == null") //здесь кэш работает очень эффективно, не убирать!
	public Lst2 getByCD(String cd) {
		return lstDao.getByCD(cd);
	}

	/*
	 * Получить список Lst2 по типу
	 * cdTp - тип списка
	 */
	public List<Lst2> getByTp(String cdTp) {
		return lstDao.getByTp(cdTp);
	}

	/**
	 * Получить список типа Ko по типу адреса и фильтру по наименованию
	 */
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt) {
		return koDao.getKoByAddrTpFlt(addrTp, flt);
	}

    /* Получить типы адресов
	 * @param tp - 0 - весь список, 1 - ограниченный основными типами, 2 - только Дом
     */
	public List<AddrTp> getAddrTpByTp(Integer tp) {
		return addrTpDao.getByTp(tp);
	}

    /* Получить тип адреса по CD
	 * @param cd - CD типа адреса
     */
	public AddrTp getAddrTpByCD(String cd) {
		return addrTpDao.getByCd(cd);
	}

}