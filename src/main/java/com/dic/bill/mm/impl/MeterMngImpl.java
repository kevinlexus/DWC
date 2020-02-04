package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.CalcStore;
import com.dic.bill.dto.MeterData;
import com.dic.bill.dto.SumMeterVol;
import com.dic.bill.dto.UslMeterDateVol;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Meter;
import com.dic.bill.model.scott.Usl;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeterMngImpl implements MeterMng {

    @Autowired
    private MeterDAO meterDao;
    @PersistenceContext
    private EntityManager em;


    /**
     * Получить первый попавшийся актуальный счетчик по параметрам
     *
     * @param ko  - Ko объекта, где установлен счетчик
     * @param usl - код услуги
     * @param dt  - дата на которую получить
     * @return
     */

    @Override
    public Optional<Meter> getActualMeterByKoUsl(Ko ko, String usl, Date dt) {
        return meterDao.findActualByKoUsl(ko.getId(), usl, dt).stream().findFirst();
    }

    /**
     * Получить первый попавшийся актуальный счетчик по помещению
     *
     * @param ko  - Ko помещения., где установлен счетчик
     * @param usl - код услуги
     * @param dt  - дата на которую получить
     */

    @Override
    public Optional<Meter> getActualMeterByKo(Ko ko, String usl, Date dt) {
        // список уникальных фин.лиц. к которым привязаны счетчики (бред)
        List<Ko> lstKoFinLsk = ko.getKartByPremise().stream().map(Kart::getKoKw).distinct().collect(Collectors.toList());
        for (Ko koFinLsk : lstKoFinLsk) {
            // найти первый попавшийся счетчик по всем фин.лиц. в помещении
            return getActualMeterByKoUsl(koFinLsk, usl, dt);
        }
        // не найдено
        return Optional.empty();
    }

    /**
     * Получить объем в доле одного дня по счетчикам помещения
     *
     * @param lstMeterVol -  объемы по счетчикам
     * @param calcStore   - хранилище необходимых данных для расчета пени, начисления
     * @return - объем в доле 1 дня к периоду наличия рабочего счетчика
     */
    public List<UslMeterDateVol> getPartDayMeterVol(List<SumMeterVol> lstMeterVol, CalcStore calcStore) {
        Calendar c = Calendar.getInstance();
        // distinct список кодов услуг найденных счетчиков
        List<Usl> lstMeterUsl = lstMeterVol.stream()
                .map(t -> em.find(Usl.class, t.getUslId())).distinct().collect(Collectors.toList());
        List<UslMeterDateVol> lstMeterDateVol = new ArrayList<>();

        // перебрать услуги
        for (Usl usl : lstMeterUsl) {
            int workDays = 0;
            // сумма объема по всем счетчикам данной услуги
            BigDecimal vol = lstMeterVol.stream().filter(t -> t.getUslId().equals(usl.getId()))
                    .map(SumMeterVol::getVol)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            // перебрать дни текущего месяца
            BigDecimal diff = vol;
            UslMeterDateVol lastUslMeterDateVol = null;
            // подсчитать кол-во дней работы счетчика
            for (c.setTime(calcStore.getCurDt1()); !c.getTime().after(calcStore.getCurDt2());
                 c.add(Calendar.DATE, 1)) {
                Date curDt = c.getTime();
                // найти любой действующий счетчик, прибавить день
                SumMeterVol meterVol = lstMeterVol.stream().filter(t -> t.getUslId().equals(usl.getId()) &&
                        Utl.between(curDt, t.getDtFrom(), t.getDtTo())).findFirst().orElse(null);
                if (meterVol != null) {
                    // прибавить день
                    workDays++;
                }
            }

            for (c.setTime(calcStore.getCurDt1()); !c.getTime().after(calcStore.getCurDt2());
                 c.add(Calendar.DATE, 1)) {
                Date curDt = c.getTime();
                // найти любой действующий счетчик, прибавить день
                SumMeterVol meterVol = lstMeterVol.stream().filter(t -> t.getUslId().equals(usl.getId()) &&
                        Utl.between(curDt, t.getDtFrom(), t.getDtTo())).findFirst().orElse(null);
                if (meterVol != null) {
                    // доля объема на 1 рабочий день наличия счетчика
                    BigDecimal partDayVol = vol.divide(BigDecimal.valueOf(workDays), 5, RoundingMode.HALF_UP);
                    UslMeterDateVol uslMeterDateVol = new UslMeterDateVol(usl, curDt, partDayVol);
                    lastUslMeterDateVol = uslMeterDateVol;
                    lstMeterDateVol.add(uslMeterDateVol);
                    diff = diff.subtract(partDayVol);
                }
            }
            // округление
            if (lastUslMeterDateVol != null && diff.compareTo(BigDecimal.ZERO) != 0) {
                lastUslMeterDateVol.vol = lastUslMeterDateVol.vol.add(diff);
            }
        }
        return lstMeterDateVol;
    }

    /**
     * Узнать, работал ли хоть один счетчик в данном дне
     *
     * @param uslId - код услуги
     * @param dt    - дата на которую проверить
     * @return
     */
    @Override
    public boolean isExistAnyMeter(List<SumMeterVol> lstMeterVol, String uslId, Date dt) {
        SumMeterVol meterVol = lstMeterVol.stream().filter(t -> t.getUslId().equals(uslId) &&
                Utl.between(dt, t.getDtFrom(), t.getDtTo())).findFirst().orElse(null);
        return meterVol != null ? true : false;
    }

    /**
     * Проверить наличие в списке показания по счетчику
     *
     * @param lst  - список показаний
     * @param guid - GUID прибора учета
     * @param ts   - временная метка
     * @return
     */
    @Override
    public boolean getIsMeterDataExist(List<MeterData> lst, String guid, XMLGregorianCalendar ts) {
        Date dt = Utl.truncDateToSeconds(Utl.getDateFromXmlGregCal(ts));
        MeterData meterData = lst.stream().filter(t -> t.getGuid().equals(guid) && t.getTs().compareTo(dt) == 0)
                .findFirst().orElse(null);
        return meterData != null ? true : false;
    }


    /**
     * Проверить, является ли счетчик в Директ актуальным
     *
     * @param meter - счетчик
     * @param dt    - проверочная дата
     * @return
     */
    @Override
    public boolean getIsMeterActual(Meter meter, Date dt) {
        log.info("meter={}, dt={}", meter, dt);
        return Utl.between(dt, meter.getDt1(), meter.getDt2());
    }

    /**
     * Проверить, открыт ли счетчик в Директ принятия показаний от ГИС
     *
     * @param meter - счетчик
     * @return
     */
    @Override
    public boolean getIsMeterOpenForReceiveData(Meter meter) {
        if (meter.getGisConnTp() == null) {
            return false;
        } else if (meter.getGisConnTp().equals(1) || meter.getGisConnTp().equals(3)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Проверить, открыт ли счетчик в Директ для отправки показаний от ГИС
     *
     * @param meter - счетчик
     * @return
     */
    @Override
    public boolean getIsMeterOpenForSendData(Meter meter) {
        if (meter.getGisConnTp() == null) {
            return false;
        } else if (meter.getGisConnTp().equals(2) || meter.getGisConnTp().equals(3)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Проверить, возможно ли сохранить показания по счетчику в Директ
     *
     * @param meterEol - счетчик Eolink
     * @param dt       - проверочная дата
     */
    @Override
    public boolean getCanSaveDataMeter(Eolink meterEol, Date dt) {
        Ko ko = meterEol.getKoObj();
        Meter meter = ko.getMeter();
        return getIsMeterActual(meter, dt) && getIsMeterOpenForReceiveData(meter);
    }

    /**
     * ТЕСТОВЫЙ МЕТОД - ПРОВЕРЯЛ LockModeType.PESSIMISTIC_READ
     *
     * @param i
     * @param i1
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Meter> findMeter(int i, int i1) throws InterruptedException {
        List<Meter> lst = meterDao.findMeter(72802, 72805);
        lst.forEach(t -> {
            log.info("Meter.id={} n1={}", t.getId(), t.getN1());
        });
        Thread.sleep(15000);
        lst.forEach(t -> {
            log.info("Meter.id={} n1={}", t.getId(), t.getN1());
        });
        Thread.sleep(15000);
        lst.forEach(t -> {
            log.info("Meter.id={} n1={}", t.getId(), t.getN1());
        });
        Thread.sleep(15000);
        lst.forEach(t -> {
            log.info("Meter.id={} n1={}", t.getId(), t.getN1());
        });
        Thread.sleep(15000);
        lst.forEach(t -> {
            log.info("Meter.id={} n1={}", t.getId(), t.getN1());
        });
        return lst;
    }


}