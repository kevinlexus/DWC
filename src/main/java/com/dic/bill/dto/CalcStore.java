package com.dic.bill.dto;

import java.util.Date;
import java.util.List;

import com.dic.bill.model.scott.PenDt;
import com.dic.bill.model.scott.PenRef;

import com.dic.bill.model.scott.StatePr;
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
	Date curDt1;
	// дата окончания периода
	Date curDt2;
	// дата расчета
	Date genDt;
	// текущий период
	Integer period;
	// период - месяц назад
	Integer periodBack;
	// справочник дат начала пени
	List<PenDt> lstPenDt;
	// справочник ставок рефинансирования
	List<PenRef> lstPenRef;
	// уровень отладочной информации
	Integer debugLvl;

}
