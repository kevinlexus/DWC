package com.dic.bill.dao;

import com.dic.bill.model.scott.PenCur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PenCurDAO extends JpaRepository<PenCur, Integer> {

    /**
     * Удалить текущую пеню
     * @param lsk - лиц.счет
     */
    @Modifying
    @Query(value = "delete from PenCur t where t.kart.lsk=:lsk")
    void deleteByLsk(@Param("lsk") String lsk);


}
