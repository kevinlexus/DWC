package com.dic.bill.dao;

import com.dic.bill.dto.SumRecMg;
import com.dic.bill.model.scott.KwtpMg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface KwtpMgDAO extends JpaRepository<KwtpMg, Integer> {


    /**
     * Получить все элементы по lsk
     *
     * @param lsk - лиц.счет
     */
    @Query("select t from KwtpMg t "
            + "where t.kart.id = :lsk")
    List<KwtpMg> getByLsk(@Param("lsk") String lsk);

    /**
     * Получить сгруппированные записи оплат текущего периода
     * @param lsk - лицевой счет
     */
    @Query(value = "select t.mg, coalesce(sum(t.summa),0) as summa "
            + "from KwtpMg t "
            + "where t.kart.lsk=:lsk "
            + "group by t.mg")
    List<SumRecMg> getKwtpMgByPeriodAndLsk(@Param("lsk") String lsk);

}
