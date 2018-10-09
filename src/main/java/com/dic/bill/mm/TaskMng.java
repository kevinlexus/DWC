package com.dic.bill.mm;

import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.exs.Task;

public interface TaskMng {

	public void setState(Task task, String state);
	public void setResult(Task task, String result);
	public void clearAllResult(Task task);
	public void setEolinkIdf(Eolink eo, String guid, String un, Integer status);
	public Task getByTguid(Task task, String tguid);
	void logTask(Task task, boolean isStart, Boolean isSucc);
}