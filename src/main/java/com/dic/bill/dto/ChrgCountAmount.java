package com.dic.bill.dto;

import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;

/**
 * DTO для хранения параметров для расчета начисления по дому
 */
@Getter
@Setter
@Slf4j
public class ChrgCountAmount {


    // с полной детализацией
    private List<UslPriceVolKart> lstUslPriceVolKart = new ArrayList<>(10);

    // сгруппированные до лиц.счетов, объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolKart> lstUslVolKart = new ArrayList<>(10);

    // сгруппированные по базовым параметрам, до лиц.счетов, объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolKartGrp> lstUslVolKartGrp = new ArrayList<>(10);

    // сгруппированные по вводу объемы для расчета услуг типа ОДН, Отопление Гкал по вводу
    private List<UslVolVvod> lstUslVolVvod = new ArrayList<>(10);


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

        // сгруппировать объемы по базовым параметрам, по лиц.счетам для распределения по вводам
        UslVolKartGrp prevUslVolKartGrp = lstUslVolKartGrp.stream().filter(t -> t.kart.equals(u.kart)
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
            lstUslVolKartGrp.add(uslVolKartGrp);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKartGrp.vol = prevUslVolKartGrp.vol.add(vol);
            prevUslVolKartGrp.volDet = prevUslVolKartGrp.volDet.add(volDet);
            prevUslVolKartGrp.area = prevUslVolKartGrp.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKartGrp.kpr = prevUslVolKartGrp.kpr.add(u.kpr);
        }

        // сохранить для услуг типа Тепл.энергия для нагрева ХВС (Кис.)
        lstUslPriceVolKart.add(u);
        // сгруппировать объемы по лиц.счетам для распределения по вводам
        UslVolKart prevUslVolKart = lstUslVolKart.stream().filter(t -> t.kart.equals(u.kart)
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
            lstUslVolKart.add(uslVolKart);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolKart.vol = prevUslVolKart.vol.add(vol);
            prevUslVolKart.area = prevUslVolKart.area.add(u.area).add(u.areaOverSoc);
            prevUslVolKart.kpr = prevUslVolKart.kpr.add(u.kpr);
        }

