package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Наборов услуг по организациям в лицевом счете
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NABOR", schema = "SCOTT")
@DynamicUpdate
@Getter
@Setter
public class Nabor extends BaseNabor implements java.io.Serializable {

    public Nabor() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Nabor_id")
    @SequenceGenerator(name = "SEQ_Nabor_id", sequenceName = "scott.nabor_id", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false, nullable = false)
    private Kart kart;

    // ввод
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_VVOD", referencedColumnName = "ID", updatable = false)
    private Vvod vvod;

    // распределение объема (например по отоплению гкал.)
    @Column(name = "VOL")
    private BigDecimal vol;

    // распределение объема (например по Х.В.ОДН - старому)
    @Column(name = "VOL_ADD")
    private BigDecimal volAdd;

    // лимит объема по услуге (Используется при начислении по ОДН)
    @Column(name = "LIMIT")
    private BigDecimal limit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nabor))
            return false;

        Nabor other = (Nabor) o;

        if (id == other.getId()) return true;
        if (id == null) return false;

        // equivalence by id
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

}

