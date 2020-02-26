package com.dic.bill.mm.impl;

import com.dic.bill.dao.MeterDAO;
import com.dic.bill.dto.CalcStore;
import com.dic.bill.dto.MeterData;
import com.dic.bill.dto.SumMeterVol;
import com.dic.bill.dto.UslMeterDateVol;
import com.dic.bill.mm.MeterMng;
import com.dic.bill.model.exs.Eolink;
import com.dic.bill.model.scott.*;
import com.ric.cmn.CommonErrs;
import com.ric.cmn.MeterValConsts;
import com.ric.cmn.Utl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedWriter;
import java.io.IOException;
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
     * Получить показания по счетчику со статусом, по периоду
     *
     * @param meter  - счетчик
     * @param status - статус
     * @param period - период
     */
    @Override
    public List<ObjPar> getValuesByMeter(Meter meter, int status, String period) {
        return meter.getObjPar().stream()
                .filter(t -> t.getLst().getCd().equals("ins_sch") && t.getMg().equals(period)
                        && t.getStatus().equals(status))
                .sorted(Comparator.comparing(ObjPar::getId).reversed())
                .collect(Collectors.toList());
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

    /**
     * Отправить показания по счетчику в биллинг
     *
     * @param writer - объект записи
     * @param lsk    - лиц.счет
     * @param strUsl - код услуги
     * @param value  - показание
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int sendMeterVal(BufferedWriter writer, String lsk, String strUsl, String value, String period, Integer userId) throws IOException {
        if (strUsl != null && strUsl.length() >= 3) {
            String codeUsl = strUsl.substring(0, 3);
            if (value != null && value.length() > 0) {
                Double val = Double.valueOf(value);
                log.trace("Отправка по лиц={}, услуге usl={}, показание ={}", lsk, codeUsl, val);

                StoredProcedureQuery query = em
                        .createStoredProcedureQuery(
                                "scott.p_meter.ins_data_meter")
                        .registerStoredProcedureParameter(
                                "p_lsk",
                                String.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_usl",
                                String.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_n1",
                                Double.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_ts",
                                Date.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_period",
                                String.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_status",
                                Integer.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_user",
                                Integer.class,
                                ParameterMode.IN
                        )
                        .registerStoredProcedureParameter(
                                "p_ret",
                                Integer.class,
                                ParameterMode.OUT
                        )
                        .setParameter("p_lsk", lsk)
                        .setParameter("p_usl", codeUsl)
                        .setParameter("p_n1", val)
                        .setParameter("p_ts", new Date())
                        .setParameter("p_period", period)
                        .setParameter("p_status", MeterValConsts.INSERT_FOR_LOAD_TO_GIS)
                        .setParameter("p_user", userId);
                Integer ret=-1;
                try {
                    query.execute();

                    ret = (Integer) query
                            .getOutputParameterValue("p_ret");

                } finally {
                    query.unwrap(ProcedureOutputs.class).release();
                }

/*
                CallableStatement callableStatement = ReflectionUtils
                        .getFieldValue(
                                query.unwrap(ProcedureOutputs.class),
                                "scott.p_meter.ins_data_meter"
                        );
*/
/*                StoredProcedureQuery qr = em.createStoredProcedureQuery("scott.p_meter.ins_data_meter");
                qr.registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN); // p_lsk
                qr.registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN); // p_usl
                qr.registerStoredProcedureParameter(3, Double.class,
                        ParameterMode.IN); // p_n1
                qr.registerStoredProcedureParameter(4, Date.class,
                        ParameterMode.IN); // p_ts
                qr.registerStoredProcedureParameter(5, String.class,
                        ParameterMode.IN); // p_period
                qr.registerStoredProcedureParameter(6, Integer.class,
                        ParameterMode.IN); // p_status
                qr.registerStoredProcedureParameter(7, Integer.class,
                        ParameterMode.IN); // p_user
                qr.registerStoredProcedureParameter(8, Integer.class,
                        ParameterMode.OUT);// p_ret
                qr.setParameter(1, lsk);
                qr.setParameter(2, codeUsl);
                qr.setParameter(3, val);
                qr.setParameter(4, new Date());
                qr.setParameter(5, period);
                qr.setParameter(6, MeterValConsts.INSERT_FOR_LOAD_TO_GIS);
                qr.setParameter(7, userId);
                qr.executeUpdate();
                Integer ret = (Integer) qr.getOutputParameterValue(8);

 */
                log.trace("Результат исполнения scott.p_meter.ins_data_meter={}", ret);
                String mess = " По лиц p_lsk=%s, услуге p_usl=%s, показание p_n1=%f, период p_period=%s";
                if (ret.equals(-1)) {
                    String str = String.format(CommonErrs.ERR_MSG_METER_VAL_OTHERS +
                            mess, lsk, codeUsl, val, period);
                    writer.write(str + "\r\n");
                } else if (ret.equals(1)) {
                    String str = String.format(CommonErrs.ERR_MSG_METER_VAL_ZERO +
                            mess, lsk, codeUsl, val, period);
                    writer.write(str + "\r\n");
                } else if (ret.equals(3)) {
                    // показания меньше или равны существующим - не считать за ошибку
                    //String str = String.format(CommonErrs.ERR_MSG_METER_VAL_LESS +
                    //        mess, lsk, codeUsl, val, configApp.getPeriod());
                    //writer.write(str + "\r\n");
                } else if (ret.equals(4)) {
                    String str = String.format(CommonErrs.ERR_MSG_METER_NOT_FOUND +
                            mess, lsk, codeUsl, val, period);
                    writer.write(str + "\r\n");
                } else if (ret.equals(5)) {
                    String str = String.format(CommonErrs.ERR_MSG_METER_VAL_TOO_BIG +
                            mess, lsk, codeUsl, val, period);
                    writer.write(str + "\r\n");
                } else {
                    log.trace(CommonErrs.ERR_MSG_METER_VAL_SUCCESS + mess,
                            lsk, codeUsl, val, period);
                }
                return ret;
            }
        }
        return 0; // нет данных для отправки
    }


}