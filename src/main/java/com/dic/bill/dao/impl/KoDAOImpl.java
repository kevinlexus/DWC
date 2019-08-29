package com.dic.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dic.bill.model.scott.Ko;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import com.dic.bill.dao.KoDAO;
import com.dic.bill.model.bs.AddrTp;


@Slf4j
@Repository
public class KoDAOImpl implements KoDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Получить объект Klsk по klsk
     */
	public Ko getByKlsk(Integer klsk) {
		Query query =em.createQuery("select t from Ko t where t.id = :klsk");
		query.setParameter("klsk", klsk);
		return (Ko)query.getSingleResult();
	}

	/**
	 * Получить список типа Ko по типу адреса и фильтру по наименованию
	 */
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt) {

		Query query = null;
		AddrTp atp = em.find(AddrTp.class, addrTp);
		String addrTpCd = atp.getCd();
		// Зная тип адреса, выбрать соотв.запрос
		if (addrTpCd.equals("РКЦ") || addrTpCd.equals("ЖЭО") || addrTpCd.equals("РЭУ")) {
			query =em.createQuery("select k from com.dic.bill.model.bs.Org o "
					+ "left join o.ko k join o.orgTp t join t.addrTp tp where tp.cd"
					+ " = :addrTpCd and "
					+ "upper(o.name) like fn.p_chrg_part.getstrbypart(:flt,  :par)  "
					+ "order by nvl(o.isMnt, 0) desc, o.name");
			query.setParameter("addrTpCd", addrTpCd);
			query.setParameter("flt", flt);
			query.setParameter("par", 1);
		} else if (addrTpCd.equals("Дом")) {

			query =em.createQuery("select t from House o "
					+ "join o.ko t join o.street s where upper(o.nd) like fn.p_chrg_part.getstrbypart(:flt,  2) "
					+ "and upper(s.name) like fn.p_chrg_part.getstrbypart(:flt,  1) "
					+ "order by s.name, o.nd");
			query.setParameter("flt", flt);

		}
		return query.getResultList();
	}

}
