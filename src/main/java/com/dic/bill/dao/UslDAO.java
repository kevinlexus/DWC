package com.dic.bill.dao;

import com.dic.bill.model.scott.Usl;
import com.dic.bill.model.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UslDAO extends JpaRepository<Usl, String> {

	@Query("select t from Usl t where t.cd = :cd")
	Usl getByCd(@Param("cd") String cd);

}
