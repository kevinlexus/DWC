package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.Kart;
import com.ric.bill.dto.SumChrgRec;

/**
 * DAO сущности начислений - Acharge (эксп.разработка)
 * @author Leo
 *
 */
@Repository
public interface AchargeDAO extends JpaRepository<Acharge, Integer> {

	/**
	 * Получить сгруппированные записи начислений (полного начисления, без учета льгот),
	 * связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param lsk - лицевой счет
	 * @param mg - период
	 * @param orgId - Id организации, по которой выбирается начисление (для обработки справочника №1 (доп.услуг) или №51 (коммун.услуг))
	 * @return
	 */
	@Query(value = "select t2.id as \"ulistId\", sum(t2.summa) as \"summa\", sum(t2.vol) as \"vol\", sum(price) as \"price\" from ( "
			+ "select u.id, s.grp, sum(t.summa) as summa, sum(t.testOpl) as vol, min(t.testCena) as price "
		+ "from scott.a_charge2 t "
		+ "join exs.servgis s on t.fk_usl=s.fk_usl "
		+ "join exs.u_list u on s.fk_list=u.id "
		+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
		+ "where t.lsk = ?1 and ?2 between t.mgFrom and t.mgTo "
		+ "and NVL(tp.fk_eolink, ?3) = ?3 "
		+ "and t.type = 0 "
		+ "group by u.id, s.grp) t2 "
		+ "group by t2.id", nativeQuery = true)
	List<SumChrgRec> getChrgGrp(String lsk, Integer period, Integer orgId);

    /**
     * Получить все элементы по lsk
     * @param - lsk - лиц.счет
     */
	@Query("select t from Acharge t "
			+ "where t.kart.id = ?1")
	List<Acharge> getByLsk(String lsk);


	/**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
	@Query("select t from Acharge t "
			+ "where t.kart.id=?1 and "
			+" (t.mgFrom >=?2 or ?2 between t.mgFrom and t.mgTo)")
	List<Acharge> getByLskPeriod(String lsk, Integer period);

	/**
     * Получить все элементы Kart, >= заданного лс, по которым есть записи Acharge
     * @param firstLsk - заданный лс
     */
	@Query("select distinct t from Acharge a join a.kart t "
			+ " where t.id >= ?1 order by t.id")
	List<Kart> getAfterLsk(String firstLsk);


}
