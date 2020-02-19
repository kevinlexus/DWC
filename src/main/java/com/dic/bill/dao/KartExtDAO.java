package com.dic.bill.dao;

import com.dic.bill.model.scott.KartExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO внешних лиц.счетов
 *
 * @author Leo
 */
@Repository
public interface KartExtDAO extends JpaRepository<KartExt, Integer> {

    Optional<KartExt> findByExtLsk(String extLsk);

}
