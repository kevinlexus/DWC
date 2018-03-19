package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.scott.Acharge;
import com.dic.bill.model.scott.Kart;
import com.ric.bill.dto.SumChrgRec;

/**
 * Методы доступа к таблице начислений приложения типа appTp=2
 * @author Leo
 *
 */
@Repository
public interface AchargeDAO extends JpaRepository<Acharge, Integer> {

	/**
	 * Получить сгруппированные записи начислений связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param orgId - Id организации, по которой выбирается начисление (для обработки справочника №1 (доп.услуг) или №51 (коммун.услуг))
	 * @param lsk - лицевой счет
	 * @param mg - период
	 * @param type - тип записи 0 - начислено без учета льгот, 1 - начислено с учетом льгот
	 * @return
	 */
/*	@Query(value = "select new com.ric.bill.dto.SumChrgRec(s, sum(t.summa), sum(t.testOpl), sum(t.testCena)) from Acharge t join Usl u with t.usl=u.id "
			+ "join ServGis s with u.id=s.usl.id "
			+ "where t.lsk = ?1 and ?2 between t.mgFrom and t.mgTo "
			+ "and t.type = ?3 "
			//+ "and ?4 = ?4 "
			//+ "and nvl(s.org.id, ?4) = ?4 " // либо ServGis.fk_eolink - пусто, либо равно orgId 
			+ "group by s")
	  List<SumChrgRec> getGrp(String lsk, Integer mg, Integer type);
	  
	  пока закомментировал - не запускался проект
	  */
    /**
     * Получить все элементы по lsk
     * @param - lsk - лиц.счет
     */
	@Query("select t from Acharge t " 
			+ "where t.lsk = ?1")
	List<Acharge> getByLsk(String lsk);
	
	
	/**
     * Получить все элементы по лиц.счету, начиная с заданного периода
     * @param lsk - лиц. счет
     * @param period - период
     */
	@Query("select t from Acharge t " 
			+ "where t.lsk=?1 and " 
			+" (t.mgFrom >=?2 or ?2 between t.mgFrom and t.mgTo)")
	List<Acharge> getByLskPeriod(String lsk, Integer period);
    
	/**
     * Получить все элементы Kart, >= заданного лс, по которым есть записи Acharge
     * @param firstLsk - заданный лс
     */
	@Query("select distinct t from Kart t join Acharge a "
			+ " with a.lsk=t.lsk where t.lsk >= ?1 order by t.lsk")
	List<Kart> getAfterLsk(String firstLsk);

	
}
