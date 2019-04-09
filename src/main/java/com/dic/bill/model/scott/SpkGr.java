package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Закон по льготе
 *
 * @version 1.00
 */
@Getter
@Setter
@Entity
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "SPK_GR", schema = "SCOTT")
public class SpkGr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // наименование
    @Column(name = "NAME", updatable = false, nullable = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpkGr spk = (SpkGr) o;
        return Objects.equals(getId(), spk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

