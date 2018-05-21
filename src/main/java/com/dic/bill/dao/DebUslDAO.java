package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.DebUsl;


public interface DebUslDAO extends JpaRepository<DebUsl, Integer> {

	/**
	 * Получить записи долгов по услугам
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @return
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, t.summa as summa, t.penya as penya, "
			+ "t.mg as mg, 1 as tp from DebPenUsl t where t.period=:period and t.kart.lsk=:lsk "
			+ "and nvl(t.summa,0) <> 0 ")
	List<SumRec> getDebit(@Param("lsk") String lsk, @Param("period") Integer period);

}
