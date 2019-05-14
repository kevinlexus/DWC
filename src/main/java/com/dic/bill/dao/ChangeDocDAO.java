package com.dic.bill.dao;

import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.model.scott.Change;
import com.dic.bill.model.scott.ChangeDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChangeDocDAO extends JpaRepository<ChangeDoc, Integer> {

    /**
     * Удалить документ корректировок
     * @param cdTp - cd документа
     */
    @Modifying
    @Query(value = "delete from ChangeDoc t where t.cdTp=:cdTp")
    void deleteChangeDocByCdTp(@Param("cdTp") String cdTp);

}
