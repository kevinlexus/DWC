package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.model.scott.DebPenUslTemp;


public interface DebPenUslTempDAO extends JpaRepository<DebPenUslTemp, Integer> {

	/**
	 * Удалить записи по лицевому счету, периоду
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 */
	@Modifying
	@Query(value = "delete from DebPenUslTemp t where t.sessionDirect.id=:sessionDirectId ")
	void delByIter(@Param("sessionDirectId") Integer sessionDirectId);

}
