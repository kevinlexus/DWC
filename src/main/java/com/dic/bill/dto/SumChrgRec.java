package com.dic.bill.dto;

import com.dic.bill.model.exs.Ulist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * Projection для хранения записи начисления
 * @author - Lev
 * @ver 1.01
 */
public interface SumChrgRec {
	// Id услуги из ГИС
	Integer getUlistId();
	// начисление
	Double getChrg();
	// перерасчет
	Double getChng();
	// объем
	Double getVol();
	// цена
	Double getPrice();
	// норматив (обычно - коэфф. для услуги повыш.коэфф)
	Double getNorm();
	// общая площадь (работает совместно с вспомогательным коэффициентом)
	Double getSqr();
	// услуга из справочника ГИС
	Ulist getUlist();
	// услуга из справочника ГИС
	void setUlist(Ulist ulist);

}

