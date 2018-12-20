package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Счетчик
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "METER", schema="SCOTT")
@Getter @Setter
public class Meter implements java.io.Serializable {

	public Meter() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Meter_id")
	@SequenceGenerator(name="SEQ_Meter_id", sequenceName="scott.meter_id", allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id; // id записи

	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USL", referencedColumnName="USL", updatable = false, nullable = false)
	private Usl usl;

	// дата начала работы
	@Column(name = "DT1", updatable = false)
	private Date dt1;

	// дата окончания работы
	@Column(name = "DT2", updatable = false)
	private Date dt2;

	// Ko счетчика
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="K_LSK_ID", referencedColumnName="ID", updatable = false)
	private Ko ko;

	// Ko объекта, к которому присоединен счетчик
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID", updatable = false)
	private Ko koObj;

	// последнее показание
	@Column(name = "N1", updatable = false)
	private BigDecimal n1;

	// тип обмена с ГИС ЖКХ (null, 0 - нет обмена, 1-принимать показания от ГИС, 2-отправлять показания в ГИС, 3-принимать и отправлять показания в ГИС)
	@Column(name = "GIS_CONN_TP", updatable = false, nullable = true)
	private Integer gisConnTp;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Meter))
	        return false;

	    Meter other = (Meter)o;

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

