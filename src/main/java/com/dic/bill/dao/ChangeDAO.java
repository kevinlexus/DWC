package com.dic.bill.dao;

import com.dic.bill.dto.SumUslOrgRec;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Change;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChangeDAO extends JpaRepository<Change, Integer> {

    /**
     * Получить сгруппированные записи перерасчетов текущего периода
     * @param lsk - лицевой счет
     */
    @Query(value = "select t.usl as uslId, t.org as orgId, sum(t.summa) as summa "
            + "from SCOTT.V_CHANGES_FOR_SALDO t "
            + "where t.lsk=:lsk "
            + "and nvl(t.summa,0) <> 0 "
            + "group by t.usl, t.org",
            nativeQuery = true)
    List<SumUslOrgRec> getChangeByLskGrouped(@Param("lsk") String lsk);

}
