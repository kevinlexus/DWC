package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Цена услуги
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PRICES", schema="SCOTT")
@Immutable
@Cacheable
@org.hibernate.annotations.Cache(region = "BillDirectEntitiesCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter @Setter
public class Price implements java.io.Serializable {

	public Price() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Price_id")
	@SequenceGenerator(name="SEQ_Price_id", sequenceName="scott.prices_id", allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USL", referencedColumnName="USl")
	private Usl usl;

	// организация, по которой действует выделенная расценка
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org;

	// цена
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal price;

	// цена дополнительная (например за Гкал.)
	@Column(name = "SUMMA2", updatable = false)
	private BigDecimal priceAddit;

	// цена без проживающих
	@Column(name = "SUMMA3", updatable = false)
	private BigDecimal priceEmpt;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Price))
	        return false;

	    Price other = (Price)o;

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

