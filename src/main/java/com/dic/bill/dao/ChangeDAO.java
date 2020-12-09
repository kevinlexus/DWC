package com.dic.bill.dao;

import com.dic.bill.dto.SumRecMg;
import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.model.scott.Change;
import org.springframework.data.jpa.repository.JpaRepository;
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


    /**
     * Получить сгруппированные записи перерасчетов текущего периода
     * @param lsk - лицевой счет
     */
    @Query(value = "select t.mg, coalesce(sum(t.summa),0) as summa "
            + "from Change t "
            + "where t.kart.lsk=:lsk "
            + "group by t.mg")
    List<SumRecMg> getChangeByPeriodAndLsk(@Param("lsk") String lsk);

}
