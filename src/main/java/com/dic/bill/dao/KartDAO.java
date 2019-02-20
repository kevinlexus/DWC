package com.dic.bill.dao;

import com.dic.bill.dto.SumRecMg;
import com.dic.bill.model.scott.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface KartDAO extends JpaRepository<Kart, String> {

    @Query("select t from Kart t where t.kul = :kul and t.nd=:nd and t.num=:kw")
    List<Kart> findByKulNdKw(@Param("kul") String kul, @Param("nd") String nd, @Param("kw") String kw);

    @Query(value = "select distinct t.k_lsk_id from TEST.KART t order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskId();
}
