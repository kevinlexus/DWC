package com.dic.bill.dto;

import java.util.Date;
import java.util.List;

import com.dic.bill.model.scott.SprPenUsl;
import com.dic.bill.model.scott.StavrUsl;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO, для хранения необходимых данных для расчета пени
 * @author Lev
 *
 */
@Getter @Setter
public class CalcStore {
	// дата начала периода
	Date dt1;
	// дата окончания расчета
	Date genDt;
	// текущий период
	Integer period;
	// период - месяц назад
	Integer periodBack;
	// справочник дат начала пени
	List<SprPenUsl> lstSprPenUsl;
	// справочник ставок рефинансирования
	List<StavrUsl> lstStavrUsl;
	// уровень отладочной информации
	Integer debugLvl;

}
