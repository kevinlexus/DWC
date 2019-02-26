package com.dic.bill.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.dic.bill.model.scott.PenDt;
import com.dic.bill.model.scott.PenRef;

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
	// список Id рассчитываемых элементов
	List<Long> lstItems = new ArrayList<>();

	// доля одного дня в периоде
	public BigDecimal getPartDayMonth() {
		BigDecimal oneDay = new BigDecimal("1");
		BigDecimal monthDays = BigDecimal.valueOf(Utl.getCntDaysByDate(getCurDt1()));
		return oneDay.divide(monthDays, 20, RoundingMode.HALF_UP);
	}
}
