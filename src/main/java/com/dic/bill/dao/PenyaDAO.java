package com.dic.bill.dao;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Penya;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PenyaDAO extends JpaRepository<Penya, Integer> {

    /**
     * Получить все элементы по lsk
     *
     * @param lsk - лиц.счет
     */
    @Query("select t from Penya t "
            + "where t.kart.id = :lsk and nvl(t.summa,0) <> 0 "
            + "order by t.mg1")
    List<Penya> getByLsk(@Param("lsk") String lsk);

    /**
     * Получить лиц.счета, которые имеют задолженность или текущее начисление
     */
    @Query(value = "select distinct k from Kart k join fetch k.penya p ")
    List<Kart> getKartWhereDebitExists();


}
