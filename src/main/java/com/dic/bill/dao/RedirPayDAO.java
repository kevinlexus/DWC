package com.dic.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.model.scott.RedirPay;


public interface RedirPayDAO extends JpaRepository<RedirPay, Integer> {


	@Query(value = "select t.reu, fk_usl_src, fk_usl_dst, fk_org_src, fk_org_dst, tp, mg1, mg2, id "
		+ "from scott.redir_pay t "
		+ "where t.tp=:tp "
		+ "order by "
		+ "case when t.reu=:pReu then 0 else 1 end, "
		+ "case when t.fk_usl_src=:pUslSrc then 0 else 1 end, "
		+ "case when t.fk_org_src=:pOrgSrc then 0 else 1 end",
		   nativeQuery = true)
	List<RedirPay> getRedirPayOrd(@Param("tp") Integer tp,
			@Param("pReu") String pReu,
			@Param("pUslSrc") String pUslSrc,
			@Param("pOrgSrc") Integer pOrgSrc
			);
}
