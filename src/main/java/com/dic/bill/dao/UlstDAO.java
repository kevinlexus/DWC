package com.dic.bill.dao;

import com.dic.bill.model.scott.Lst;
import com.dic.bill.model.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * DAO сущности com.dic.bill.model.scott.Lst2. Так как существовал LstDAO другой сущности com.dic.bill.model.bs.Lst2,
 * пришлось этот назвать UlstDAO
 */
public interface UlstDAO extends JpaRepository<Lst, Integer> {

	@Query("select t from com.dic.bill.model.scott.Lst t where t.cd = :cd")
	Lst getByCd(@Param("cd") String cd);

}
