package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.KwtpDay;


public interface KwtpDayDAO extends JpaRepository<KwtpDay, Integer> {

	/**
	 * Получить записи текущих оплат долга по услугам - организациям
	 * @param lsk - лицевой счет
	 * @return
	 */
	@Query(value = "select '003' as uslId, t.org.id as orgId, "/*t.usl.idВРЕМЕННО!!!###*/
			+ "t.summa as summa, t.dopl as mg, t.dt as dt, "
			+ "3 as tp from KwtpDay t "
			+ "where t.kart.lsk=:lsk and t.tp=1 "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getKwtpDaySumByLsk(@Param("lsk") String lsk);

	/**
	 * Получить записи текущих оплат пени по услугам - организациям
	 * @param lsk - лицевой счет
	 * @return
	 */
	@Query(value = "select '003' as uslId, t.org.id as orgId, "/*t.usl.idВРЕМЕННО!!!###*/
			+ "t.summa as summa, t.dopl as mg, t.dt as dt, "
			+ "4 as tp from KwtpDay t "
			+ "where t.kart.lsk=:lsk and t.tp=0 "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getKwtpDayPenByLsk(@Param("lsk") String lsk);

}