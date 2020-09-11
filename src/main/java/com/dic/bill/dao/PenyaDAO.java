package com.dic.bill.dao;

import com.dic.bill.model.scott.Kart;
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
     * @param ukId - Id УК
     */
    @Query(value = "select distinct k from Kart k left join fetch k.penya p where k.uk.id=:ukId")
    List<Kart> getKartWithDebitByReu(@Param("ukId") Integer ukId);

    /**
     * Получить лиц.счета, которые имеют задолженность или текущее начисление
     * @param grpDeb - группа организаций для объединения задолженности в один файл
     */
    @Query(value = "select distinct k from Kart k left join fetch k.penya p where k.uk.grpDeb=:grpDeb")
    List<Kart> getKartWithDebitByGrpDeb(@Param("grpDeb") Integer grpDeb);

}
