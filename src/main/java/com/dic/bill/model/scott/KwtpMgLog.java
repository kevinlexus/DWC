package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Лог распределения платежа из C_KWTP_MG
 *
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KWTP_DAY_LOG", schema = "SCOTT")
@Getter
@Setter
public class KwtpMgLog implements java.io.Serializable {

    public KwtpMgLog() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KWTP_DAY_LOG")
    @SequenceGenerator(name = "SEQ_KWTP_DAY_LOG", sequenceName = "SCOTT.KWTP_DAY_LOG_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;

    // распределение платежа по периоду
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_C_KWTP_MG", referencedColumnName = "ID")
    private KwtpMg kwtpMg;

    // комментарий распределения
    @Column(name = "TEXT", updatable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KwtpMgLog kwtpMg = (KwtpMgLog) o;
        return Objects.equals(getId(), kwtpMg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

