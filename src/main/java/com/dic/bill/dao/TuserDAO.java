package com.dic.bill.dao;

import com.dic.bill.model.scott.Tuser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuserDAO extends JpaRepository<Tuser, Integer> {

	Tuser findByCd(String cd);

}
