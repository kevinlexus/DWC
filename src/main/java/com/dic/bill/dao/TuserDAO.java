package com.dic.bill.dao;

import com.dic.bill.model.scott.Tuser;
import com.dic.bill.model.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TuserDAO extends JpaRepository<Tuser, Integer> {

	@Query("select t from Tuser t where t.cd = :cd")
	Tuser getByCd(@Param("cd") String cd);

}
