package com.dic.bill.model.scott;

import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
@Getter @Setter
public class BaseNabor implements Nabors {

    @Column(name = "KOEFF")
    private BigDecimal koeff;

    @Column(name = "NORM")
    private BigDecimal norm;

    // услуга
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USL", referencedColumnName = "USl", updatable = false, nullable = false)
    private Usl usl;

    // организация - поставщик услуги
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG", referencedColumnName = "ID", updatable = false, nullable = false)
    private Org org;



    /**
     * Получить статус действующей услуги
     *
     * @param isForVol true - для расчета объемов, false - для записи в c_charge начисления
     */
    public boolean isValid(boolean isForVol) {
        BigDecimal bdKoeff = Utl.nvl(this.getKoeff(), BigDecimal.ZERO);
        BigDecimal bdNorm = Utl.nvl(this.getNorm(), BigDecimal.ZERO);
        if (this.getUsl().getFkCalcTp() != null && this.getUsl().getFkCalcTp().equals(14)) {
            // отопление Гкал - отдельная проверка
            // контроль только по коэфф.
            return bdKoeff.compareTo(BigDecimal.ZERO) != 0;
        } else {
            switch (this.getUsl().getSptarn()) {
                case 0: {
                    // контроль только по коэфф.
                    if (bdKoeff.compareTo(BigDecimal.ZERO) != 0) {
                        return true;
                    }
                    break;
                }
                case 1: {
                    // контроль только по нормативу
                    if (bdNorm.compareTo(BigDecimal.ZERO) != 0) {
                        return true;
                    }
                    break;
                }
                case 2:
                case 3: {
                    // когда koeff-является коэфф. и когда norm-тоже является коэфф.
                    // контроль по коэфф.и нормативу (странно и 2 и 3 sptarn, - потом разобраться, почему так FIXME
                    if (bdKoeff.multiply(bdNorm).compareTo(BigDecimal.ZERO) != 0) {
                        return true;
                    }
                    break;
                }
                case 4: {
                    // для г.в. и х.в., чтобы объемы учлись для водоотведения, но не сохранились суммы в c_charge
                    if (isForVol) {
                        // расчет объемов
                        if (bdNorm.compareTo(BigDecimal.ZERO) != 0) {
                            return true;
                        }
                    } else {
                        // сохранение в c_charge
                        if (bdKoeff.multiply(bdNorm).compareTo(BigDecimal.ZERO) != 0) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

}
