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
    @Query(value = "select t.usl.id as uslId, t.org.id as orgId, sum(t.summa) as summa "
            + "from Change t "
            + "where t.kart.lsk=:lsk "
            + "and nvl(t.summa,0) <> 0 "
            + "group by t.usl.id, t.org.id")
    List<SumUslOrgRec> getChangeByLskGrouped(@Param("lsk") String lsk);

}
