package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.CorrectPay;


public interface CorrectPayDAO extends JpaRepository<CorrectPay, Integer> {

	/**
	 * Получить записи корректировок оплаты по услугам - организациям
	 * @param lsk - лицевой счет
	 * @return
	 */
	@Query(value = "select '003' as uslId, t.org.id as orgId, "/*t.usl.idВРЕМЕННО!!!###*/
			+ "t.summa as summa, t.dopl as mg, t.dt as dt, "
			+ "6 as tp from CorrectPay t "
			+ "where t.kart.lsk=:lsk "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getCorrectPayByLsk(@Param("lsk") String lsk);

}