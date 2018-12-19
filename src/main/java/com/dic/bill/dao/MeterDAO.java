package com.dic.bill.dao;

import com.dic.bill.dto.MeterData;
import com.dic.bill.model.scott.Apenya;
import com.dic.bill.model.scott.ApenyaId;
import com.dic.bill.model.scott.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

/**
 * DAO сущности Meter
 * @author Lev
 * @version 1.00
 *
 */
@Repository()
public interface MeterDAO extends JpaRepository<Meter, Integer> {


	/**
	 * Получить все актуальные счетчики по объекту Ko, коду услуги
	 * @param koId - объект Ko, к которому прикреплен счетчик
	 * @param uslId - код услуги
	 * @return
	 */
	@Query(value = "select t from Meter t "
			+ "where t.koObj.id = ?1 and t.usl.id = ?2 " +
			"and ?3 between t.dt1 and t.dt2")
	List<Meter> findActualByKoUsl(Integer koId, String uslId, Date dt);

	/**
	 * Получить Timestamp показаний и GUID счетчиков, по которым они были приняты
	 * @param userCd - CD внёсшего пользователя
	 * @param period - период
	 * @param lstCd - тип действия
	 * @return
	 */
	@Query(value = "select t.ts as ts, t.ko.eolink.guid as guid from ObjPar t "
			+ "where t.tuser.cd = ?1 and t.lst.cd=?2 and t.mg=?3")
	List<MeterData> findMeteringDataTsByUser(String userCd, String lstCd, String period);

	/**
	 * ТЕСТОВЫЙ МЕТОД - ПРОВЕРЯЛ LockModeType.PESSIMISTIC_READ
	 * */
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query(value = "select t from Meter t "
			+ "where t.id between ?1 and ?2")
	List<Meter> findMeter(int n1, int n2);

}
