package com.dic.bill.dao;

import com.dic.bill.model.scott.Spk;
import com.dic.bill.model.scott.Usl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpkDAO extends JpaRepository<Spk, Integer> {

    @Query("select t from Spk t where t.cd = :cd")
    Spk getByCd(@Param("cd") String cd);

}
