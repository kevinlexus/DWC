package com.dic.bill.dao;

import com.dic.bill.model.scott.LoadKartExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO загрузки внешних лиц.счетов
 *
 * @author Leo
 */
@Repository
public interface LoadKartExtDAO extends JpaRepository<LoadKartExt, Integer> {

    List<LoadKartExt> findByExtLsk(String extLsk);

    /**
     * Найти лиц.счета, несуществующие в KART_EXT, со статусом успешной проверки
     */
    @Query(value = "from LoadKartExt t where t.status=0 and not exists (select d from KartExt d where  " +
            "d.extLsk=t.extLsk)")
    List<LoadKartExt> findApprovedForLoadFormat0();

    /**
     * Обработать все, предназначенные для загрузки внешние лиц.счета
     */
    @Query(value = "from LoadKartExt t where t.status not in (1,2,10)")
    List<LoadKartExt> findApprovedForLoadFormat1();

}
