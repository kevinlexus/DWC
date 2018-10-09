package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.exs.TaskToTask;

public interface TaskToTaskDAO extends JpaRepository<TaskToTask, Integer> {

}
