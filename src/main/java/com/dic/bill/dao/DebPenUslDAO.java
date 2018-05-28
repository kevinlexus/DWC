package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.DebPenUsl;


public interface DebPenUslDAO extends JpaRepository<DebPenUsl, Integer> {

	/**
	 * Получить записи долгов по услугам
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @return
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, nvl(t.penOut,0) as penOut, "
			+ "nvl(t.debOut,0) as debOut, "
			+ "t.mg as mg, 1 as tp from DebPenUsl t where t.period=:period and t.kart.lsk=:lsk "
			+ "and (nvl(t.debOut,0) <> 0 or nvl(t.penOut,0) <> 0)")
	List<SumRec> getDebitByLsk(@Param("lsk") String lsk, @Param("period") Integer period);


	/**
	 * Удалить записи по лицевому счету, периоду
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 */
	@Modifying
	@Query(value = "delete from DebPenUsl t where t.period=:period and "
			+ " t.kart.lsk=:lsk) "
			)
	void delByLskPeriod(@Param("lsk") String lsk, @Param("period") Integer period);

}
