package com.dic.bill.dao;

import com.dic.bill.model.scott.Apenya;
import com.dic.bill.model.scott.PenCorr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * DAO сущности PenCorr
 * @author Lev
 * @version 1.00
 *
 */
@Repository()
public interface PenCorrDAO extends JpaRepository<PenCorr, Integer> {


	/**
	 * Получить все элементы по lsk
	 *
	 * @param lsk - лиц.счет
	 */
	@Query("select t from PenCorr t "
			+ "where t.kart.id = :lsk and nvl(t.penya,0) <> 0")
	List<PenCorr> getByLsk(@Param("lsk") String lsk);

}
