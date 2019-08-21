package com.dic.bill.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.dic.bill.dao.EolinkDAO;
import com.dic.bill.model.exs.Eolink;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
public class EolinkDAOImpl implements EolinkDAO {

    @PersistenceContext
    private EntityManager em;

    //конструктор
    public EolinkDAOImpl() {

    }

/*
    //вернуть список необработанных действий
    @Override
	public List<Eolink> getAll() {
			Query query =em.createQuery("from Eolink t");
			return query.getResultList();
	}

*/
    /**
     * Получить объект дом по параметрам
     * @param kul - код улицы
     * @param nd - номер дома
     * @return
     */
    @Override
    public Eolink getHouseByKulNd(String kul, String nd) {
        Query query =em.createQuery("select t from com.dic.bill.model.exs.Eolink t " +
                "join t.objTp tp where tp.cd=:tp and t.kul = :kul and t.nd=:nd");
        query.setParameter("tp", "Дом");
        query.setParameter("kul", kul);
        query.setParameter("nd", nd);
        try {
            return (Eolink) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Получить объекты по типу, начиная с начальной точки иерархии
     * @param parent - начальная точка иерархии
     * @param tp - тип
     * @return
     */
    @Override
	public List<Eolink> getChildByTp(Eolink parent, String tp) {
    	List<Eolink> lst = parent.getChild().stream().filter(t -> t.getObjTp().getCd().equals(tp)).collect(Collectors.toList());
    	lst.addAll(parent.getChild().stream().flatMap(t -> getChildByTp(t, tp).stream()).collect(Collectors.toList()));
    	return lst;
	}


    /**
     * Получить дочерние объекты по родительскому объекту
     * @param parent - родительский объект
     * @return
     */
    /*public List<Eolink> getChildByEolink(Eolink parent) {
		Query query =em.createQuery("select t from Eolink t where t.parent.id = :id");
		query.setParameter("id", parent.getId());
		return query.getResultList();
    }*/

    /**
     * Получить объект по reu,kul,nd
     * @param reu - REU из Квартплаты - если не заполнен, не будет учитываться в запросе!
     * @param kul - KUL из Квартплаты
     * @param nd -  ND из Квартплаты
     * @param kw -  KW из Квартплаты
     * @param entry -  ENTRY из Квартплаты
     * @param tp -  тип объекта
     */
	@Override
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
			String kw, String entry, String tp) {
		Query query = null;
		switch (tp) {
		case "Дом":
			query =em.createQuery("select t from Eolink t where " +
                    "t.reu=decode(:reu,null, t.reu, :reu) and "//  не работает NVL - сделал DECODE
					+ "t.kul=:kul and t.nd=:nd and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("tp", tp);
			break;

		case "Квартира":
			query =em.createQuery("select t from Eolink t where " +
                    "t.reu=decode(:reu,null, t.reu, :reu) and "//  не работает NVL - сделал DECODE
					+ "t.kul=:kul and t.nd=:nd and t.kw=:kw and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("kw", kw);
			query.setParameter("tp", tp);
			break;

		case "Подъезд":
			query =em.createQuery("select t from Eolink t where " +
                    "t.reu=decode(:reu,null, t.reu, :reu) and "//  не работает NVL - сделал DECODE
					+ "t.kul=:kul and t.nd=:nd and t.entry=:entry and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("entry", entry);
			query.setParameter("tp", tp);
			break;

		case "Организация":
			query =em.createQuery("select t from Eolink t where " +
                    "t.reu=decode(:reu,null, t.reu, :reu) and "  //не работает NVL - сделал DECODE
                    + "t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("tp", tp);
			break;

		}

		try {
			return (Eolink) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		}
	}

	/**
	 * Получить все счетчики, по которым не сохранены показания в файл
	 */
	@Override
	public List<Eolink> getValsNotSaved() {
		Query query = em.createQuery("select t from Eolink t "
				+ "join AddrTp b with b.cd ='СчетчикФизический' "
				+ "join AddrTp d with d.cd ='Квартира' "
				+ "join Eolink e with e.objTp.id=d.id and t.parent.id=e.id and t.objTp.id=b.id "
				+ "join Par a with a.cd = 'ГИС ЖКХ.Счетчик.СтатусОбработкиПоказания' "
				+ "join EolinkPar p with p.eolink.id = t.id and p.par.id = a.id "
				+ "where (p.n1 = 1) ");
		return query.getResultList();
	}

	/**
	 * Получить Eolink УК по коду REU
	 */
	@Override
	public Eolink getEolinkUkByReu(String reu) {
		Query query = em.createQuery("select t from Eolink t "
				+ "where t.reu=:reu and t.objTp.cd='Организация'");
		query.setParameter("reu", reu);
		try {
			return (Eolink) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	/**
	 * Получить список УК в объектах Eolink
	 * @return
	 */
	@Override
	public List<Eolink> getUk() {
		Query query = em.createQuery("select e from Eolink e "
				+ "join e.objTp b "
				+ "where e.parent is not null and b.cd ='Организация' ");
		return query.getResultList();
	}

	/**
	 * Получить список Домов в объектах Eolink
	 * @return
	 */
	@Override
	public List<Eolink> getHouse() {
		Query query = em.createQuery("select e from Eolink e "
				+ "join e.objTp b "
				+ "where e.parent is not null and b.cd ='Дом' ");
		return query.getResultList();
	}

	/**
	 * Получить все объекты, определенного типа, по которым
	 * НЕТ созданных заданий определенного типа действия
	 * @param eolTp - тип объекта
	 * @param actTp - тип действия
	 * @param parentCD - CD родительского задания
	 */
	@Override
	public List<Eolink> getEolinkByTpWoTaskTp(String eolTp, String actTp, String parentCD) {
		Query query = null;
		if (eolTp.equals("Организация")) {
			query = em.createQuery("select e from Eolink e "
					+ "join e.objTp b "
					+ "where e.parent is not null and b.cd =:eolTp and " // с родителем (т.е. только УК)
					+ "not exists (select t from TaskToTask t where t.child.eolink.id=e.id "
					+ "and t.child.act.cd = :actTp and t.parent.cd = :parentCD) ");
		} else if (eolTp.equals("Дом")) {
			query = em.createQuery("select e from Eolink e "
					+ "join e.objTp b "
					+ "where b.cd =:eolTp and e.guid is not null "
					+ "and not exists (select t from TaskToTask t where t.child.eolink.id=e.id "
					+ "and t.child.act.cd = :actTp and t.parent.cd = :parentCD) ");
		}
		query.setParameter("eolTp", eolTp);
		query.setParameter("actTp", actTp);
		query.setParameter("parentCD", parentCD);
		return query.getResultList();
	}

	/**
	 * Получить объект по GUID
	 * @param guid - GUID
	 */
	@Override
	//@Cacheable(cacheNames="EolinkDAOImpl.getEolinkByGuid", key="{#guid }", unless = "#result == null")
	//  note Нельзя кэшировать! приводит к тому что не перечитываются изменения сделанные в Bexs
	public Eolink getEolinkByGuid(String guid) {
		//log.info("GUID={}", guid);
		javax.persistence.Query query =em.createQuery("select t from com.dic.bill.model.exs.Eolink t where t.guid = :guid");
		query.setParameter("guid", guid);
		try {
			return (Eolink) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
