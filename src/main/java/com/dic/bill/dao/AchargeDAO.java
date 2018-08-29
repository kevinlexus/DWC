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

/*  Было до13.07.2018, убрал ненужную вложенность!
	@Query(value = "select t2.id as \"ulistId\", sum(t2.summa) as \"summa\", sum(t2.vol) as \"vol\", sum(price) as \"price\" from ( "
			+ "select u.id, s.grp, sum(t.summa) as summa, sum(t.test_opl) as vol, min(t.test_cena) as price "
		+ "from scott.a_charge2 t "
		+ "join exs.servgis s on t.usl=s.fk_usl and s.fk_eolink=?3 "
		+ "join exs.u_list u on s.fk_list=u.id "
		+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
		+ "where t.lsk = ?1 and ?2 between t.mgFrom and t.mgTo "
		+ "and NVL(tp.fk_eolink, ?3) = ?3 "
		+ "and t.type = 1 "
		+ "group by u.id, s.grp) t2 "
		+ "group by t2.id", nativeQuery = true)
*/
	/**
	 * Получить сгруппированные записи начислений (полного начисления, без учета льгот),
	 * связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param lsk - лицевой счет
	 * @param period - период
	 * @param eolOrgId - Id организации, по которой выбирается начисление (для обработки справочника №1 (доп.услуг) или №51 (коммун.услуг))
	 * @return
	 */
	@Query(value = "select e.id as \"ulistId\", nvl(a.summa,0) as \"chrg\", nvl(b.summa,0) as \"chng\", " +
            "nvl(a.vol,0) as \"vol\", nvl(a.price,0) as \"price\", nvl(a.sqr,0) as \"sqr\", nvl(a.norm,0) as \"norm\" " +
            "from exs.u_list e left join ( " +
			"select b.ulistId, sum(b.summa) as summa, sum(b.vol) as vol, sum(b.price) as price, " +
            "max(b.sqr) as sqr, max(b.norm) as norm from ( " +
			"select t.usl, u.id as ulistId, sum(t.summa) as summa, " +
			"sum(t.test_opl) as vol, max(t.test_cena) as price, " +
			"max(k.opl) as sqr, max(n.norm) as norm " +
			"from scott.a_charge2 t " + // начисление
			"join scott.kart k on t.lsk=k.lsk and ?2 between t.mgFrom and t.mgTo " +
			"join scott.a_nabor2 n on t.lsk=n.lsk and t.usl=n.usl and ?2 between n.mgFrom and n.mgTo " +
			"join exs.servgis s on t.usl=s.fk_usl and s.fk_eolink=?3 " +
			"join exs.u_list u on s.fk_list=u.id " +
			"join exs.u_listtp tp on u.fk_listtp=tp.id " +
			"where t.lsk = ?1 " +
			"and NVL(tp.fk_eolink, ?3) = ?3 " +
			"and t.type = 1 " +
			"group by t.usl, u.id) b " +
            "group by b.ulistId " +
			") a on e.id=a.ulistId " +
            "left join (select u.id as ulistId, sum(t.summa) as summa " +
            "from scott.a_change t " + // перерасчет
            "join scott.kart k on t.lsk=k.lsk " +
            "join exs.servgis s on t.usl=s.fk_usl and s.fk_eolink=?3 " +
            "join exs.u_list u on s.fk_list=u.id " +
            "join exs.u_listtp tp on u.fk_listtp=tp.id " +
            "where t.lsk = ?1 and ?2 = t.mg " +
            "and NVL(tp.fk_eolink, ?3) = ?3 " +
            "group by u.id) b on e.id=b.ulistId " +
             "where nvl(a.summa,0)<>0 or nvl(b.summa,0)<>0", nativeQuery = true)
	List<SumChrgRec> getChrgGrp(String lsk, Integer period, Integer eolOrgId);
/*
    @Query(value = "select u.id as \"ulistId\", sum(t.summa) as \"summa\", " +
            "sum(t.test_opl) as \"vol\", min(t.test_cena) as \"price\", " +
            "min(k.opl) as \"sqr\" "
            + "from scott.a_charge2 t "
            + "join scott.kart k on t.lsk=k.lsk "
            + "join exs.servgis s on t.usl=s.fk_usl and s.fk_eolink=?3 "
            + "join exs.u_list u on s.fk_list=u.id "
            + "join exs.u_listtp tp on u.fk_listtp=tp.id "
            + "where t.lsk = ?1 and ?2 between t.mgFrom and t.mgTo "
            + "and NVL(tp.fk_eolink, ?3) = ?3 "
            + "and t.type = 1 "
            + "group by u.id", nativeQuery = true)
*/


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
