package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO для хранения объемов по Квартире(локальное использование)
 */
@Getter
@Setter
@Slf4j
public class ChrgCountAmountLocal extends ChrgCountAmountBase {

    @PersistenceContext
    private EntityManager em;

    // с полной детализацией по услуге usl.cd="х.в. для гвс", используется только услугой fk_calc_tp = 47
    private List<UslPriceVolKart> lstUslPriceVolKartLinked = new ArrayList<>(10);

    // сгруппированное до дат, для записи реультата начисления в C_CHARGE
    private List<UslPriceVolKartDt> lstUslPriceVolKartDt = new ArrayList<>(10);

    /**
     * сгруппировать объемы для распределения по вводам
     *
     * @param u - объект объема
     */
    public void groupUslVol(UslPriceVolKart u) {
        // детализированный объем
        BigDecimal volDet = u.vol.add(u.volOverSoc);
        u.vol = u.vol.setScale(5, BigDecimal.ROUND_HALF_UP);
        u.volOverSoc = u.volOverSoc.setScale(5, BigDecimal.ROUND_HALF_UP);
        // округленный объем
        BigDecimal vol = u.vol.add(u.volOverSoc);

        // Сгруппировать объемы по базовым параметрам, по лиц.счетам для распределения по вводам
        UslVolKartGrp prevUslVolKartGrp = getLstUslVolKartGrp().stream().filter(t -> t.kart.equals(u.kart)
                && t.usl.equals(u.usl)
        ).findFirst().orElse(null);
        if (prevUslVolKartGrp == null) {
            // добавить новый элемент
            UslVolKartGrp uslVolKartGrp = new UslVolKartGrp();
            uslVolKartGrp.kart = u.kart;
            // жилое помещение (берётся по Основному лиц.счету)
            uslVolKartGrp.isResidental = u.isResidental;
            uslVolKartGrp.usl = u.usl;
            uslVolKartGrp.vol = vol;
            uslVolKartGrp.volDet = volDet;

            uslVolKartGrp.area = u.area.add(u.areaOverSoc);
            uslVolKartGrp.kpr = u.kpr;
            if (u.isMeter) {
                // если хоть один раз был в периоде счетчик - поставить отметку
                uslVolKartGrp.isExistMeterCurrPeriod = true;
            }
            if (!u.isEmpty) {
                // если хоть один раз были в периоде зарегистрированы проживающие - поставить отметку
                uslVolKartGrp.isExistPersCurrPeriod = true;
            }
            getLstUslVolKartGrp().add(uslVolKartGrp);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKartGrp.vol = prevUslVolKartGrp.vol.add(vol);
            prevUslVolKartGrp.volDet = prevUslVolKartGrp.volDet.add(volDet);
            prevUslVolKartGrp.area = prevUslVolKartGrp.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKartGrp.kpr = prevUslVolKartGrp.kpr.add(u.kpr);
        }

        // если услуга usl.cd="х.в. для гвс", то сохранить для услуг типа Тепл.энергия для нагрева ХВС (Кис.)
        if (u.usl.getCd() != null && u.usl.getCd().equals("х.в. для гвс")) {
            lstUslPriceVolKartLinked.add(u);
        }
        // Сгруппировать объемы по лиц.счетам для распределения по вводам
        UslVolKart prevUslVolKart = getLstUslVolKart().stream().filter(t -> t.kart.equals(u.kart)
                && t.usl.equals(u.usl) && t.isMeter == u.isMeter
                && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
        ).findFirst().orElse(null);
        if (prevUslVolKart == null) {
            // добавить новый элемент
            UslVolKart uslVolKart = new UslVolKart();
            uslVolKart.kart = u.kart;
            uslVolKart.usl = u.usl;
            uslVolKart.isResidental = u.isResidental;
            uslVolKart.isMeter = u.isMeter;
            uslVolKart.isEmpty = u.isEmpty;
            uslVolKart.vol = vol;
            uslVolKart.area = u.area.add(u.areaOverSoc);
            uslVolKart.kpr = u.kpr;
            getLstUslVolKart().add(uslVolKart);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKart.vol = prevUslVolKart.vol.add(vol);
            prevUslVolKart.area = prevUslVolKart.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKart.kpr = prevUslVolKart.kpr.add(u.kpr);
        }

        // Сгруппировать по вводу
        UslVolVvod prevUslVolVvod = getLstUslVolVvod().stream().filter(t ->
                t.usl.equals(u.usl) && t.isMeter == u.isMeter
                        && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
        ).findFirst().orElse(null);
        if (prevUslVolVvod == null) {
            // добавить новый элемент
            UslVolVvod uslVolVvod = new UslVolVvod();
            uslVolVvod.isResidental = u.isResidental;
            uslVolVvod.isMeter = u.isMeter;
            uslVolVvod.isEmpty = u.isEmpty;
            uslVolVvod.usl = u.usl;
            uslVolVvod.vol = vol;
            uslVolVvod.area = u.area;
            getLstUslVolVvod().add(uslVolVvod);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolVvod.vol = prevUslVolVvod.vol.add(vol);
            prevUslVolVvod.area = prevUslVolVvod.area.add(u.area).add(u.areaOverSoc);
            prevUslVolVvod.kpr = prevUslVolVvod.kpr.add(u.kpr);
        }

        // Сгруппировать до дат, для записи реультата начисления в C_CHARGE
        Date prevDt = Utl.addDays(u.dt, -1);
        // искать по лиц.счету, предыдущей дате, основному ключу
        UslPriceVolKartDt prevUslPriceVolKart = lstUslPriceVolKartDt.stream().filter(t -> t.kart.equals(u.kart)
                && t.dtTo.equals(prevDt)
                && t.usl.equals(u.usl) &&
                t.org.equals(u.org) && t.isMeter == u.isMeter && t.isResidental == u.isResidental
                && t.isEmpty == u.isEmpty && t.socStdt.equals(u.socStdt)
                && t.price.equals(u.price) && t.priceOverSoc.equals(u.priceOverSoc)
                && t.priceEmpty.equals(u.priceEmpty)).findFirst().orElse(null);
        if (prevUslPriceVolKart == null) {
            // добавить новый элемент
            UslPriceVolKartDt uslPriceVolKartDt = new UslPriceVolKartDt();
            uslPriceVolKartDt.kart = u.kart;
            uslPriceVolKartDt.usl = u.usl;
            uslPriceVolKartDt.dtFrom = u.dt;
            uslPriceVolKartDt.dtTo = u.dt;
            uslPriceVolKartDt.isEmpty = u.isEmpty;
            uslPriceVolKartDt.isMeter = u.isMeter;
            uslPriceVolKartDt.isResidental = u.isResidental;

            uslPriceVolKartDt.vol = u.vol;
            uslPriceVolKartDt.volOverSoc = u.volOverSoc;

            uslPriceVolKartDt.area = u.area;
            uslPriceVolKartDt.areaOverSoc = u.areaOverSoc;

            uslPriceVolKartDt.kpr = u.kpr;
            uslPriceVolKartDt.kprOt = u.kprOt;
            uslPriceVolKartDt.kprWr = u.kprWr;

            uslPriceVolKartDt.org = u.org;
            uslPriceVolKartDt.socStdt = u.socStdt;

            uslPriceVolKartDt.price = u.price;
            uslPriceVolKartDt.priceEmpty = u.priceEmpty;
            uslPriceVolKartDt.priceOverSoc = u.priceOverSoc;

            lstUslPriceVolKartDt.add(uslPriceVolKartDt);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденный период
            prevUslPriceVolKart.area = prevUslPriceVolKart.area.add(u.area);
            prevUslPriceVolKart.areaOverSoc = prevUslPriceVolKart.areaOverSoc.add(u.areaOverSoc);

            prevUslPriceVolKart.vol = prevUslPriceVolKart.vol.add(u.vol);
            prevUslPriceVolKart.volOverSoc = prevUslPriceVolKart.volOverSoc.add(u.volOverSoc);

            prevUslPriceVolKart.kpr = prevUslPriceVolKart.kpr.add(u.kpr);
            prevUslPriceVolKart.kprWr = prevUslPriceVolKart.kprWr.add(u.kprWr);
            prevUslPriceVolKart.kprOt = prevUslPriceVolKart.kprOt.add(u.kprOt);

            // продлить дату окончания
            prevUslPriceVolKart.dtTo = u.dt;
        }

    }

