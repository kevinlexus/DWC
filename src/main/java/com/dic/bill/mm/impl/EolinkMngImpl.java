package com.dic.bill.mm.impl;

import com.dic.bill.dao.EolinkDAO;
import com.dic.bill.dao.EolinkDAO2;
import com.dic.bill.mm.EolinkMng;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Kart;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class EolinkMngImpl implements EolinkMng {

    @PersistenceContext
    private EntityManager em;
    private final EolinkDAO eolinkDAO;
    private final EolinkDAO2 eolinkDAO2;
    private final KartMng kartMng;

    public EolinkMngImpl(EolinkDAO eolinkDAO, EolinkDAO2 eolinkDAO2, KartMng kartMng) {
        this.eolinkDAO = eolinkDAO;
        this.eolinkDAO2 = eolinkDAO2;
        this.kartMng = kartMng;
    }

    /**
     * Получить Внешний объект по reu,kul,nd
     *
     * @param reu   - REU из Квартплаты
     * @param kul   - KUL из Квартплаты
     * @param nd    -  ND из Квартплаты
     * @param kw    -  KW из Квартплаты
     * @param entry -  ENTRY из Квартплаты
     * @param tp    -  тип объекта
     */
    @Override
    public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
                                        String kw, String entry, String tp) {
        return eolinkDAO.getEolinkByReuKulNdTp(reu, kul, nd, kw, entry, tp);
    }


    /* Поменять статус "актив" всех дочерних объектов по типу
     * @param - eolink - объект
     * @param - tp - тип объекта
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //rollbackFor=Exception.class - означает, что все исключения, выбрасываемые данным методом, должны приводить к откату транзакции.
    public void setChildActive(Eolink eolink, String tp, Integer status) {
        eolink.getChild().stream().forEach(t -> {
            t.setStatus(status);
        });
    }

    /**
     * Получить лицевые счета по дому (входящие в подъезды и  не входящие)
     *
     * @param eolHouseId - Eolink.Id объекта типа "Дом"
     * @param eolUkId    - Eolink.Id объекта типа "Организация"
     */
    @Override
    public List<Eolink> getLskEolByHouseEol(Integer eolHouseId, Integer eolUkId) {
        List<Eolink> lst = new ArrayList<>();
        lst.addAll(
                eolinkDAO2.getLskEolByHouseWithEntry(eolHouseId, eolUkId));
        lst.addAll(
                eolinkDAO2.getLskEolByHouseWOEntry(eolHouseId, eolUkId));
        return lst;
    }


    /**
     * Найти объект определенного типа, двигаясь по иерархии вверх
     *
     * @param eolink - объект, начиная от которого искать
     * @param cdTp   - тип объекта, который найти
     */
    @Override
    public Optional<Eolink> getEolinkByEolinkUpHierarchy(Eolink eolink, String cdTp) {
        if (eolink.getObjTp().getCd().equals(cdTp)) {
            return Optional.of(eolink);
        } else {
            if (eolink.getParent() != null) {
                // рекурсией искать далее
                return getEolinkByEolinkUpHierarchy(eolink.getParent(), cdTp);
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * Найти объекты определенного типа, двигаясь по иерархии вниз
     *
     * @param eolink - объект, начиная от которого искать
     * @param cdTp   - тип объекта, который найти
     */
    @Override
    public List<Eolink> getEolinkByEolinkDownHierarchy(Eolink eolink, String cdTp) {
        List<Eolink> lstFound = new ArrayList<>();

        if (eolink.getObjTp().getCd().equals(cdTp)) {
            lstFound.add(eolink);
        }
        for (Eolink child : eolink.getChild()) {
            List<Eolink> lstChild = getEolinkByEolinkDownHierarchy(child, cdTp);
            lstFound.addAll(lstChild);
        }
        return lstFound;
    }

    /**
     * Получить лицевые счета Kart по дому, отсутствующие в Eolink
     * (входящие в подъезды и  не входящие)
     *
     * @param eolHouseId - Eolink.Id объекта типа "Дом"
     * @param eolUkId    - Eolink.Id объекта типа "Организация"
     */
    @Override
    public List<Kart> getKartNotExistsInEolink(Integer eolHouseId, Integer eolUkId) {
        List<String> lst = new ArrayList<>();
        lst.addAll(
                eolinkDAO2.getKartActiveNotExistsInEolinkWithEntry(eolHouseId, eolUkId));
        lst.addAll(
                eolinkDAO2.getKartActiveNotExistsInEolinkWOEntry(eolHouseId, eolUkId));
        return lst.stream().map(t -> em.find(Kart.class, t)).collect(Collectors.toList());
    }


    /**
     * DTO с параметрами лиц.счета Eolink
     */
    @Getter @Setter
    public class EolinkParams {
        String houseGUID="";
        String un="";
    }

    /**
     * Получить параметры объекта Eolink по основному лиц счету
     * @param kart - лиц.счет
     * @return - DTO с параметрами
     */
    @Override
    public EolinkParams getEolinkParamsOfKartMain(Kart kart) {
        Kart kartMain = kartMng.getKartMain(kart);
        Optional<Eolink> eolink = kartMain.getEolink().stream().filter(t -> t.getUn() != null).findFirst();

        EolinkParams eolinkParams = new EolinkParams();
        // получить первый актуальный объект типа "Дом" по данному лиц.счету
        eolink.ifPresent(value -> getEolinkByEolinkUpHierarchy(value, "Дом").ifPresent(t -> {
            eolinkParams.setHouseGUID(t.getGuid());
            eolinkParams.setUn(value.getUn());
        }));
        if (!eolink.isPresent()) {
            if (kartMain.getHouse().getGuid()!=null) {
                eolinkParams.setHouseGUID(kartMain.getHouse().getGuid());
            }
        }
        return eolinkParams;
    }

}