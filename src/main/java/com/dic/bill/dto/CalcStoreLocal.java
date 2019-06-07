package com.dic.bill.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * Хранилище необходимых данных касающихся конкретного лиц.счета, для расчета пени
 * @author Lev
 *
 */
@Getter @Setter
public class CalcStoreLocal {
	// задолженность предыдущего периода
	List<SumDebPenRec> lstDebFlow;
	// текущее начисление
	List<SumRec> lstChrgFlow;
	// перерасчеты
	List<SumRec> lstChngFlow;
	// оплата долга
	List<SumRec> lstPayFlow;
	// корректировки оплаты
	List<SumRec> lstPayCorrFlow;
	// общий список всех финансовых операций
	List<UslOrg> lstAll;
	// код УК в числовом выражении (для ускорения фильтров)
	int reuId;

	// добавить список услуга+орг.
	private void addLst(List<SumRec> lstChrgFlow2) {
		lstAll.addAll(lstChrgFlow2.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.collect(Collectors.toList()));
	}

	// добавить список услуга+орг.
	private void addLst2(List<SumDebPenRec> lstChrgFlow2) {
		lstAll.addAll(lstChrgFlow2.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.collect(Collectors.toList()));
	}

	// создать список всех услуг+орг.
	public void createUniqUslOrg() {
		lstAll = lstDebFlow.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.collect(Collectors.toList());
		addLst(lstChrgFlow);
		addLst(lstChngFlow);
		addLst(lstPayFlow);
		addLst(lstPayCorrFlow);
	}

	// получить уникальный список услуг + организаций
	public List<UslOrg> getUniqUslOrg() {
		return lstAll.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.distinct().collect(Collectors.toList());
	}
}
