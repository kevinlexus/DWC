package com.dic.bill.dao;

import com.dic.bill.model.scott.AkartPr;
import com.dic.bill.model.scott.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AkartPrDAO extends JpaRepository<AkartPr, Integer> {


    /**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     *
     * @param lsk    - лиц. счет
     * @param period - период
     */
    @Query("select t from AkartPr t "
            + "where t.kart.lsk=?1 and "
            + " (t.mgFrom >=?2 or ?2 between t.mgFrom and t.mgTo)")
    List<AkartPr> getByLskPeriod(String lsk, Integer period);

    /**
     * Получить все элементы по lsk
     * @param lsk - лиц.счет
     */
    @Query("select t from AkartPr t "
            + "where t.kart.lsk = ?1")
    List<AkartPr> getByLsk(String lsk);


    /**
     * Получить все элементы Kart, >= заданного лс, по которым есть записи AkartPr
     * @param firstLsk - заданный лс
     */
    @Query("select distinct t from AkartPr a join a.kart t "
            + " where t.id >= ?1 order by t.id")
    List<Kart> getAfterLsk(String firstLsk);

}
