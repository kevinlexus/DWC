package com.dic.bill.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dic.bill.model.scott.House;


public interface HouseDAO extends JpaRepository<House, Integer> {

	/**
	 * Найти все открытые дома
	 * @return
	 */
	@Query(value = "select t from com.dic.bill.model.scott.House t where nvl(t.psch,0) = 0 ")
	List<House> getNotClosed();

	Optional<House> findByGuid(String guid);

	List<House> findByGuidIsNotNull();
}
