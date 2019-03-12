package com.dic.bill.mm.impl;

import com.dic.bill.dao.KartDAO;
import com.dic.bill.mm.KartMng;
import com.dic.bill.model.scott.*;
import com.ric.cmn.excp.DifferentKlskBySingleAdress;
import com.ric.cmn.excp.EmptyId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KartMngImpl implements KartMng {

    @Autowired
    private KartDAO kartDao;

    @PersistenceContext
    private EntityManager em;

    /**
     * Получить Ko помещения по параметрам
     *
     * @param kul - код улицы
     * @param nd  - № дома
     * @param kw  - № помещения
     * @return
     */
    @Override
    public Ko getKoByKulNdKw(String kul, String nd, String kw) throws DifferentKlskBySingleAdress, EmptyId {
        List<Kart> lst = kartDao.findByKulNdKw(kul, nd, kw);
        Ko ko = null;
        for (Kart kart : lst) {
            if (kart.getKoKw().getId() == null) {
                throw new EmptyId("ОШИБКА! Обнаружен пустой KLSK_ID по лиц.счету: lsk" + kart.getLsk());
            } else if (ko == null) {
                ko = kart.getKoKw();
            } else if (!ko.equals(kart.getKoKw())) {
                throw new DifferentKlskBySingleAdress("ОШИБКА! Обнаружен разный KLSK_ID на один адрес: kul="
                        + kul + ", nd=" + nd + ", kw=" + kw);
            }
        }
        return ko;
    }

    /**
     * Получить все помещения по дому
     *
     * @param house - дом
     */
    @Override
    public List<Ko> getKoByHouse(House house) {
        return house.getKart().stream().map(t -> t.getKoKw()).distinct().collect(Collectors.toList());
    }

    /**
     * Получить помещения по вводу
     *
     * @param vvod - ввод
     */
    @Override
    public List<Ko> getKoByVvod(Vvod vvod) {
        return vvod.getNabor().stream().map(t -> t.getKart().getKoKw()).distinct().collect(Collectors.toList());
    }

    /**
     * Получить кол-во проживающих в лиц.счете
     *
     * @param kart - лиц.счет
     * @param dt   - рассчитываемая дата
     */
    @Override
    public boolean getPersCountByDate(Kart kart, Date dt) {
        log.info("*** kart.lsk={}", kart.getLsk());
        for (KartPr kartPr : kart.getKartPr()) {
            log.info("*** kartPr.id={}, kartPr.fio={}", kartPr.getId(), kartPr.getFio());
            for (StatePr statePr : kartPr.getStatePr()) {
                log.info("*** statePr.id={}, statePr.dtFrom={}, statePr.dtTo={}",
                        statePr.getId(), statePr.getDtFrom(), statePr.getDtTo());
            }
        }
        return true;
    }

/*
    */

    /**
     * Получить основной лиц.счет (родительский)
     * обычно для счетов РСО, капремонта требуется основной лиц.счет,
     * для получения информации о кол-ве проживающих, прочих параметрах, привязанных к основному лиц.счету
     *
     * @param kart - текущий лиц.счет
     * @return - возвращает String lsk, потому что нельзя кэшировать в потоке managed entity
     */
    @Override
    @Cacheable(cacheNames="KartMng.getKartMainLsk", key="{#kart.getLsk()}")
    public String getKartMainLsk(Kart kart) {
        for (Kart t : kart.getKoKw().getKart()) {
            if (t.isActual()) {
                if (t.getTp().getCd().equals("LSK_TP_MAIN")) {
                    return t.getLsk();
                }
            }
        }
        // не найден основной лиц.счет, вернуть текущий
        return kart.getLsk();
    }


}