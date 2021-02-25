package com.dic.bill.dao;

import com.dic.bill.dto.KartLsk;
import com.dic.bill.model.scott.Kart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface KartDAO extends JpaRepository<Kart, String> {

    @Query("select t from Kart t where t.kul = :kul and t.nd=:nd and t.num=:kw")
    List<Kart> findByKulNdKw(@Param("kul") String kul, @Param("nd") String nd, @Param("kw") String kw);

    // закомментировал psch not in (8,9) - не пересчитываются полностью закрытые помещения (закрыты все лиц.счета) ред. 13.03.2019
    @Query(value = "select distinct t.k_lsk_id from SCOTT.KART t where t.reu=:reuId /*and t.PSCH not in (8,9)*/ order by t.k_lsk_id",
            nativeQuery = true)
    List<BigDecimal> findAllKlskIdByReuId(@Param("reuId") String reuId);

    @Query(value = "select distinct t.k_lsk_id from SCOTT.KART t, SCOTT.C_VVOD d where d.house_id=t.house_id " +
            "and d.house_id=:houseId /*and t.PSCH not in (8,9)*/ order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskIdByHouseId(@Param("houseId") long houseId);

    @Query(value = "select distinct t.k_lsk_id from SCOTT.KART t, SCOTT.C_VVOD d, SCOTT.NABOR n " +
            "where d.house_id=t.house_id " +
            "and d.id=:vvodId and t.lsk=n.lsk and n.usl=d.usl and n.FK_VVOD=d.id /*and t.PSCH not in (8,9)*/ " +
            "order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskIdByVvodId(@Param("vvodId") long vvodId);

    @Query(value = "select distinct t.k_lsk_id from SCOTT.KART t /*where t.psch not in (8,9)*/ order by t.k_lsk_id", nativeQuery = true)
    List<BigDecimal> findAllKlskId();

    @Query("select t from Kart t where t.uk.reu=:reu and t.tp.cd=:tpCd " +
            "and t.house.id = :houseId and t.num=:kw and t.psch not in (8,9)")
    List<Kart> findActualByReuHouseIdTpKw(@Param("reu") String reu, @Param("tpCd") String tpCd,
                                          @Param("houseId") Integer houseId, @Param("kw") String kw);

    //@EntityGraph(attributePaths = {"kartDetail", "kartPr"})
    @Query("select t.lsk as lsk, t.koKw.id as klskId, t.psch as psch from Kart t " +
            "where t.house.id = :houseId and t.num=:kw")
    List<KartLsk> findByHouseIdKw(@Param("houseId") Integer houseId, @Param("kw") String kw);

    @EntityGraph(attributePaths = {"kartDetail"})
    @Query("select t from Kart t where t.lsk='00000007'")
    List<Kart> findAllForOrdering();

    @Query("select distinct t from Kart t join t.nabor n where t.tp.cd=:tpCd and n.usl.id=:uslId " +
            "and t.house.id = :houseId and t.num=:kw and t.psch not in (8,9)")
    List<Kart> findActualByUslHouseIdTpKw(@Param("tpCd") String tpCd,
                                          @Param("uslId") String uslId,
                                          @Param("houseId") Integer houseId, @Param("kw") String kw);

    @Query("select t from Kart t where t.uk.reu=:reu and t.tp.cd=:tpCd " +
            "and t.psch not in (8,9) and t.status.cd in (:statusLst) order by t.kartDetail.ord1")
    List<Kart> findActualByReuStatusOrderedByAddress(@Param("reu") String reu,
                                                     @Param("statusLst") List<String> statusLst, @Param("tpCd") String tpCd);


}
