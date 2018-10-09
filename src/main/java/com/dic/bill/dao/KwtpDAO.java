package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Kwtp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface KwtpDAO extends JpaRepository<Kwtp, Integer> {

    /**
     * Получить платеж по № извещения (из ГИС ЖКХ)
     * @param numDoc - № извещения (из ГИС ЖКХ)
     * @return
     */
    @Query(value = "select t from Kwtp t "
            + "where t.numDoc=:numDoc")
    Kwtp getByNumDoc(@Param("numDoc") String numDoc);

}
