package com.dic.bill.dao;

import com.dic.bill.model.scott.ChargePay;
import com.dic.bill.model.scott.ChargePayId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface ChargePayDAO extends JpaRepository<ChargePay, ChargePayId> {

    @Query("select t from ChargePay t where " +
            "t.period = :period and t.kart.lsk = :lsk")
    ChargePay getByLsk(@Param("lsk") String lsk, @Param("period") String period);

}
