package com.dic.bill.dao;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.KartPr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface KartPrDAO extends JpaRepository<KartPr, Integer> {


}
