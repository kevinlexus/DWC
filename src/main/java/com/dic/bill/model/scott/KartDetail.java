package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;


/**
 * Детализация карточки лиц.счета
 *
 * @author lev
 * @version 1.00
 */
@Entity
@Table(name = "KART_DETAIL", schema = "SCOTT")
@Getter
@Setter
@DynamicUpdate
public class KartDetail implements java.io.Serializable {

    public KartDetail() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KART_DETAIL")
    @SequenceGenerator(name = "SEQ_KART_DETAIL", sequenceName = "SCOTT.KART_DETAIL_ID", allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Integer id;

    // лиц.счет
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSK", referencedColumnName = "LSK")
    private Kart kart;

    // сортировка по адресу
    @Column(name = "ORD1")
    private Integer ord1;

    // на текущий момент, в контексте K_LSK_ID: Основной(Если нет основного - РСО), незакрытый лиц.счет.-1 Остальные - 0
    @Type(type= "org.hibernate.type.NumericBooleanType")
    @Column(name = "IS_MAIN")
    Boolean isMain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof KartDetail))
            return false;

        KartDetail other = (KartDetail) o;

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

