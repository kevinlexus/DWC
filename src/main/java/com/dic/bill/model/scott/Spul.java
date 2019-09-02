package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Справочник улиц
 *
 * @version 1.00
 */
@Getter
@Setter
@Entity
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "SPUL", schema = "SCOTT")
public class Spul {

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
        Spul spul = (Spul) o;
        return Objects.equals(getId(), spul.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

