package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.Charge;


/**
 * DAO entity ChargeDAO
 * @author Lev
 * @version 1.00
 */
public interface ChargeDAO extends JpaRepository<Charge, Integer> {

	/**
	 * Получить записи текущих начислений по услугам - организациям
	 * @param lsk - лицевой счет
	 * @return
	 */
	@Query(value = "select t.usl.id as uslId, n.org.id as orgId, t.summa as summa, "
			+ "2 as tp from Charge t join t.kart k join k.nabor n on n.usl.id=t.usl.id "
			+ "where t.kart.lsk=:lsk and t.type=1 "
			+ "and nvl(t.summa,0) <> 0")
	List<SumRec> getCharge(@Param("lsk") String lsk);


}
