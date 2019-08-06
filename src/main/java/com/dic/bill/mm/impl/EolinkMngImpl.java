package com.dic.bill.mm.impl;

import com.dic.bill.dao.EolinkDAO;
import com.dic.bill.dao.EolinkDAO2;
import com.dic.bill.mm.EolinkMng;
import com.dic.bill.model.exs.Eolink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EolinkMngImpl implements EolinkMng {

    @Autowired
    private EolinkDAO eolinkDAO;
    @Autowired
    private EolinkDAO2 eolinkDAO2;

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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //rollbackFor=Exception.class - означает, что все исключения, выбрасываемые данным методом, должны приводить к откату транзакции.
    public void setChildActive(Eolink eolink, String tp, Integer status) {
        eolink.getChild().stream().forEach(t -> {
            t.setStatus(status);
        });
    }

    /**
     * Получить лицевые счета по дому (входящие в подъезды и  не входящие)
     *
     * @param eolink - объект типа Дом
     */
    @Override
    public List<Eolink> getLskEolByHouseEol(Eolink eolink, Integer ukId) {
        List<Eolink> lst = new ArrayList<>();
        lst.addAll(
                eolinkDAO2.getLskEolByHouseWithEntry(eolink.getId(), ukId));
        lst.addAll(
                eolinkDAO2.getLskEolByHouseWOEntry(eolink.getId(), ukId));
        return lst;
    }

}