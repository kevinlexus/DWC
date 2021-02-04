package com.dic.bill.dao;

import com.dic.bill.model.scott.Usl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UslDAO extends JpaRepository<Usl, String> {

	Optional<Usl> findByCd(String cd);

}
