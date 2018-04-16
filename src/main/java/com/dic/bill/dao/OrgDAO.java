package com.dic.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dic.bill.model.scott.Org;

/**
 * DAO сущности com.dic.bill.model.scott.Org
 * @author Lev
 * @version 1.01
 *
 */
@Repository("OrgDAO_DWC")
public interface OrgDAO extends JpaRepository<Org, Integer> {

	 	
	/**
	 * Получить организацию по коду REU
	 * @param reu - код REU
	 * @return
	 */
	@Query("select t from com.dic.bill.model.scott.Org t where t.reu = ?1")
	 Org getByReu(String reu);
	
	/**
	 * Получить организацию по CD
	 * @param cd - код CD
	 * @return
	 */
	@Query("select t from com.dic.bill.model.scott.Org t where t.cd = ?1")
	 Org getByCD(String cd);

	/**
	 * Получить организацию по Типу орг 
	 * @param cd - код CD
	 * @return
	 */
	@Query("select t from com.dic.bill.model.scott.Org t where t.orgTp.cd = ?1")
	 Org getByOrgTp(String tp);
	
}
