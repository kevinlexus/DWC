package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Kwtp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface KwtpDAO extends JpaRepository<Kwtp, Integer> {

    /**
     * Получить платеж по № извещения (из ГИС ЖКХ)
     * @param numDoc - № извещения (из ГИС ЖКХ)
     */
    @Query(value = "select t from Kwtp t "
            + "where t.numDoc=:numDoc")
    Kwtp getByNumDoc(@Param("numDoc") String numDoc);

    /**
     * Получить платежи по внешним лиц.счетам, используя LSK
     * @param ukId - Id УК
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Kwtp t join t.kart k join k.kartExt e " +
            " where k.uk.reu=:ukId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Kwtp> getKwtpKartExtByReuWithLsk(@Param("ukId") String ukId,
                                   @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

    /**
     * Получить платежи по внешним лиц.счетам, используя LSK и наборы услуг
     * @param ukId - Id УК
     * @param orgId - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Kwtp t join t.kart k join k.kartExt e join k.nabor n " +
            " where k.uk.reu <> :ukId and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Kwtp> getKwtpKartExtByReuWithLsk(@Param("ukId") String ukId, @Param("orgId") Integer orgId,
                                          @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE
     * @param ukId - Id УК
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Kwtp t join t.kart k join k.koPremise.kartExtByPremise e " +
            " where k.uk.reu = :ukId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Kwtp> getKwtpKartExtByReuWithPremise(@Param("ukId") String ukId,
                                   @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

    /**
     * Получить платежи по внешним лиц.счетам, используя FK_KLSK_PREMISE и наборы услуг
     * @param ukId - Id УК
     * @param orgId - Id организации
     * @param genDt1 - дата начала
     * @param genDt2 - дата окончания
     */
    @Query(value = "select distinct t from Kwtp t join t.kart k join k.koPremise.kartExtByPremise e join k.nabor n " +
            " where k.uk.reu <> :ukId and n.org.id=:orgId and t.dtInk between :genDt1 and :genDt2 order by t.id")
    List<Kwtp> getKwtpKartExtByReuWithPremise(@Param("ukId") String ukId, @Param("orgId") Integer orgId,
                                              @Param("genDt1") Date genDt1, @Param("genDt2") Date genDt2);

}
