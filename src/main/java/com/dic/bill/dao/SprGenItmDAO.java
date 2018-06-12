package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.model.scott.SprGenItm;


public interface SprGenItmDAO extends JpaRepository<SprGenItm, Integer> {


	@Query("select t from SprGenItm t order by t.npp2")
	List<SprGenItm> getAllOrdered();

	@Query("select t from SprGenItm t where t.cd=:cd ")
	SprGenItm getByCd(@Param("cd") String cd);

}
