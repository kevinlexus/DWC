package com.dic.bill.dao;

import com.dic.bill.dto.HouseUkTaskRec;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Kart;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NoResultException;
import java.util.List;

public interface EolinkDAO2 extends JpaRepository<Eolink, Integer> {


    /**
     * Получить все дома из Eolink, по которым
     * НЕТ созданных заданий определенного типа действия содержащих обрабатываемый УК
     * используется например для выгрузки лиц.счетов, для загрузки ПД
     *
     * @param masterTaskCD - CD ведущего задания (например 'GIS_EXP_HOUSE')
     * @param checkTaskCD  - CD задания для проверки (например 'GIS_EXP_ACCS')
     */
    @Query(value = "select distinct t.id as eolHouseId, uk.id as eolUkId, " +
            "s2.id as masterTaskId from exs.eolink t "
            + "join bs.addr_tp tp on t.fk_objtp=tp.id and tp.cd='Дом' " // по объектам Дом
            + "join scott.kart k on t.kul=k.kul and t.nd=k.nd and k.psch not in (8,9) " // по Kart поиск лиц.счетов УК
            + "join bs.addr_tp tp2 on tp2.cd='Организация' "
            + "join exs.eolink uk on uk.fk_objtp=tp2.id and tp2.parent_id is not null and k.reu=uk.reu " // УК
            + "join bs.list stp2 on stp2.cd=:masterTaskCD "
            + "join exs.task s2 on s2.fk_eolink=t.id and s2.fk_act=stp2.id " // ведущее задание, к которому прикреплять
            + "where not exists " // где нет заданий указанного типа
            + "		(select * from exs.task s join bs.list stp on s.fk_act=stp.id and stp.cd=:checkTaskCD  "
            + "				where s.fk_proc_uk=uk.id "
            + "		)", nativeQuery = true)
    List<HouseUkTaskRec> getHouseByTpWoTaskTp(@Param("masterTaskCD") String masterTaskCD,
                                              @Param("checkTaskCD") String checkTaskCD);

    /**
     * Получить все дома из Eolink, по которым
     * НЕТ созданных заданий определенного типа действия содержащих обрабатываемый УК
     * используется например для выгрузки лиц.счетов, для загрузки ПД
     * БЕЗ ведущего задания
     *
     * @param checkTaskCD - CD задания для проверки (например 'GIS_EXP_ACCS')
     */
    @Query(value = "select distinct t.id as eolHouseId, uk.id as eolUkId, " +
            "null as masterTaskId from exs.eolink t "
            + "join bs.addr_tp tp on t.fk_objtp=tp.id and tp.cd='Дом' " // по объектам Дом
            + "join scott.kart k on t.kul=k.kul and t.nd=k.nd and k.psch not in (8,9) " // по Kart поиск лиц.счетов УК
            + "join bs.addr_tp tp2 on tp2.cd='Организация' "
            + "join exs.eolink uk on uk.fk_objtp=tp2.id and tp2.parent_id is not null and k.reu=uk.reu " // УК
            + "where not exists " // где нет заданий указанного типа
            + "		(select * from exs.task s join bs.list stp on s.fk_act=stp.id and stp.cd=:checkTaskCD  "
            + "				where s.fk_proc_uk=uk.id "
            + "		)", nativeQuery = true)
    List<HouseUkTaskRec> getHouseByTpWoTaskTp(@Param("checkTaskCD") String checkTaskCD);

    /**
     * Найти объект Eolink по klskId
     *
     * @param klskId - Id объекта типа KO
     */
    @Query(value = "select t from Eolink t where t.koObj.id=:klskId")
    Eolink getEolinkByKlskId(@Param("klskId") Long klskId);

    /*
     * Найти Лиц.счета, по помещениям, входящим в подъезд, по Дому, по УК
     * @param eolHouseId - Id дома
     * @param eolUkId - Id владеющая лиц.счетом УК
     */
    @Query("select s from Eolink s " // лиц.счет
            + "join s.parent k " // квартира
            + "join k.parent e " // подъезд
            + "join e.parent h on h.id=:eolHouseId "  // дом
            + "where s.uk.id=:eolUkId " // УК
            + "order by s.id")
    List<Eolink> getLskEolByHouseWithEntry(@Param("eolHouseId") Integer eolHouseId,
                                           @Param("eolUkId") Integer eolUkId);

