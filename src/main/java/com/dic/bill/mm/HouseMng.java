package com.dic.bill.mm;

import com.dic.bill.model.scott.House;

import java.util.Optional;

public interface HouseMng {

	Optional<House> findByGuid(String guid);
}
