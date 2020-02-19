package com.dic.bill.mm.impl;

import com.dic.bill.dao.HouseDAO;
import com.dic.bill.model.scott.House;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseMngImpl implements com.dic.bill.mm.HouseMng {

	private final HouseDAO houseDAO;

	public HouseMngImpl(HouseDAO houseDAO) {
		this.houseDAO = houseDAO;
	}

	@Override
	@Cacheable(cacheNames = "HouseMng.findByGuid", key = "{#guid}")
	public Optional<House> findByGuid(String guid) {
		return houseDAO.findByGuid(guid);
	}
}