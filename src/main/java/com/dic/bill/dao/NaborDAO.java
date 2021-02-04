package com.dic.bill.dao;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Nabor;
import com.dic.bill.model.scott.Usl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface NaborDAO extends JpaRepository<Nabor, Integer> {

    @Query("select t from Nabor t where t.kart.lsk = :lsk and t.usl.id = :uslId")
    Nabor getByLskUsl(@Param("lsk") String lsk, @Param("uslId") String uslId);

    Optional<Nabor> findByKartAndUsl(Kart kart, Usl usl);

}
