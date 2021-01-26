package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Параметры по объекту
 *
 *
 */
@Getter @Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "T_OBJXPAR", schema="SCOTT")
public class ObjPar implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ObjPar_id")
	@SequenceGenerator(name="SEQ_ObjPar_id", sequenceName="scott.T_OBJXPAR_id", allocationSize=1)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LSK", referencedColumnName="LSK")
	private Kart kart;

	// параметр BD
	@Column(name = "N1", updatable = false, nullable = true)
	private BigDecimal n1;

	// параметр String
    @Column(name = "S1", updatable = false, nullable = true)
	private String s1;

	// параметр Date
	@Column(name = "D1", updatable = false, nullable = true)
	private Date d1;

	// период
	@Column(name = "mg", updatable = false, nullable = true)
	private String mg;

	// Ko объекта
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_K_LSK", referencedColumnName="ID")
	private Ko ko;

	// параметр
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LIST", referencedColumnName="ID")
	private Lst lst;

	// пользователь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER", referencedColumnName="ID")
	private Tuser tuser;

	// документ по обработке параметра (например реестр показаний по счетчикам)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_DOC", referencedColumnName="ID")
	private Doc doc;

	// timestamp
	@Column(name = "TS", updatable = false, nullable = true)
	private Date ts;

	// Статус загрузки показания при обмене с ГИС ЖКХ (0-добавлен на загрузку в ГИС, 1-в процессе загрузки в ГИС, 2-загружен в ГИС, 3-принят из ГИС, 4-ошибка загрузки в ГИС, смотреть COMM)
	@Column(name = "STATUS", nullable = false)
	private Integer status;

	// транспортрый GUID для ГИС
	@Column(name = "tguid")
	private String tguid;

	// результат загрузки в ГИС
	@Column(name = "comm")
	private String comm;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ObjPar))
	        return false;

	    ObjPar other = (ObjPar)o;

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

