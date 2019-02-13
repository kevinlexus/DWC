package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dic.bill.model.scott.Vvod;
import org.springframework.data.repository.query.Param;


public interface VvodDAO extends JpaRepository<Vvod, Integer> {

	/**
	 * Найти все вводы, где нет ОДПУ, по открытым домам
	 * @return
	 */
	@Query(value = "select t from Vvod t join t.house h where t.distTp in (4,5) and nvl(h.psch,0) = 0")
	List<Vvod> getWoODPU();

	/**
	 * Найти все вводы, где есть ОДПУ, по открытым домам
	 * @return
	 */
	@Query(value = "select t from Vvod t join t.house h where t.distTp not in (4,5,2) and nvl(h.psch,0) = 0")
	List<Vvod> getWithODPU();

	/**
	 * Найти все вводы, по дому
	 * @return
	 */
	@Query(value = "select t from Vvod t join t.house h where h.id=:houseId")
	List<Vvod> findVvodByHouse(@Param("houseId") int houseId);

}
