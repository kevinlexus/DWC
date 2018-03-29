package com.dic.bill.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.scott.Apenya;
import com.dic.bill.model.scott.ApenyaId;
import com.dic.bill.model.scott.Org;

/**
 * DAO сущности Apenya
 * @author Lev
 * @version 1.00
 *
 */
@Repository()
public interface ApenyaDAO extends JpaRepository<Apenya, ApenyaId> {

	 	
	/**
	 * Получить совокупную пеню по основным услугам
	 * @param lsk - лиц.счет
	 * @param dt - дата, на которую пеня
	 * @return
	 */
	@Query(value = "select sum(t.penya) from Apenya t "
			+ "where t.lsk = ?1 and t.mg = TO_CHAR(?2,'YYYYMM')")
	BigDecimal getPenAmnt(String lsk, Date dt);
	
}
