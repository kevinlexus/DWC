package com.dic.bill.dao;

import com.dic.bill.model.scott.ObjPar;
import com.dic.bill.model.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObjParDAO extends JpaRepository<ObjPar, Integer> {

	@Query("select t from ObjPar t where t.ko.id = :klskId and t.lst.cd = :cd")
	ObjPar getByKlskCd(@Param("klskId") Long klskId, @Param("cd") String cd);

}
