package com.dic.bill.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dic.bill.model.scott.PenDt;
import com.dic.bill.model.scott.PenRef;

import com.dic.bill.model.scott.StatePr;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO, для хранения необходимых данных для расчета пени, начисления
 *
 * @author Lev
 *
 */
@Getter @Setter
public class CalcStore {
	// дата начала периода, нужна здесь, в случае если нужно будет задать даты не текущего периода (из ConfigApp)
	// например при тестировании
	Date curDt1;
	// дата окончания периода, нужна здесь, в случае если нужно будет задать даты не текущего периода (из ConfigApp)
	// например при тестировании
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

	// кол-во дней в месяце
	public int getCntCurDays() {
		return Utl.getCntDaysByDate(getCurDt1());
	}

	// доля одного дня в периоде
	public BigDecimal getDayPartMonth() {
		return BigDecimal.valueOf(1/Double.valueOf(getCntCurDays()));
	}


}
