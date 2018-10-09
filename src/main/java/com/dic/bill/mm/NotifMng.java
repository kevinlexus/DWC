package com.dic.bill.mm;

import java.util.List;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Notif;

public interface NotifMng {

	public List<Notif> getNotifForLoadByHouse(Eolink houseEol);

}