package com.dic.bill;

import java.util.List;
import com.dic.bill.model.bs.Dw;
import com.dic.bill.model.scott.Ko;

/**
 * Интерфейс - объект квартплаты
 * @author lev
 *
 */
public interface Storable  {
	//klsk объекта (в каждом подклассе переписать методы!)
	//Integer getKlskId();
	//void setKlskId(Integer klsk);
	Ko getKo();
	void setKo(Ko ko);
	public List<Dw> getDw();
	public void setDw(List<Dw> dw);

}
