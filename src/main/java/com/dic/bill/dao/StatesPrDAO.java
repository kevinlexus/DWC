package com.dic.bill.dao;

import com.dic.bill.model.scott.StatePr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface StatesPrDAO extends JpaRepository<StatePr, Integer> {

	/**
	 * Получить статусы проживающих по периоду
	 * примечание: sysdate-100000 sysdate+100000  - самая первая или последняя дата в системе, если пусто в поле
	 * @param dtFrom - дата начала
	 * @param dtTo - дата окончания
	 * @return
	 */
	@Query(value = "select t from StatePr t where " +
			"t.kartPr.kart.id=?1 and (?2 between nvl(t.dtFrom, sysdate-100000) and nvl(t.dtTo, sysdate+100000)" +
			"or ?3 between nvl(t.dtFrom, sysdate-100000) and nvl(t.dtTo, sysdate+100000))")
	List<StatePr> findByDate(String lsk, Date dtFrom, Date dtTo);

/*
	*/
/**
	 * Получить статусы проживающего по дате
	 * примечание: sysdate-100000 sysdate+100000  - самая первая или последняя дата в системе, если пусто в поле
	 * @param kartPrId - ID проживающего
	 * @param dt - дата запроса
	 * @return
	 *//*

	@Query(value = "select t from StatePr t where t.kartPr.id=?1 and " +
			"?2 between nvl(t.dtFrom, sysdate-100000) and nvl(t.dtTo, sysdate+100000)")
	List<StatePr> getByDate(Integer kartPrId, Date dt);

	*/
/**
	 * Получить статус проживающего по типу статуса и по дате
	 * примечание: sysdate-100000 sysdate+100000  - самая первая или последняя дата в системе, если пусто в поле
	 * @param kartPrId - ID проживающего
	 * @param statusTp - тип статуса ("PROP" - постоянной регистрации, "PROP_REG" - временной)
	 * @param dt - дата запроса
	 * @return
	 *//*

	@Query(value = "select t from StatePr t where t.kartPr.id=?1 and " +
			"?3 between nvl(t.dtFrom, sysdate-100000) and nvl(t.dtTo, sysdate+100000) " +
			"and t.statusPr.tp.cd=?2")
	StatePr getByTpDate(Integer kartPrId, String statusTp, Date dt);
*/

}
