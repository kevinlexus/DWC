package com.dic.bill.model.scott;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
/**
 * Лицевой счет (он же - помещение)
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART", schema="SCOTT")
@Getter @Setter
public class Kart implements java.io.Serializable{

	public Kart() {
	}

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // ID задается при создании Entity
    @Column(name = "LSK", updatable = false, nullable = false)
	private String lsk; //id записи

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REU", referencedColumnName="REU")
	private Org uk;

    @Column(name = "KUL", updatable = false, nullable = false)
	private String kul;

    @Column(name = "ND", updatable = false, nullable = false)
	private String nd;

	// номер квартиры
	@Column(name = "KW", updatable = false, nullable = false)
	private String num;

	// дата ограничения пени
	@Column(name = "PN_DT", updatable = false, nullable = false)
	private Date pnDt;

	// номер подъезда
	@Column(name = "ENTR", nullable = true)
	private Integer entry;

	// признак счета
	@Column(name = "PSCH", nullable = false)
	private Integer psch;

	// кол-во проживающих
	@Column(name = "KPR", nullable = false)
	private Integer kpr;

	// Кол-во вр.зарег.
	@Column(name = "KPR_WR", nullable = false)
	private Integer kprWr;

	// Кол-во вр.отсут.
	@Column(name = "KPR_OT", nullable = false)
	private Integer kprOt;

	// тип лиц.счета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID", updatable = false, insertable = true)
	private Lst tp;

	// Ko помешения (здесь OneToOne)
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="K_LSK_ID", referencedColumnName="ID", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private Ko koKw;

	// Ko лиц.счета (здесь OneToOne, cascade=CascadeType.ALL)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID", updatable = false, insertable = true)
	private Ko koLsk;

	// дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="HOUSE_ID", referencedColumnName="ID", updatable = false, insertable = true)
	private House house;

	// общая площадь
	@Column(name = "OPL", nullable = true)
	private BigDecimal opl;

	// начало действия
	@Column(name = "MG1", nullable = true)
	private String mgFrom;

	// окончание действия
	@Column(name = "MG2", nullable = true)
	private String mgTo;

	// набор услуг
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<Nabor> nabor = new ArrayList<>(0);

	// проживающие
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<KartPr> kartPr = new ArrayList<>(0);

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kart))
	        return false;

	    Kart other = (Kart)o;

	    if (lsk == other.getLsk()) return true;
	    if (lsk == null) return false;

	    // equivalence by id
	    return lsk.equals(other.getLsk());
	}

	@Override
	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

