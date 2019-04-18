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

    // закомментировал psch not in (8,9) - не пересчитываются полностью закрытые помещения (закрыты все лиц.счета) ред. 13.03.2019
    @Query(value = "select distinct t.k_lsk_id from TEST201903.KART t where t.reu=:reuId /*and t.PSCH not in (8,9)*/ order by t.k_lsk_id",
            nativeQuery = true)
    List<BigDecimal> findAllKlskIdByReuId(@Param("reuId") String reuId);

    @Query(value = "select distinct t.k_lsk_id from TEST201903.KART t, TEST201903.C_VVOD d where d.house_id=t.house_id " +
            "and d.house_id=:houseId /*and t.PSCH not in (8,9)*/ order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskIdByHouseId(@Param("houseId") long houseId);

    @Query(value = "select distinct t.k_lsk_id from TEST201903.KART t, TEST201903.C_VVOD d, TEST201903.NABOR n " +
            "where d.house_id=t.house_id " +
            "and d.id=:vvodId and t.lsk=n.lsk and n.usl=d.usl and n.FK_VVOD=d.id /*and t.PSCH not in (8,9)*/ " +
            "order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskIdByVvodId(@Param("vvodId") long vvodId);

    @Query(value = "select distinct t.k_lsk_id from TEST201903.KART t /*where t.psch not in (8,9)*/ order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskId();
}
