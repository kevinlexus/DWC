package com.dic.bill.dto;

import com.dic.bill.model.scott.Stavr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

// запись пени
@Data
public class PenCurRec {
    // период долга
    private int mg;
    // кол-во дней расчета пени всего
    // int days = 0;
    // кол-во текущих дней расчета пени
    private int curDays = 1;
    // долг
    //BigDecimal deb;
    // долг для расчета пени
    private BigDecimal debForPen;
    // пеня
    private BigDecimal pen;
    // ставка рефинансирования
    private Stavr stavr;
    // дата начала
    private Date dt1;
    // дата окончания
    private Date dt2;

    public PenCurRec(int mg, BigDecimal debForPen, BigDecimal pen,
                     Stavr stavr, Date dt1, Date dt2) {
        this.mg = mg;
        this.debForPen = debForPen;
        this.pen = pen;
        this.stavr = stavr;
        this.dt1 = dt1;
        this.dt2 = dt2;
    }

    /**
     * сравнить запись
     *
     * @param mg        - период
     * @param debForPen - долг для расчета пени
     * @param stavr     - ставка реф.
     */
    public boolean compareWith(int mg, BigDecimal debForPen, Stavr stavr) {
        return this.mg == mg && this.debForPen.equals(debForPen)
                && this.stavr.equals(stavr);
    }

    /**
     * добавить в существующую запись о пене
     *
     * @param pen - сумма начисленной пени
     * @param dt  - дата начисления
     */
    public void addPenDay(BigDecimal pen, Date dt) {
        // добавить пеню
        this.pen = this.pen.add(pen);
        // расширить период
        this.dt2 = dt;
        // добавить день пени
        this.curDays++;
    }
}