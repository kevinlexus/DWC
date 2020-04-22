package com.dic.bill.dao;

import com.dic.bill.model.scott.KartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface KartDetailDAO extends JpaRepository<KartDetail, Integer> {

    @Modifying
    @Query("update KartDetail t set t.ord1=null, t.isMain=false")
    int updateOrd1ToNull();

}
