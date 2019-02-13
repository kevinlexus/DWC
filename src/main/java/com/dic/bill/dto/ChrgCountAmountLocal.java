package com.dic.bill.dto;

import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;
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

    // сгруппированное до дат, для подготовки записи результата начисления в C_CHARGE
    // (возможно в будущем, прям отсюда записывать в C_CHARGE - детально)
    private List<UslPriceVolKartDt> lstUslPriceVolKartDt = new ArrayList<>(10);

    // сгруппированное до фактических услуг, для записи в C_CHARGE
    private List<UslVolCharge> lstUslVolCharge = new ArrayList<>(10);

    /**
     * сгруппировать объемы для распределения по вводам
     *
     * @param u - объект объема
     */
    public void groupUslVol(UslPriceVolKart u) {
        // детализированный объем
        //BigDecimal volDet = u.vol.add(u.volOverSoc);
        //u.vol = u.vol.setScale(5, BigDecimal.ROUND_HALF_UP);
        //u.volOverSoc = u.volOverSoc.setScale(5, BigDecimal.ROUND_HALF_UP);
        // округленный объем
        BigDecimal vol = u.vol.add(u.volOverSoc);

/*        if (u.usl.getId().equals("053")) {
            log.info("ДОБАВЛЕНО: dt={}, empt={}, vol={}", u.dt, u.isEmpty, vol);
        }*/

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
            //uslVolKartGrp.volDet = volDet;

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
            //prevUslVolKartGrp.volDet = prevUslVolKartGrp.volDet.add(volDet);
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
            BigDecimal summSample = roundByLst(getLstUslVolKartGrp(), usl, null);
            roundByLst(getLstUslVolVvod(), usl, summSample);
            //roundByLst(getLstUslPriceVolKartLinked(), usl, summSample);
            roundByLst(getLstUslPriceVolKartDt(), usl, summSample);
        }
    }

    /**
     * Округлить по коллекциям
     * @param lstSrc - коллекция
     * @param usl - услуга
     * @param summSample - сумма для сравнения
     * @return - сумма для сравнения в будущих округлениях
     */
    private BigDecimal roundByLst(List<? extends UslVol> lstSrc, Usl usl, BigDecimal summSample) {

        List<UslVol> lst = lstSrc.stream()
                .filter(t -> t.usl.equals(usl))
                .collect(Collectors.toList());

        int round;
        if (usl.isCalcByArea()) {
            // до 2 знаков - услуги, рассчитываемые по площади
            round = 2;
        } else {
            // остальные услуги до 5 знаков
            round = 5;
        }
        if (summSample == null) {
            summSample = lst.stream().map(t -> t.vol).reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(round, BigDecimal.ROUND_HALF_UP);
        }
        //log.info("$$$$$$$ summSample={}", summSample);
        lst.forEach(t->t.vol = t.vol.setScale(round, BigDecimal.ROUND_HALF_UP));
        BigDecimal sumVol2 = lst.stream().map(t -> t.vol).reduce(BigDecimal.ZERO, BigDecimal::add);
        //log.info("$$$$$$$ sumVol2={}", sumVol2);
        BigDecimal diff = summSample.subtract(sumVol2);
        //log.info("$$$$$$$ diff={}", diff);
        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            lstSrc.stream()
                    .filter(t -> t.usl.equals(usl))
                    .reduce((first, second) -> second) // найти последний элемент
                    //.findFirst()
                    .ifPresent(t -> t.vol = t.vol.add(diff));
        }
        return summSample;
    }

    /**
     * Распечатать объемы по лиц.счетам
     *
     * @param uslId - код услуги, если не заполнено, то все
     * @param msg - сообщение
     */
    public void printVolAmnt(String uslId, String msg) {
        log.info("");
        log.info("****** ПРОВЕРКА объемов {}, по UslPriceVolKartDt:", msg);
        // отсортировать по lsk, usl, dtFrom
        List<UslPriceVolKartDt> lst =
                getLstUslPriceVolKartDt().stream()
                        .sorted(Comparator.comparing((UslPriceVolKartDt o1) -> o1.getKart().getLsk())
                                .thenComparing((UslPriceVolKartDt o1) -> o1.getUsl().getId())
                                .thenComparing((UslPriceVolKartDt o1) -> o1.dtFrom)
                        )
                        .collect(Collectors.toList());
        for (UslPriceVolKartDt t : lst) {
            if (uslId == null || t.usl.getId().equals(uslId)) {
                log.info("dt={}-{}, lsk={}, usl={}, ar={}, arOv={}, empt={}, met={}, res={}, " +
                                "org={}, prc={}, prcE={}, prcO={}, std={} " +
                                "kpr={}, kprO={}, kprW={}, vol={}, volO={}",
                        Utl.getStrFromDate(t.dtFrom), Utl.getStrFromDate(t.dtTo),
                        t.kart.getLsk(), t.usl.getId(),
                        t.area.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.areaOverSoc.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros(),
                        t.isEmpty ? "T" : "F",
                        t.isMeter ? "T" : "F",
                        t.isResidental ? "T" : "F",
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

    /**
     * Распечатать объемы по лиц.счетам для начисления
     *
     * @param uslId - код услуги, если не заполнено, то все
     */

    public void printVolAmntChrg(String uslId) {
        log.info("");
        log.info("****** ПРОВЕРКА объема UslPriceVolKartDt, для сохранения в C_CHARGE:");
        for (UslPriceVolKartDt u : getLstUslPriceVolKartDt()) {
            log.info("lsk={}, usl={}, vol={}, volOverSoc={} *******",
                    u.kart.getLsk(), u.usl.getId(), u.vol, u.volOverSoc);
        }
    }

    /**
     * сгруппировать и сохранить начисление
     * примечание: так как пока не реализовано хранение организации в C_CHARGE, не группировать по org. ред. 30.01.19
     */
    public void groupUslVolChrg() {
        for (UslPriceVolKartDt u : getLstUslPriceVolKartDt()) {
            Usl uslFact;
            BigDecimal priceFact;
            if (u.vol.compareTo(BigDecimal.ZERO) != 0) {
                if (u.usl.isCalcByArea()) {
                    // услуги типа Текущее содержание - не контролировать 0 зарег и свыше соц нормы
                    uslFact = u.usl;
                    priceFact = u.price;
                } else {
                    // прочие услуги
                    if (!u.isEmpty) {
                        // есть проживающие
                        uslFact = u.usl;
                        priceFact = u.price;
                    } else {
                        // нет проживающих
                        uslFact = u.usl.getFactUslEmpt();
                        priceFact = u.priceEmpty;
                    }
                }
                addUslVolChrg(u, uslFact, u.vol, u.area, priceFact);
            }

            // свыше соц.нормы
            // у услуг типа Текущее содержание - не должно быть объема в u.volOverSoc
            if (u.volOverSoc.compareTo(BigDecimal.ZERO) != 0) {
                addUslVolChrg(u, u.usl.getUslOverNorm(),
                        u.volOverSoc, u.areaOverSoc, u.priceOverSoc);
            }
        }
    }

    /**
     * добавить строку для записи в C_CHARGE, с группировкой
     *
     * @param u       - запись начисления
     * @param uslFact - фактическая услуга
     * @param vol     - объем
     * @param area    - площадь
     */
    private void addUslVolChrg(UslPriceVolKartDt u, Usl uslFact,
                               BigDecimal vol, BigDecimal area, BigDecimal price) {
        UslVolCharge prev = getLstUslVolCharge().stream()
                .filter(t -> t.kart.equals(u.kart) && t.usl.equals(uslFact)
                        && t.isMeter == u.isMeter) // price пока не контролирую, в этой версии должна быть постоянна на протыжении месяца
                .findFirst().orElse(null);
        if (prev != null) {
            // найдена запись с данным ключом
            prev.vol = prev.vol.add(vol);
            prev.area = prev.area.add(area);
            prev.kpr = prev.kpr.add(u.kpr);
            prev.kprWr = prev.kprWr.add(u.kprWr);
            prev.kprOt = prev.kprOt.add(u.kprOt);
        } else {
            // не найдена запись, создать новую
            UslVolCharge uslVolCharge = new UslVolCharge();
            uslVolCharge.kart = u.kart;
            uslVolCharge.usl = uslFact;
            uslVolCharge.isMeter = u.isMeter;
            uslVolCharge.vol = vol;
            uslVolCharge.price = price;
            uslVolCharge.area = area;
            uslVolCharge.kpr = u.kpr;
            uslVolCharge.kprWr = u.kprWr;
            uslVolCharge.kprOt = u.kprOt;
            getLstUslVolCharge().add(uslVolCharge);
        }
    }

    /**
     * Сохранить и округлить начисление в C_CHARGE
     *
     * @param ko - квартира
     */
    public void saveChargeAndRound(Ko ko) throws ErrorWhileChrg {
        // удалить информацию по текущему начислению, по квартире, только по type=0,1
        for (Kart kart : ko.getKart()) {
            kart.getCharge().removeIf(t->t.getType().equals(0) || t.getType().equals(1));
        }
        log.trace("Сохранено в C_CHARGE:");
        for (UslVolCharge u : getLstUslVolCharge()) {
            Charge charge = new Charge();
            charge.setType(1);
            charge.setUsl(u.usl);
            charge.setKart(u.kart);
            charge.setTestOpl(u.vol);
            BigDecimal area = u.area.setScale(2, BigDecimal.ROUND_HALF_UP);
            charge.setOpl(area);
            charge.setTestCena(u.price);
            charge.setIsSch(u.isMeter);
            BigDecimal summa = u.vol.multiply(u.price).setScale(2, BigDecimal.ROUND_HALF_UP);
            charge.setSumma(summa);
            u.kart.getCharge().add(charge);
            log.trace("lsk={}, usl={}, testOpl={}, opl={}, testCena={}, isSch={} summa={}",
                    u.kart.getLsk(), u.usl.getId(), u.vol, area, u.price, u.isMeter, summa);

        }

        // округлить для ГИС ЖКХ
        for (Kart kart : ko.getKart()) {
            // по услугам:
            // цена
            Map<Usl, BigDecimal> mapPrice = new HashMap<>();

            // итоговая сумма
            BigDecimal summAmnt = BigDecimal.ZERO;
            // итоговая цена
            BigDecimal priceAmnt = BigDecimal.ZERO;

            Charge firstCharge = null;

            // сохранить все цены, суммы и объемы по услугам
            // по услугам, подлежащим округлению (находящимся в справочнике SCOTT.USL_ROUND)
            // соответствующего REU
            log.trace("Округление для ГИС ЖКХ: lsk={}", kart.getLsk());

            List<Charge> lll = kart.getCharge().stream()
                    .filter(t -> t.getUsl().getUslRound().stream()
                            .anyMatch(d -> d.getReu().equals(kart.getUk().getReu())))
                    .sorted(Comparator.comparing(d -> d.getUsl().getId())) // сортировать по коду услуги
                    .collect(Collectors.toList());
            for (Charge t : lll) {
                log.info("charge: usl={}, summa={}", t.getUsl().getId(), t.getSumma());
            }

            for (Charge charge : kart.getCharge().stream()
                    .filter(t -> t.getUsl().getUslRound().stream()
                            .anyMatch(d -> d.getReu().equals(kart.getUk().getReu())))
                    .sorted(Comparator.comparing(d -> d.getUsl().getId())) // сортировать по коду услуги
                    .collect(Collectors.toList())
                    ) {
                // цена
                if (mapPrice.get(charge.getUsl()) == null) {
                    mapPrice.put(charge.getUsl(), charge.getTestCena());
                    log.trace("usl={}, price={}", charge.getUsl().getId(), charge.getTestCena());
                    priceAmnt = priceAmnt.add(charge.getTestCena());
                }

                // сумма
                log.trace("summa={}", charge.getSumma());
                summAmnt = summAmnt.add(charge.getSumma());
                if (firstCharge == null)
                    firstCharge = charge;
            }
            // округлить на первую услугу по порядку кода USL
            if (firstCharge != null) {
                BigDecimal summCheck = kart.getOpl().multiply(priceAmnt).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal diff = summCheck.subtract(summAmnt);
                log.trace("Итого сумма ={} рассчит={}", summAmnt, summCheck);
                if (diff.abs().compareTo(new BigDecimal("0.05")) < 0) {
                    log.trace("Применено округление для ГИС ЖКХ, по lsk={}, usl={}, diff={}",
                            kart.getLsk(), firstCharge.getUsl().getId(), diff);
                    firstCharge.setSumma(firstCharge.getSumma().add(diff));
                } else {
                    throw new ErrorWhileChrg("ОШИБКА! Округление для ГИС ЖКХ превысило 0.05 по lsk=" + kart.getLsk());
                }
            }

        }


    }
}
