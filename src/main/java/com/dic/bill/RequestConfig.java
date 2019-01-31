package com.dic.bill;

import com.dic.bill.model.scott.House;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Vvod;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//import com.dic.bill.model.scott.SessionDirect;

/**
 * Конфигуратор запроса
 *
 * @author Lev
 */
@Getter
@Setter
public class RequestConfig {

    // Id запроса
    int rqn;
    // тип выполнения 0-начисление, 1-задолженность и пеня, 2 - распределение объемов по вводу
    int tp = 0;
    // уровень отладки
    int debugLvl = 0;
    // дата на которую сформировать
    Date genDt = null;
    // выполнять многопоточно
    boolean isMultiThreads = false;

    // объекты формирования:
    // дом
    House house = null;
    // ввод
    Vvod vvod = null;
    // квартира
    Ko ko = null;

    // получить наименование типа выполнения
    public String getTpName() {
        switch (this.tp) {
            case 0:
                return "Начисление";
            case 1:
                return "Задолженность и пеня";
            case 2:
                return "Распределение объемов";
        }
        return null;
    }

    public static final class RequestConfigBuilder {
        // Id запроса
        int rqn;
        // тип выполнения 0-начисление, 1-задолженность и пеня, 2 - распределение объемов по вводу
        int tp = 0;
        // уровень отладки
        int debugLvl = 0;
        // дата на которую сформировать
        Date genDt = null;
        // выполнять многопоточно
        boolean isMultiThreads = false;
        // объекты формирования:
        // дом
        House house = null;
        // ввод
        Vvod vvod = null;
        // квартира
        Ko ko = null;

        private RequestConfigBuilder() {
        }

        public static RequestConfigBuilder aRequestConfig() {
            return new RequestConfigBuilder();
        }

        public RequestConfigBuilder withRqn(int rqn) {
            this.rqn = rqn;
            return this;
        }

        public RequestConfigBuilder withTp(int tp) {
            this.tp = tp;
            return this;
        }

        public RequestConfigBuilder withDebugLvl(int debugLvl) {
            this.debugLvl = debugLvl;
            return this;
        }

        public RequestConfigBuilder withGenDt(Date genDt) {
            this.genDt = genDt;
            return this;
        }

        public RequestConfigBuilder withIsMultiThreads(boolean isMultiThreads) {
            this.isMultiThreads = isMultiThreads;
            return this;
        }

        public RequestConfigBuilder withHouse(House house) {
            this.house = house;
            return this;
        }

        public RequestConfigBuilder withVvod(Vvod vvod) {
            this.vvod = vvod;
            return this;
        }

        public RequestConfigBuilder withKo(Ko ko) {
            this.ko = ko;
            return this;
        }

        public RequestConfig build() {
            RequestConfig requestConfig = new RequestConfig();
            requestConfig.setRqn(rqn);
            requestConfig.setTp(tp);
            requestConfig.setDebugLvl(debugLvl);
            requestConfig.setGenDt(genDt);
            requestConfig.setHouse(house);
            requestConfig.setVvod(vvod);
            requestConfig.setKo(ko);
            requestConfig.isMultiThreads = this.isMultiThreads;
            return requestConfig;
        }
    }
}
