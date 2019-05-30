package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.VchangeDet;

/**
 * DAO entity VchangeDet
 * @author Lev
 * @version 1.00
 */
public interface VchangeDetDAO extends JpaRepository<VchangeDet, Integer> {

	/**
	 * Получить записи текущих перерасчетов по услугам - организациям из View
	 * @param lsk - лицевой счет
	 * @return
	 */
	@Query(value = "select t.usl.id as uslId, t.org.id as orgId, t.summa as summa, "
			+ "t.mgchange as mg, 5 as tp, t.dt as dt from VchangeDet t "
			+ "where t.kart.lsk=:lsk "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getVchangeDetByLsk(@Param("lsk") String lsk);


}
