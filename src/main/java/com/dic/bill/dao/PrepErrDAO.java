package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dic.bill.model.scott.PrepErr;


public interface PrepErrDAO extends JpaRepository<PrepErr, Integer> {

	@Query("select t from PrepErr t order by t.id")
	List<PrepErr> getAllOrdered();

}
