package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.dto.SumUslOrgTpRec;
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
	@Query(value = "select '003' as uslId, 677 as orgId, "/*note t.usl.id,t.org.id ВРЕМЕННО!!! TODO ###*/
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
	@Query(value = "select '003' as uslId, 677 as orgId, "/*note t.usl.id, t.org.id ВРЕМЕННО!!! TODO ###*/
			+ "t.summa as summa, t.dopl as mg, t.dt as dt, "
			+ "4 as tp from KwtpDay t "
			+ "where t.kart.lsk=:lsk and t.tp=0 "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getKwtpDayPenByLsk(@Param("lsk") String lsk);


	/**
	 * Получить записи оплаты текущего периода
	 * @param lsk - лицевой счет
	 */
	@Query(value = "select 3 as tp, t.usl.id as uslId, t.org.id as orgId, sum(t.summa) as summa "
			+ "from KwtpDay t "
			+ "where t.kart.lsk=:lsk "
			+ "and nvl(t.summa,0) <> 0 "
			+ "and t.tp=:tp "
			+ "group by t.usl.id, t.org.id")
	List<SumUslOrgTpRec> getKwtpDayByLskGrouped(@Param("lsk") String lsk, @Param("tp") Integer tp);

}
