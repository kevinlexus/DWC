package com.dic.bill.model.scott;

import com.dic.bill.model.exs.Eolink;
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
 * Справочник всех объектов Klsk Objects - KO
 *
 * @author Lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "K_LSK", schema = "SCOTT")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectNeverClearCache", // никогда не обновляется извне данная таблица
        usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter
@Setter
public class Ko implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Ko_id")
    @SequenceGenerator(name = "SEQ_Ko_id", sequenceName = "scott.k_lsk_id", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id; //id

    // объект Eolink
    @OneToOne(mappedBy = "koObj", fetch = FetchType.LAZY)
    private Eolink eolink;
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ID", referencedColumnName = "FK_KLSK_OBJ", updatable = false)
    //private Eolink eolink;

    // счетчик
    @OneToOne(mappedBy = "ko", fetch = FetchType.LAZY)
    private Meter meter;

    // счетчики, через Id фин.лиц.счета
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "FK_KLSK_OBJ", referencedColumnName = "ID", updatable = false)
    private Set<Meter> meterByKo = new HashSet<>(0);

    // лицевые счета
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "K_LSK_ID", referencedColumnName = "ID", updatable = false)
    private Set<Kart> kart = new HashSet<>(0);

    // лицевые счета, через Id помещения
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "FK_KLSK_PREMISE", referencedColumnName = "ID", updatable = false)
    private Set<Kart> kartByPremise = new HashSet<>(0);

    // внешние лиц.счета, через помещение
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "FK_KLSK_PREMISE", referencedColumnName = "ID", updatable = false)
    private Set<KartExt> kartExtByPremise = new HashSet<>(0);

    // внешние лиц.счета, через фин.лиц.счет
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "FK_KLSK_ID", referencedColumnName = "ID", updatable = false)
    private Set<KartExt> kartExtByKoKw = new HashSet<>(0);

    public Ko() {
        super();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ko))
            return false;

        Ko other = (Ko) o;

        if (id == other.getId()) return true;
        if (id == null) return false;

        // equivalence by id
        return id.equals(other.getId());
    }

    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

}

