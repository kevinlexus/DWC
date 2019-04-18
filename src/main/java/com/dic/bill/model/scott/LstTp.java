package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Тип элемента списка
 */
@Entity
@Immutable
@Getter @Setter
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "U_LISTTP", schema = "TEST201903")
public class LstTp implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    // CD
    @Column(name = "CD", updatable = false, nullable = false)
    private String cd;

    // наименование
    @Column(name = "NAME", updatable = false, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LstTp lstTp = (LstTp) o;
        return getId().equals(lstTp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

