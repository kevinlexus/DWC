package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Льготы
 *
 * @version 1.00
 */
@Getter
@Setter
@Entity
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "SPK", schema = "SCOTT")
public class Spk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // наименование
    @Column(name = "NAME", updatable = false, nullable = true)
    private String name;

    // CD
    @Column(name = "CD", updatable = false, nullable = false)
    private String cd;

    // закон по льготе
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GR_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    private SpkGr spkGr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spk spk = (Spk) o;
        return Objects.equals(getId(), spk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

