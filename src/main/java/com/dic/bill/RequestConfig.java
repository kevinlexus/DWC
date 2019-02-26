package com.dic.bill;

import com.dic.bill.model.scott.*;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

//import com.dic.bill.model.scott.SessionDirect;

/**
 * Конфигуратор запроса
 *
 * @author Lev
 */
@Getter
@Setter
public class RequestConfig implements Cloneable {

    // Id запроса
    int rqn;
    // тип выполнения 0-начисление, 1-задолженность и пеня, 2 - распределение объемов по вводу
    int tp = 0;
    // уровень отладки
    int debugLvl = 0;
    // дата на которую сформировать
    Date genDt = null;

    // текущий период
    Date curDt1;
    Date curDt2;

    // выполнять многопоточно
    boolean isMultiThreads = false;

    // объекты формирования:
    // УК
    Org uk = null;
    // дом
    House house = null;
    // ввод
    Vvod vvod = null;
    // помещение
    Ko ko = null;
    // услуга
    Usl usl = null;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
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

    /**
     * Проверить параметры запроса
     *
     * @return - null -  нет ошибок, !null - описание ошибки
     */
    public String checkArguments() {
        if (this.tp == 1) {
            // задолженность и пеня, - проверить текущую дату
            if (genDt == null) {
                return "ERROR! некорректная дата расчета!";
            } else {
                // проверить, что дата в диапазоне текущего периода
                if (!Utl.between(genDt, curDt1, curDt2)) {
                    return "ERROR! дата не находится в текущем периоде genDt=" + genDt;
                }
            }
        }
        return null;
    }


}