    /*
     * Найти Лиц.счета, по помещениям, не входящим в подъезд, по Дому, по УК
     * @param eolHouseId - Id дома
     * @param eolUkId - Id владеющая лиц.счетом УК
     */
    @Query("select s from Eolink s " // лиц.счет
            + "join s.parent k " // квартира
            + "join k.parent h on h.id=:eolHouseId "  // дом
            + "where s.uk.id=:eolUkId " // УК
            + "order by s.id")
    List<Eolink> getLskEolByHouseWOEntry(@Param("eolHouseId") Integer eolHouseId,
                                         @Param("eolUkId") Integer eolUkId);


    /**
     * Найти открытые лиц.счета из Kart, которых нет в Eolink, но по которым есть помещения в Eolink
     * @param eolHouseId -
     */
/*
    @Query("select k from Kart k "
            + "where not exists (select e.id from Eolink e where e.kart=k and e.objTp.cd='ЛС') "
            + "and exists (select e from Eolink e where e.koObj=k.koKw and e.objTp.cd='Квартира' "
            + "     and (e.parent.parent.id=:eolHouseId or e.parent.id=:eolHouseId)) " // входящая или не входящая в подъезд
            + "and k.psch not in (8,9)"
    )
*/

    /**
     * Найти открытые лиц.счета из Kart, которых нет в Eolink, но по которым есть помещения в Eolink,
     * входящие в подъезд
     * @param eolHouseId - Id дома
     * @param eolUkId - Id владеющая лиц.счетом УК
     */
    @Query(value = "select k.lsk from SCOTT.KART k " +
            "join EXS.EOLINK t on k.reu=t.reu and t.id=:eolUkId " +
            "join BS.ADDR_TP tp on t.FK_OBJTP=tp.id and tp.cd='Организация' " +
            "where k.psch not in (8,9) and not " +
            "exists (select * from EXS.EOLINK e join BS.ADDR_TP tp on e.FK_OBJTP=tp.ID and tp.cd='ЛС' " +
            "where e.lsk=k.lsk) " +
            "and exists (select * from EXS.EOLINK e join BS.ADDR_TP tp on e.FK_OBJTP=tp.ID and tp.cd='Квартира' " +
            "join EXS.EOLINK p on e.PARENT_ID=p.id join BS.ADDR_TP tp2 on p.FK_OBJTP=tp2.ID and tp2.cd='Подъезд' " +
            "where p.PARENT_ID=:eolHouseId " +
            "and e.FK_KLSK_OBJ=k.K_LSK_ID)", nativeQuery = true)
    List<String> getKartNotExistsInEolinkWithEntry(@Param("eolHouseId") Integer eolHouseId,
                                                 @Param("eolUkId") Integer eolUkId);

    /**
     * Найти открытые лиц.счета из Kart, которых нет в Eolink, но по которым есть помещения в Eolink,
     * НЕ входящие в подъезд
     * @param eolHouseId - Id дома
     * @param eolUkId - Id владеющая лиц.счетом УК
     */
    @Query(value = "select k.lsk from SCOTT.KART k " +
            "join EXS.EOLINK t on k.reu=t.reu and t.id=:eolUkId " +
            "join BS.ADDR_TP tp on t.FK_OBJTP=tp.id and tp.cd='Организация' " +
            "where k.psch not in (8,9) and not " +
            "exists (select * from EXS.EOLINK e join BS.ADDR_TP tp on e.FK_OBJTP=tp.ID and tp.cd='ЛС' " +
            "where e.lsk=k.lsk) " +
            "and exists (select * from EXS.EOLINK e join BS.ADDR_TP tp on e.FK_OBJTP=tp.ID and tp.cd='Квартира' " +
            "where e.PARENT_ID=:eolHouseId " +
            "and e.FK_KLSK_OBJ=k.K_LSK_ID)", nativeQuery = true)
    List<String> getKartNotExistsInEolinkWOEntry(@Param("eolHouseId") Integer eolHouseId,
                                               @Param("eolUkId") Integer eolUkId);

    /**
     * Получить объект по TGUID
     * @param tguid - TGUID
     */
    @Query("select t from Eolink t where t.tguid=:tguid")
    Eolink getEolinkByTGUID(@Param("tguid") String tguid);

}
