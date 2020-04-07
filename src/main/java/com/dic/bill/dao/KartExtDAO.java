package com.dic.bill.dao;

import com.dic.bill.model.scott.KartExt;
import com.dic.bill.model.scott.Kwtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO внешних лиц.счетов
 *
 * @author Leo
 */
@Repository
public interface KartExtDAO extends JpaRepository<KartExt, Integer> {

    Optional<KartExt> findByExtLsk(String extLsk);

    /**
     * Получить внешние лиц.счета по дому и квартире
     */
    @Query(value = "select t from KartExt t join t.kart k " +
            " where k.house.id=:houseId and k.num=:kw and k.psch not in (8,9) and t.v=1")
    List<KartExt> getKartExtByHouseIdAndKw(@Param("houseId") Integer houseId, @Param("kw") String kw);


    /**
     * Получить платежи по внешним лиц.счетам
     * @param ukId - Id УК
     */
    @Query(value = "select distinct t from Kwtp t join t.kart k join k.kartExt e " +
            " where k.uk.reu=:ukId order by t.id")
    List<Kwtp> getKwtpKartExtByReu(@Param("ukId") String ukId);

}
