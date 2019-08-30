package com.dic.bill.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dic.bill.model.scott.Ko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.scott.Apenya;

/**
 * DAO сущности Apenya
 * @author Lev
 * @version 1.00
 *
 */
@Repository()
public interface ApenyaDAO extends JpaRepository<Apenya, Integer> {


	/**
	 * Получить все элементы по lsk
	 *
	 * @param lsk - лиц.счет
	 */
	@Query("select t from Apenya t "
			+ "where t.kart.id = :lsk and t.mg = :mg and nvl(t.summa,0) <> 0 "
			+ "order by t.mg1")
	List<Apenya> getByLsk(@Param("lsk") String lsk, @Param("mg") String mg);

	/**
	 * Получить совокупную пеню по основным услугам
	 * @param lsk - лиц.счет
	 * @param dt - дата, на которую пеня
	 * @return
	 */
	@Query(value = "select sum(t.penya) from Apenya t "
			+ "where t.kart.id = ?1 and t.mg = TO_CHAR(?2,'YYYYMM')")
	BigDecimal getPenAmnt(String lsk, Date dt);

}
