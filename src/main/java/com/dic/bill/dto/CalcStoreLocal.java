package com.dic.bill.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO, для хранения необходимых данных касающихся конкретного лиц.счета, для расчета пени
 * @author Lev
 *
 */
@Getter @Setter
public class CalcStoreLocal {
	// задолженность предыдущего периода
	List<SumRec> lstDebFlow;
	// текущее начисление
	List<SumRec> lstChrgFlow;
	// перерасчеты
	List<SumRec> lstChngFlow;
	// оплата долга
	List<SumRec> lstPayFlow;
	// оплата пени
	List<SumRec> lstPayPenFlow;
	// корректировки оплаты
	List<SumRec> lstPayCorrFlow;
	// корректировки начисления пени
	List<SumRec> lstPenChrgCorrFlow;

	// общий список всех финансовых операций
	List<UslOrg> lstAll;

	private void addLst(List<SumRec> lstChrgFlow2) {
		lstAll.addAll(lstChrgFlow2.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.collect(Collectors.toList()));
	}

	public void createUniqUslOrg() {
		lstAll = lstDebFlow.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.collect(Collectors.toList());
		addLst(lstChrgFlow);
		addLst(lstChngFlow);
		addLst(lstPayFlow);
		addLst(lstPayPenFlow);
		addLst(lstPayCorrFlow);
		addLst(lstPenChrgCorrFlow);
	}

	public List<UslOrg> getUniqUslOrg() {
		return lstAll.stream()
		.map(t-> new UslOrg(t.getUslId(), t.getOrgId()))
		.distinct().collect(Collectors.toList());
	}
}