        // сгруппировать по вводу
        UslVolVvod prevUslVolVvod = lstUslVolVvod.stream().filter(t ->
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
            lstUslVolVvod.add(uslVolVvod);
        } else {
            // такой же по ключевым параметрам, добавить данные в найденную строку
            prevUslVolVvod.vol = prevUslVolVvod.vol.add(vol);
            prevUslVolVvod.area = prevUslVolVvod.area.add(u.area).add(u.areaOverSoc);
            prevUslVolVvod.kpr = prevUslVolVvod.kpr.add(u.kpr);
        }
    }

    /**
     * Округлить объемы по всем услугам
     */
    public void roundVol() {
        List<Usl> lstUsl = lstUslVolKartGrp.stream().map(t -> t.usl).distinct().collect(Collectors.toList());
        for (Usl usl : lstUsl) {
            List<UslVolKartGrp> lst = lstUslVolKartGrp.stream()
                    .filter(t -> t.usl.equals(usl))
                    .collect(Collectors.toList());
            for (UslVolKartGrp d : lst) {
                BigDecimal diff = d.volDet.setScale(5, BigDecimal.ROUND_HALF_UP)
                        .subtract(d.vol);
                d.vol = d.vol.add(diff);
                if (diff.compareTo(BigDecimal.ZERO) != 0) {
                    lstUslVolKart.stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    lstUslPriceVolKart.stream()
                            .filter(t -> t.kart.equals(d.kart) && t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                    lstUslVolVvod.stream()
                            .filter(t -> t.usl.equals(d.usl))
                            .findFirst().ifPresent(t -> t.vol = t.vol.add(diff));
                }
            }
        }
    }

    /**
     * Распечатать объемы по лиц.счетам
     *
     * @param lsk   - лиц.счет, если не заполнено, то все
     * @param uslId - код услуги, если не заполнено, то все
     */
    public void printVolAmnt(String lsk, String uslId) {
        log.info("");
        getLstUslVolKart().stream()
                .map(d -> d.usl)
                .filter(d -> uslId == null || d.getId().equals(uslId))
                .distinct()
                .forEach(d -> {
                    log.info("");

/*
                    log.info("****** ПРОВЕРКА объема по lsk={}, usl={} *******", lsk, d.getId());
                    for (UslPriceVolKart t : getLstUslPriceVolKart()) {
                        if ((lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d)) {
                            log.info("dt={}, lsk={}, area={}, kpr={}, empt={}, met={}, vol={}",
                                    Utl.getStrFromDate(t.dt),
                                    t.kart.getLsk(),
                                    t.area.setScale(4, BigDecimal.ROUND_HALF_UP),
                                    t.kpr.setScale(4, BigDecimal.ROUND_HALF_UP),
                                    t.isEmpty, t.isMeter,
                                    t.vol.setScale(8, BigDecimal.ROUND_HALF_UP)
                            );
                        }
                    }
*/

                    log.info("****** ПРОВЕРКА объема по lsk={}, usl={} *******", lsk, d.getId());
                    for (UslVolKart t : getLstUslVolKart()) {
                        if ((lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d)) {
                            log.info("lsk={}, area={}, kpr={}, empt={}, met={}, vol={}", t.kart.getLsk(),
                                    t.area.setScale(4, BigDecimal.ROUND_HALF_UP),
                                    t.kpr.setScale(4, BigDecimal.ROUND_HALF_UP),
                                    t.isEmpty, t.isMeter,
                                    t.vol.setScale(8, BigDecimal.ROUND_HALF_UP)
                            );
                        }
                    }


                    BigDecimal vol1 = getLstUslVolKart()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);

                    log.info("****** общий объем по lstUslVolKart={} ******",
                            new DecimalFormat("#0.########").format(vol1)
                    );

                    BigDecimal vol2 = getLstUslPriceVolKart()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);
                    log.info("****** общий объем по lstUslPriceVolKart={} ******",
                            new DecimalFormat("#0.########").format(vol2)
                    );

                    BigDecimal vol3 = getLstUslVolKartGrp()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);

                    log.info("****** общий объем по lstUslVolKartGrp={} ******",
                            new DecimalFormat("#0.########").format(vol3)
                    );

                    BigDecimal vol4 = getLstUslVolVvod()
                            .stream()
                            .filter(t -> t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);
                    log.info("****** общий объем по lstUslVolVvod={} ******",
                            new DecimalFormat("#0.########").format(vol4)
                    );

                    assertTrue(vol1.compareTo(vol2) == 0 &&
                            vol2.compareTo(vol3) == 0 &&
                            vol3.compareTo(vol4) == 0
                    );
                });
/*
            for (Nabor nabor : vvod.getNabor()) {
				if (nabor.getUsl().getId().equals("011")) {
					for (Charge t : nabor.getKart().getCharge()) {
						log.info("ОДН:Charge lsk={}, usl={}, type={}, testOpl={}", t.getKart().getLsk(), t.getUsl().getId(),
								t.getType(), t.getTestOpl());
						amntVolChrg = amntVolChrg.add(t.getTestOpl());
					}
				}
			}
			for (Nabor nabor : vvod.getNabor()) {
				if (nabor.getUsl().getId().equals("011")) {
					for (ChargePrep t : nabor.getKart().getChargePrep()) {
						log.info("ОДН:ChargePrep lsk={}, usl={}, sch={}, tp={}, vol={}",
								t.getKart().getLsk(), t.getUsl().getId(), t.isExistMeter(),
								t.getTp(), t.getVol());
						amntVolChrgPrep = amntVolChrgPrep.add(t.getVol());
					}
				}
			}
			log.info("Итоговое распределение ОДН charge={}, chargePrep={}", amntVolChrg, amntVolChrgPrep);
*/

    }

}
