package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Цена услуги
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Prices", schema="TEST")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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
	@JoinColumn(name="USL", referencedColumnName="USl", updatable = false, insertable = false)
	private Usl usl;

	// организация, по которой действует выделенная расценка
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID", updatable = false, insertable = false)
	private Org org;

	// цена
	@Column(name = "SUMMA", updatable = false, nullable = true)
	private BigDecimal price;

	// цена дополнительная (например за Гкал.)
	@Column(name = "SUMMA2", updatable = false, nullable = true)
	private BigDecimal priceAddit;

	// цена без проживающих
	@Column(name = "SUMMA3", updatable = false, nullable = true)
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

