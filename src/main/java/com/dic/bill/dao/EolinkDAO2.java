package com.dic.bill.dao;

import com.dic.bill.dto.HouseUkTaskRec;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Pdoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EolinkDAO2 extends JpaRepository<Eolink, Integer> {


	/**
	 * Получить все дома из Eolink, по которым
	 * НЕТ созданных заданий определенного типа действия содержащих обрабатываемый УК
	 * используется например для выгрузки лиц.счетов, для загрузки ПД
	 * @param masterTaskCD - CD ведущего задания (например 'GIS_EXP_HOUSE')
	 * @param checkTaskCD - CD задания для проверки (например 'GIS_EXP_ACCS')
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
	 * @param klskId - Id объекта типа KO
	 */
	@Query(value = "select t from Eolink t where t.koObj.id=:klskId")
	List<Eolink> getEolinkByKlskId(@Param("klskId") Long klskId);

/*
	*/
/**
	 * Найти незагруженные Лиц.счета, по помещениям, входящим в подъезд, по Дому, по УК
	 * @param eolinkId - Id дома
	 * @param ukId - Id владеющая лиц.счетом УК
	 *//*

	@Query("select p from Eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where s.uk.id=:eolinkUkId " // УК
			+ "and s.guid = null " // незагруженные
			+ "order by s.id")
	List<Eolink> getEolLskForLoadByHouseWithEntry(@Param("eolinkId") Integer eolinkId,
										  @Param("eolinkUkId") Integer ukId);

	*/
/**
	 * Найти незагруженные Лиц.счета, по помещениям, входящим в подъезд, по Дому, по УК
	 * @param eolinkId - Id дома
	 * @param ukId - Id владеющая лиц.счетом УК
	 *//*

	@Query("select p from Eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where s.uk.id=:eolinkUkId " // УК
			+ "and s.guid = null " // незагруженные
			+ "order by s.id")
	List<Eolink> getEolLskForLoadByHouseWOEntry(@Param("eolinkId") Integer eolinkId,
												@Param("eolinkUkId") Integer ukId);
*/

}
