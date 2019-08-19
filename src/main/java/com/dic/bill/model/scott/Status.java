package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Статус лицевого счета
 */
@Getter
@Setter
@Entity
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "STATUS", schema = "SCOTT")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = true)
    private Integer id;

    // наименование
    @Column(name = "NAME", updatable = false, nullable = false)
    private String name;

    // CD
    @Column(name = "CD", updatable = false, nullable = false)
    private String cd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Status))
            return false;

        Status other = (Status) o;

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

