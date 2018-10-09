package com.dic.bill.dao;

import com.dic.bill.model.exs.Task;
import com.dic.bill.model.exs.TaskPar;


public interface TaskParDAO {


	public TaskPar getTaskPar(Task task, String parCd);

}
