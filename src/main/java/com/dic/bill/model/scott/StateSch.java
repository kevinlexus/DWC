package com.dic.bill.model.scott;

import com.dic.bill.model.exs.Ulist;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;

/**
 * Состояние лицевого счета
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "C_STATES_SCH", schema = "SCOTT")
public class StateSch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = true)
    private Integer id;

    // статус (0-норматив, 1 - х.в.+г.в., 2-х.в., 3-г.в., 8-старый фонд, 9-закрытый фонд)
    // обычно в самой карточке лиц.счета, скрываю статусы 1,2,3 и меняю их визуально на "открытый"
    @Column(name = "FK_STATUS", updatable = false, nullable = false)
    private Integer fkStatus;

    // дата начала действия
    @Column(name = "DT1", updatable = false, nullable = false)
    private Date dt1;

    // дата окончания действия
    @Column(name = "DT2", updatable = false, nullable = false)
    private Date dt2;

    // причина закрытия
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLOSE_REASON", referencedColumnName = "ID", updatable = false)
    private Ulist reason;

    // активный ли лиц.счет в данной записи состояния?
    @Transient
    public boolean isActual(){
        return !Utl.in(fkStatus,8,9);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof StateSch))
            return false;

        StateSch other = (StateSch) o;

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

