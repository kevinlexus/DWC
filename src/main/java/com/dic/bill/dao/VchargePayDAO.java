package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dic.bill.model.scott.Lst;

/**
 * DAO V_chargePay
 * @author Lev
 * @version 1.00
 */
public interface VchargePayDAO extends JpaRepository<Lst, Integer> { // Не знаю что написать в кач-ве первого аргумента, оставил Lst2


}
