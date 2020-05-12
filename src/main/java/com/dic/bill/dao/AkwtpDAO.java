package com.dic.bill.dao;

import com.dic.bill.model.scott.Akwtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
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
     * Получить платежи по внешним лиц.счетам, используя LSK и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.kartExt e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithLsk(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                          @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);


    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_ID и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koKw.kartExtByKoKw e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithKoKw(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                           @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE и наборы услуг
     *
     * @param mg - период
     * @param orgId  - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Akwtp t join t.kart k join k.koPremise.kartExtByPremise e join k.nabor n " +
            " where t.mg=:mg and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Akwtp> getKwtpKartExtByReuWithPremise(@Param("mg") String mg, @Param("orgId") Integer orgId,
                                              @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

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
