package com.dic.bill.dao;

import com.dic.bill.model.scott.Akwtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AkwtpDAO extends JpaRepository<Akwtp, Integer> {

    /**
     * Получить платеж по № извещения (из ГИС ЖКХ)
     * @param numDoc - № извещения (из ГИС ЖКХ)
     * @return
     */
    @Query(value = "select t from Akwtp t "
            + "where t.numDoc=:numDoc and t.mg between :mgFrom and :mgTo")
    Akwtp getByNumDoc(@Param("numDoc") String numDoc,
                      @Param("mgFrom") String mgFrom, @Param("mgTo") String mgTo);

}
