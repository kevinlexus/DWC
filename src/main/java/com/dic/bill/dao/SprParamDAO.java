package com.dic.bill.dao;

import com.dic.bill.model.scott.SprParam;
import com.dic.bill.model.scott.Vvod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SprParamDAO extends JpaRepository<SprParam, Integer> {

	/**
	 * Получить строку параметра определенного типа
	 * @param cd - CD параметра
	 * @param cdTp - тип параметра (0 - число, 1- Строка симв, 2-Дата, 3-Логич.,4 - список(одно знач.),5-диапазон, 6-список своих значений)
	 * @return
	 */
	@Query(value = "select t from SprParam t where t.cd=?1 and t.cdTp=?2")
	SprParam getByCD(String cd, int cdTp);

}
