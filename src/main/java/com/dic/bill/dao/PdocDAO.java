package com.dic.bill.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.exs.Pdoc;

/**
 * DAO сущности ПД - Pdoc
 * @author lev
 * @version 1.00
 *
 */
@Repository
public interface PdocDAO extends JpaRepository<Pdoc, Integer> {

	/**
	 * Получить список незагруженных ПД в ГИС по Дому, по помещениям в подъездах
	 * отсортированно по номеру документа в биллинге
	 * @param eolinkId - Id дома
	 * @param dt - дата ПД
	 * @return
	 */
	@Query("select p from Pdoc p join p.eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where (p.status=0 and p.v=1 or p.status=1 and p.v=0) " // либо добавленные, но не загруженные, либо отмененные и не отмененные в ГИС
			+ "and p.dt=:dt "
			+ "order by p.cd")
	  List<Pdoc> getForLoadByHouseWithEntry(@Param("eolinkId") Integer eolinkId, @Param("dt") Date dt);

	/**
	 * Получить список незагруженных ПД в ГИС по Дому, по УК, по помещениям в подъездах
	 * отсортированно по номеру документа в биллинге
	 * @param eolinkId - Id дома
	 * @param ukId - Id владеющая лиц.счетом УК
	 * @param dt - дата ПД (РСО)
	 * @return
	 */
	@Query("select p from Pdoc p join p.eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where s.uk.id=:eolinkUkId " // УК
			+ "and (p.status=0 and p.v=1 or p.status=1 and p.v=0) " // либо добавленные, но не загруженные, либо отмененные и не отмененные в ГИС
			+ "and p.dt=:dt "
			+ "order by p.cd")
	List<Pdoc> getForLoadByHouseWithEntry(@Param("eolinkId") Integer eolinkId,
											@Param("eolinkUkId") Integer ukId,
											@Param("dt") Date dt);

	/**
	 * Получить список незагруженных ПД в ГИС по Дому, по помещениям без подъездов
	 * отсортированно по номеру документа в биллинге
	 * @param eolinkId - ID дома
	 * @param dt - дата ПД
	 * @return
	 */
	@Query("select p from Pdoc p join p.eolink s " // лиц.счет
			+ "join s.parent h on h.id=:eolinkId "  // дом
			+ "where (p.status=0 and p.v=1 or p.status=1 and p.v=0) " // либо добавленные, но не загруженные, либо отмененные и не отмененные в ГИС
			+ "and p.dt=:dt "
			//+ "and nvl(p.err,0) = 0 " // без ошибок загрузки -  пока убрал ред.16.08.2018
			+ "order by p.cd")
	  List<Pdoc> getForLoadByHouseWOEntry(@Param("eolinkId") Integer eolinkId, @Param("dt") Date dt);


	/**
	 * Получить список Загруженных ПД в ГИС по Дому, по помещениям в подъездах
	 * @param eolinkId - Id дома
	 * @return
	 */
	@Query("select p from Pdoc p join p.eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where (p.status=1 and p.v=1) " // загруженные ПД
			+ "order by p.cd")
	List<Pdoc> getForCheckNotifByHouseWithEntry(@Param("eolinkId") Integer eolinkId);

	/**
	 * Найти ПД по TGUID
	 * @param tguid - транспортный GUID
	 * @return
	 */
	@Query("select p from Pdoc p "
			+ "where p.tguid = :tguid ")
	  Pdoc getByTGUID(@Param("tguid") String tguid);

	/**
	 * Найти ПД по CD
	 * @param cd - CD (№ документа в биллинге)
	 * @return
	 */
	@Query("select p from Pdoc p "
			+ "where p.cd = :cd ")
	Pdoc getByCD(@Param("cd") String cd);

}
