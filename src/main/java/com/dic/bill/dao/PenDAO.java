package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumDebPenRec;
import com.dic.bill.model.scott.Deb;


public interface PenDAO extends JpaRepository<Deb, Integer> {

	/**
	 * Получить записи задолженности по пени, по услугам
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @return
	 */
	@Query(value = "select t.id as id, t.usl.id as uslId, t.org.id as orgId, "
			+ "nvl(t.penIn,0) as penIn, "
			+ "nvl(t.penChrg,0) as penChrg, "
			+ "nvl(t.penCorr,0) as penCorr, "
			+ "nvl(t.penPay,0) as penPay, "
			+ "nvl(t.penOut,0) as penOut, "
			+ "nvl(t.days,0) as days, "
			+ "t.mg as mg, 8 as tp from Pen t where :period between t.mgFrom and t.mgTo and t.kart.lsk=:lsk "
			//+ "and nvl(t.penOut,0) <> 0 "
			)
	List<SumDebPenRec> getPenByLsk(@Param("lsk") String lsk, @Param("period") Integer period);

	/**
	 * Удалить записи по лицевому счету, созданные текущим периодом
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 */
	@Modifying
	@Query(value = "delete from Pen t where t.mgFrom =:period and "
			+ " t.kart.lsk=:lsk) "
			)
	void delByLskPeriod(@Param("lsk") String lsk, @Param("period") Integer period);

	/**
	 * Обновить записи по лицевому счету, расширенные до текущего периода
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @param periodBack - бухгалтерский период -1 месяц
	 */
	@Modifying
	@Query(value = "update Pen t set t.mgTo=:periodBack where t.mgTo = :period and "
			+ " t.kart.lsk=:lsk) "
			)
	void updByLskPeriod(@Param("lsk") String lsk, @Param("period") Integer period,
			@Param("periodBack") Integer periodBack);

}
