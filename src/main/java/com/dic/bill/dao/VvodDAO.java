package com.dic.bill.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dic.bill.model.scott.Vvod;
import org.springframework.data.repository.query.Param;


public interface VvodDAO extends JpaRepository<Vvod, Integer> {

	/**
	 * Найти все вводы, где нет ОДПУ, по открытым домам
	 */
	@Query(value = "select t from Vvod t join t.house h where t.distTp in (4,5) and nvl(h.psch,0) = 0")
	List<Vvod> getWoODPU();

	/**
	 * Найти все вводы, где есть ОДПУ, по открытым домам
	 */
	@Query(value = "select t from Vvod t join t.house h where t.distTp not in (4,5,2) and nvl(h.psch,0) = 0")
	List<Vvod> getWithODPU();

	/**
	 * Найти все вводы, по УК
	 */
	@Query(value = "select distinct d.id from TEST.C_VVOD d " +
			"join TEST.KART k on d.HOUSE_ID=k.HOUSE_ID where k.reu=:reuId and k.PSCH not in (8,9)",
			nativeQuery = true)
	List<BigDecimal> findVvodByUk(@Param("reuId") String reuId);

	/**
	 * Найти все вводы, по дому
	 */
	@Query(value = "select t from Vvod t join t.house h where h.id=:houseId")
	List<Vvod> findVvodByHouse(@Param("houseId") int houseId);

}
