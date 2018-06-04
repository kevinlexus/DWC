package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRec;
import com.dic.bill.model.scott.PenUslCorr;


public interface PenUslCorrDAO extends JpaRepository<PenUslCorr, Integer> {

	/**
	 * Получить записи корректировок пени по услугам
	 * @param lsk - лицевой счет
	 * @param period - бухгалтерский период
	 * @return
	 */
	@Query(value = "select '003' as uslId, t.org.id as orgId, t.penya as summa, " /*t.usl.idВРЕМЕННО###*/
			+ "t.mgchange as mg, t.dt as dt, 7 as tp from PenUslCorr t where t.kart.lsk=:lsk "
			+ "and nvl(t.penya,0) <> 0 ")
	List<SumRec> getPenUslCorrByLsk(@Param("lsk") String lsk);

}