    /**
     * Округлить объемы по всем услугам
     */
    public void roundVol() {
        List<Usl> lstUsl = getLstUslVolKartGrp().stream().map(t -> t.usl).distinct().collect(Collectors.toList());
        for (Usl usl : lstUsl) {
            List<UslVolKartGrp> lst = getLstUslVolKartGrp().stream()
                    .filter(t -> t.usl.equals(usl))
                    .collect(Collectors.toList());
            for (UslVolKartGrp d : lst) {
                BigDecimal diff = d.volDet.setScale(5, BigDecimal.ROUND_HALF_UP)
                        .subtract(d.vol);
                d.vol = d.vol.add(diff);
                if (diff.compareTo(BigDecimal.ZERO) != 0) {
                    getLstUslVolKart().stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    lstUslPriceVolKartLinked.stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    getLstUslVolVvod().stream()
                            .filter(t -> t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                }
            }
        }
    }

    /**
     * Распечатать объемы по лиц.счетам
     *
     * @param uslId - код услуги, если не заполнено, то все
     */
    public void printVolAmnt(String uslId) {
        log.info("");
        Usl usl = null;
        if (uslId != null) {
            usl = em.find(Usl.class, uslId);
        }

        if (getLstUslPriceVolKartDt().size() > 0) {
            log.info("****** ПРОВЕРКА объема по klskId={}, lsk={}, usl={} *******",
                    getLstUslPriceVolKartDt().get(0).kart.getKoKw().getId(),
                    getLstUslPriceVolKartDt().get(0).kart.getLsk(),
                    uslId);
        }
        // отсортировать по lsk, usl, dtFrom
        List<UslPriceVolKartDt> lst =
                getLstUslPriceVolKartDt().stream()
                        .sorted(Comparator.comparing((UslPriceVolKartDt o1)->o1.getKart().getLsk())
                                .thenComparing((UslPriceVolKartDt o1)->o1.getUsl().getId())
                                .thenComparing((UslPriceVolKartDt o1)->o1.dtFrom)
                        )
                        .collect(Collectors.toList());
        for (UslPriceVolKartDt t : lst) {
            if (usl == null || t.usl.equals(usl)) {
                log.info("dt={}-{}, lsk={}, usl={}, ar={}, arOv={}, empt={}, met={}, res={}, " +
                                "org={}, prc={}, prcE={}, prcO={}, std={} " +
                                "kpr={}, kprO={}, kprW={}, vol={}, volO={}",
                        Utl.getStrFromDate(t.dtFrom), Utl.getStrFromDate(t.dtTo),
                        t.kart.getLsk(), t.usl.getId(),
                        t.area.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.areaOverSoc.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.isEmpty?"T":"F",
                        t.isMeter?"T":"F",
                        t.isResidental?"T":"F",
                        t.org.getId(), t.price, t.priceEmpty, t.priceOverSoc, t.socStdt,
                        t.kpr.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.kprOt.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.kprWr.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.vol.setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.volOverSoc.setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
                );
            }
        }
    }

}
