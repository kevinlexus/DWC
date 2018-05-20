package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Change;


public interface ChangeDAO extends JpaRepository<Change, Integer> {


}
