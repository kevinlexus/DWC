package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.dto.SumUslOrgRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.CorrectPay;


public interface CorrectPayDAO extends JpaRepository<CorrectPay, Integer> {

	/**
	 * Получить записи корректировок оплаты по услугам - организациям
	 * @param lsk - лицевой счет
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, "
			+ "t.summa as summa, t.dopl as mg, t.dt as dt, "
			+ "6 as tp from CorrectPay t "
			+ "where t.kart.lsk=:lsk and t.mg=:period "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getCorrectPayByLsk(@Param("lsk") String lsk, @Param("period") String period);

	/**
	 * Получить записи корректировок оплаты по услугам - организациям
	 * @param mg - необходимый период
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, "
			+ "sum(t.summa) as summa from CorrectPay t "
			+ "where t.kart.lsk=:lsk and t.mg=:mg "
			+ "and nvl(t.summa,0) <> 0 "
			+ "group by t.usl.id, t.org.id")
	List<SumUslOrgRec> getCorrectPayByLskGrouped(@Param("lsk") String lsk, @Param("mg") String mg);

	/**
	 * Получить записи корректировок оплаты по услугам - организациям за выбраный период DOPL
	 * @param mg - необходимый бухгалтерский период
	 * @param dopl - период оплаты
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, "
			+ "sum(t.summa) as summa from CorrectPay t "
			+ "where t.kart.lsk=:lsk and t.mg=:mg and t.dopl=:dopl "
			+ "and nvl(t.summa,0) <> 0 "
			+ "group by t.usl.id, t.org.id")
	List<SumUslOrgRec> getCorrectPayByLskGrouped(@Param("lsk") String lsk,
												 @Param("mg") String mg, @Param("dopl") String dopl);

	/**
	 * Удалить корректировки по cdtp документа
	 * @param cdTp - cd документа
	 */
	@Modifying
	@Query(value = "delete from CorrectPay t where exists (select d from ChangeDoc d where d.cdTp=:cdTp " +
			"and t.changeDoc=d)")
	void deleteCorrectPayByChangeDoc(@Param("cdTp") String cdTp);

}
