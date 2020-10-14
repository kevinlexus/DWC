package com.dic.bill.dao;

import com.dic.bill.dto.KartExtPaymentRec;
import com.dic.bill.model.scott.Akwtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AkwtpDAO extends JpaRepository<Akwtp, Integer> {

    /**
     * Получить платеж по № извещения (из ГИС ЖКХ)
     * @param numDoc - № извещения (из ГИС ЖКХ)
     * @return
     */
    @Query(value = "select t from Akwtp t "
            + "where t.numDoc=:numDoc and t.mg between :mgFrom and :mgTo")
    Akwtp getByNumDoc(@Param("numDoc") String numDoc,
                      @Param("mgFrom") String mgFrom, @Param("mgTo") String mgTo);


    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_ID и FK_KLSK_PREMISE
     */
    @Query(value = "select t.kwtp_id as id, t.dtek as dt, coalesce(e.ext_lsk, e2.ext_lsk) as extLsk, t.summa " +
            "from scott.a_kwtp_day t join scott.kart k on t.lsk=k.lsk\n" +
            "left join scott.kart_ext e on e.fk_klsk_premise=k.fk_klsk_premise\n" +
            "left join scott.kart_ext e2 on e2.fk_klsk_id=k.k_lsk_id\n" +
            "and exists (select * from scott.kart_ext e where e.fk_klsk_premise=k.fk_klsk_premise or e.fk_klsk_id=k.k_lsk_id)\n" +
            "where t.mg=:period\n" +
            "and t.org=:orgId\n" +
            "and t.summa is not null\n" +
            "and t.priznak = 1\n" +
            "and coalesce(e.ext_lsk, e2.ext_lsk) is not null\n" +
            "order by coalesce(e.ext_lsk, e2.ext_lsk) ", nativeQuery = true)
    List<KartExtPaymentRec> getPaymentByPeriod(@Param("period") String period, @Param("orgId") Integer orgId);

    /**
     * Получить платежи по внешним лиц.счетам, используя LSK и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
/*
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.kartExt e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithLsk(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                          @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);
*/


    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_ID и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
/*
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koKw.kartExtByKoKw e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithKoKw(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                           @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);
*/

    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
/*
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koPremise.kartExtByPremise e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithPremise(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                              @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);
*/

    /*
    */
/**
     * Получить платежи по внешним лиц.счетам, используя LSK
     * @param ukId - Id УК
     * @param mg - период
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     *//*

    @Query(value = "select distinct t from Akwtp t join t.kart k join k.kartExt e " +
            " where t.mg=:mg and k.uk.reu=:ukId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithLsk(@Param("ukId") String ukId, @Param("mg") String mg,
                                          @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

*/
/*
    */
/**
     * Получить платежи по внешним лиц.счетам, используя LSK и наборы услуг
     * @param ukId - Id УК
     * @param mg - период
     * @param orgId - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     *//*

    @Query(value = "select distinct t from Akwtp t join t.kart k join k.kartExt e join k.nabor n  " +
            " where t.mg=:mg and k.uk.reu <> :ukId and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithLsk(@Param("ukId") String ukId, @Param("mg") String mg,
                                           @Param("orgId") Integer orgId,
                                           @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

    */
/**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE
     * @param ukId - Id УК
     * @param mg - период
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     *//*

    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koPremise.kartExtByPremise e " +
            " where t.mg=:mg and k.uk.reu=:ukId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithPremise(@Param("ukId") String ukId, @Param("mg") String mg,
                                              @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);


    */
/**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE и наборы услуг
     * @param ukId - Id УК
     * @param mg - период
     * @param orgId - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     *//*

    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koPremise.kartExtByPremise e join k.nabor n " +
            " where t.mg=:mg and k.uk.reu <> :ukId and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithPremise(@Param("ukId") String ukId, @Param("mg") String mg,
                                               @Param("orgId") Integer orgId,
                                               @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

*/
}
