package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Подготовительная информация для расчета начисления
 *
 * @author lev
 */
@Entity
@Table(name = "C_CHARGE_PREP", schema = "TEST")
@Getter
@Setter
public class ChargePrep {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ChargePrep_id")
    @SequenceGenerator(name = "SEQ_ChargePrep_id", sequenceName = "scott.c_charge_prep_id", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK", updatable = false, nullable = false, insertable = true)
    private Kart kart;

    // Тип записи (0-предварительный подсчет,1 - сгруппированный до ключевых структур,
    // 2 - кол-во прож.для опред.соцнормы 3 - итог объёма, проживающих,
    // 4 - корректировки ОДН, 5 - детально, но без корректировок ОДН, 6 - итог объёма, проживающих без коррект. ОДН)
    // 7 - просто, наличие счетчика 8-детализация льготы, 9 - наличие льготы (используется совместно с dt1,dt2)
    // NOTE ИСПОЛЬЗОВАТЬ В JAVA только 4! Решил использовать только данный тип, так как остальные - не нужны в
    // NOTE Java - начислении ред.16.01.2019
    @Column(name = "TP", updatable = false, nullable = false, insertable = true)
    private Integer tp;

    // услуга
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USL", referencedColumnName = "USl", updatable = false, nullable = false, insertable = true)
    private Usl usl;

    // объем
    @Column(name = "VOL", updatable = false, nullable = false)
    private BigDecimal vol;

    // наличие счетчика
    @Type(type= "org.hibernate.type.NumericBooleanType")
    @Column(name = "SCH", updatable = false, nullable = false)
    private Boolean isExistMeter;

    // льгота
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SPK", referencedColumnName = "ID", updatable = false)
    private Spk spk;

}

