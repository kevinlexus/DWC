package com.dic.bill.dao;

import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.dto.SumUslOrgTpRec;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Change;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChangeDAO extends JpaRepository<Change, Integer> {

    /**
     * Получить записи перерасчетов текущего периода
     * @param lsk - лицевой счет
     */
    @Query(value = "select 2 as tp, t.usl.id as uslId, t.org.id as orgId, sum(t.summa) as summa "
            + "from Change t "
            + "where t.kart.lsk=:lsk "
            + "and nvl(t.summa,0) <> 0 "
            + "group by t.usl.id, t.org.id")
    List<SumUslOrgTpRec> getChangeByLskGrouped(@Param("lsk") String lsk);

}
