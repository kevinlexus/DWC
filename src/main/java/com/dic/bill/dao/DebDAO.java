package com.dic.bill.dao;
/*

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumDebPenRec;
import com.dic.bill.model.scott.Deb;


public interface DebDAO extends JpaRepository<Deb, Integer> {

	*/
/**
	 * Получить записи долгов, по услугам
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @return
	 *//*

	@Query(value = "select t.id as id, t.usl.id as uslId, t.org.id as orgId, "
			+ "nvl(t.debOut,0) as debOut, nvl(t.debRolled,0) as debRolled,"
			+ "t.mg as mg from Deb t where :period between t.mgFrom and t.mgTo and t.kart.lsk=:lsk "
			)
	List<SumDebPenRec> getDebitByLsk(@Param("lsk") String lsk, @Param("period") Integer period);

	*/
/**
	 * Удалить записи по лицевому счету, созданные текущим периодом
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 *//*

	@Modifying
	@Query(value = "delete from Deb t where t.mgFrom = :period and "
			+ " t.kart.lsk=:lsk "
			)
	void delByLskPeriod(@Param("lsk") String lsk, @Param("period") Integer period);

	*/
/**
	 * Обновить записи по лицевому счету, расширенные до текущего периода
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @param periodBack - бухгалтерский период -1 месяц
	 *//*

	@Modifying
	@Query(value = "update Deb t set t.mgTo=:periodBack where t.mgTo = :period and "
			+ " t.kart.lsk=:lsk "
			)
	void updByLskPeriod(@Param("lsk") String lsk, @Param("period") Integer period,
			@Param("periodBack") Integer periodBack);

}
*/
