package com.dic.bill.dto;

import com.dic.bill.dto.UslVolKart;
import com.dic.bill.dto.UslVolKartGrp;
import com.dic.bill.dto.UslVolVvod;
import com.dic.bill.dto.ChrgCountAmountLocal;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

/**
 * DTO для хранения объемов по Вводу (при распределении объемов)
 */
@Getter
@Setter
@Slf4j
public class ChrgCountAmount extends ChrgCountAmountBase {

    /**
     * Добавить локальные объемы по квартире в объем по вводу
     * @param chrgCountAmountLocal - хранилище локальных объемов по квартире
     */
    public void append(ChrgCountAmountLocal chrgCountAmountLocal) {
        this.getLstUslVolKart().addAll(chrgCountAmountLocal.getLstUslVolKart());
        this.getLstUslVolKartGrp().addAll(chrgCountAmountLocal.getLstUslVolKartGrp());

        for (UslVolVvod u : chrgCountAmountLocal.getLstUslVolVvod()) {

            UslVolVvod prevUslVolVvod = this.getLstUslVolVvod().stream().filter(
                    t->t.usl.equals(u.usl) && t.isMeter == u.isMeter
                    && t.isEmpty == u.isEmpty && t.isResidental == u.isResidental
            ).findFirst().orElse(null);

            if (prevUslVolVvod == null) {
                // добавить новый элемент
                UslVolVvod uslVolVvod = new UslVolVvod();
                uslVolVvod.isResidental = u.isResidental;
                uslVolVvod.isMeter = u.isMeter;
                uslVolVvod.isEmpty = u.isEmpty;
                uslVolVvod.usl = u.usl;
                uslVolVvod.vol = u.vol;
                uslVolVvod.area = u.area;
                getLstUslVolVvod().add(uslVolVvod);
            } else {
                // такой же по ключевым параметрам, добавить данные в найденную строку
                prevUslVolVvod.vol = prevUslVolVvod.vol.add(u.vol);
                prevUslVolVvod.area = prevUslVolVvod.area.add(u.area);
                prevUslVolVvod.kpr = prevUslVolVvod.kpr.add(u.kpr);
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

                    BigDecimal area1 = getLstUslVolKart()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.area
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);

                    log.info("****** итого по lstUslVolKart vol={}, area={}",
                            new DecimalFormat("#0.########").format(vol1),
                            new DecimalFormat("#0.########").format(area1)
                    );

/*
                    BigDecimal vol2 = getLstUslPriceVolKartLinked()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);
                    log.info("****** общий объем по lstUslPriceVolKart по услуге usl.cd=\"х.в. для гвс\" ={} ******",
                            new DecimalFormat("#0.########").format(vol2)
                    );
*/

                    BigDecimal vol3 = getLstUslVolKartGrp()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);
                    BigDecimal area3 = getLstUslVolKartGrp()
                            .stream()
                            .filter(t -> (lsk == null || t.kart.getLsk().equals(lsk)) && t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);

                    log.info("****** итого по lstUslVolKartGrp vol={}, area={}",
                            new DecimalFormat("#0.########").format(vol3),
                            new DecimalFormat("#0.########").format(area3)
                    );

                    BigDecimal vol4 = getLstUslVolVvod()
                            .stream()
                            .filter(t -> t.usl.equals(d))
                            .map(t -> t.vol
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);
                    BigDecimal area4 = getLstUslVolVvod()
                            .stream()
                            .filter(t -> t.usl.equals(d))
                            .map(t -> t.area
                            ).reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(8, BigDecimal.ROUND_HALF_UP);

                    log.info("****** итого по lstUslVolKartVvod vol={}, area={}",
                            new DecimalFormat("#0.########").format(vol4),
                            new DecimalFormat("#0.########").format(area4)
                    );

                    assertTrue(vol1.compareTo(vol4) == 0 &&
                            vol3.compareTo(vol4) == 0
                    );
                });
    }


}
