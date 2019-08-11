package com.dic.bill.model.exs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.*;

//import com.dic.bill.model.scott.Kart; $$$$$
import com.dic.bill.model.bs.Lst2;
import com.dic.bill.model.scott.Kart;
import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.scott.Ko;
import com.dic.bill.model.scott.Org;
import com.dic.bill.model.sec.User;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


/**
 * Связанный объект
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLINK", schema="EXS")
@DynamicUpdate
@Getter @Setter
public class Eolink implements java.io.Serializable  {

	public Eolink() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EOLINK")
	@SequenceGenerator(name="SEQ_EOLINK", sequenceName="EXS.SEQ_EOLINK", allocationSize=1)
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	// РЭУ в системе "Директ" note - повторяемая колонка - исправить
	//@Column(name = "REU", insertable = false, updatable = false)
	//private String reu;

	// УК/РСО в системе "Директ"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REU", referencedColumnName="REU")
	private Org org;

	// улица в системе "Директ"
	@Column(name = "KUL")
	private String kul;

	// дом в системе "Директ"
	@Column(name = "ND")
	private String nd;

	// квартира в системе "Директ"
	@Column(name = "KW")
	private String kw;

	// подъезд в  системе "Директ"
	@Column(name = "ENTRY")
	private Integer entry;

	// услуга в системе "Директ" (для счетчика)
	@Column(name = "USL")
	private String usl;

	// GUID объекта во внешней системе
	@Column(name = "GUID")
	private String guid;

	// уникальный номер объекта во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// идентификатор ЖКУ (Заполняется только для Лиц.счетов, при экспорте из ГИС)
	@Column(name = "SERVICEID")
	private String serviceId;

	// CD (для ПД - номер документа в биллинге)
	@Column(name = "CD")
	private String cd;

	// тип объекта, например "Дом"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTP", referencedColumnName="ID", updatable = false)
	private AddrTp objTp;

	// тип информационной системы (0- "Квартплата", 1 - "Новая разработка")
	@Column(name = "APP_TP")
	private Integer appTp;

	// расширенный тип объекта (например "Договор управления") (используется для обмена с "Квартплатой")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTPX", referencedColumnName="ID")
	private Lst2 objTpx;

	// идентификатор объекта связанного с EOLINK, в новой разработке
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_KLSK_OBJ")
	private Ko koObj;

	// лицевой счет (если объект Eolink - является лиц.счетом)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// родительский объект
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private Eolink parent;

	// УК (Принадлежащий УК лиц.счет)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UK", referencedColumnName="ID")
	private Eolink uk;

	// ОГРН Организации
	@Column(name = "OGRN")
	private String ogrn;

	// транспортный GUID объекта
	@Column(name = "TGUID")
	private String tguid;

	// пользователь создавший запись
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER", referencedColumnName="ID")
	private User user;

	// параметры
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private List<EolinkPar> eolinkPar = new ArrayList<>(0);

	// дочерние объекты
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private List<Eolink> child = new ArrayList<>(0);

	// задания
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private List<Task> task = new ArrayList<>(0);

	// платежные документы (для уровня Лицевой счет)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private List<Pdoc> pdoc = new ArrayList<>(0);

	// дочерние объекты, связанные через EOLXEOL
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.EOLXEOL", joinColumns = {
			@JoinColumn(name = "FK_PARENT", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_CHILD",
					nullable = false, updatable = false) })
	private List<Eolink> childLinked = new ArrayList<>(0);

	// родительские объекты, связанные через EOLXEOL
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.EOLXEOL", joinColumns = {
			@JoinColumn(name = "FK_CHILD", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_PARENT",
					nullable = false, updatable = false) })
	private List<Eolink> parentLinked = new ArrayList<>(0);

	// Дочерние объекты, связанные через внешнюю таблицу
/*	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID")
	private List<EolinkToEolink> childLinked = new ArrayList<EolinkToEolink>(0);
*/
	// Родительские объекты, связанные через внешнюю таблицу
/*	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID")
	private List<EolinkToEolink> parentLinked = new ArrayList<EolinkToEolink>(0);
*/
	// статус, 0 - архивная запись, 1-активная запись
	@Column(name = "STATUS")
	private Integer status;

	// ID лиц.счета в системе "Квартплата" (Заполняется только для Лиц.счетов)
	@Column(name = "C_LSK_ID")
	private Integer cLskId;

	// дата создания
	@Column(name = "DT_CRT", updatable = false)
	private Date dtCrt;

	// дата обновления
	@Column(name = "DT_UPD")
	private Date updDt;

	// код ошибки
	@Column(name = "ERR")
	private Long err;

	// примечание по объекту (описание ошибки)
	@Column(name = "COMM")
	private String comm;

	@Generated("SparkTools")
	private Eolink(Builder builder) {
		this.id = builder.id;
		// note исправить this.reu = builder.reu;
		this.kul = builder.kul;
		this.nd = builder.nd;
		this.kw = builder.kw;
		this.org = builder.org;
		this.entry = builder.entry;
		this.usl = builder.usl;
		this.guid = builder.guid;
		this.un = builder.un;
		this.serviceId = builder.serviceId;
		this.cd = builder.cd;
		this.objTp = builder.objTp;
		this.appTp = builder.appTp;
		this.objTpx = builder.objTpx;
		this.koObj = builder.koObj;
		this.parent = builder.parent;
		this.uk = builder.uk;
		this.ogrn = builder.ogrn;
		this.user = builder.user;
		this.eolinkPar = builder.eolinkPar;
		this.child = builder.child;
		this.childLinked = builder.childLinked;
		this.parentLinked = builder.parentLinked;
		this.status = builder.status;
		this.cLskId = builder.cLskId;
		this.dtCrt = builder.crtDt;
		this.updDt = builder.updDt;
		this.comm = builder.comm;
		this.kart = builder.kart;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Eolink))
	        return false;

	    Eolink other = (Eolink)o;

	    // equivalence by id
	    return getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (getId() != null) {
	        return getId().hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


	/**
	 * Creates builder to build {@link Eolink}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Eolink}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		//private String reu;
		private String kul;
		private String nd;
		private String kw;
		private Integer entry;
		private Org org;
		private String usl;
		private Integer idCnt;
		private Integer idGrp;
		private String guid;
		private String un;
		private String serviceId;
		private String cd;
		private AddrTp objTp;
		private Integer appTp;
		private Lst2 objTpx;
		private Ko koObj;
		private Eolink parent;
		private Eolink uk;
		private String ogrn;
		private User user;
		private List<EolinkPar> eolinkPar = Collections.emptyList();
		private List<Eolink> child = Collections.emptyList();
		private List<Eolink> childLinked = Collections.emptyList();
		private List<Eolink> parentLinked = Collections.emptyList();
		private Integer status;
		private Integer cLskId;
		private Date crtDt;
		private Date updDt;
		private String comm;
		private Kart kart;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

/*
		public Builder withReu(String reu) {
			this.reu = reu;
			return this;
		}
*/

		public Builder withKul(String kul) {
			this.kul = kul;
			return this;
		}

		public Builder withNd(String nd) {
			this.nd = nd;
			return this;
		}

		public Builder withKw(String kw) {
			this.kw = kw;
			return this;
		}

		public Builder withOrg(Org org) {
			this.org = org;
			return this;
		}

		public Builder withEntry(Integer entry) {
			this.entry = entry;
			return this;
		}

		public Builder withUsl(String usl) {
			this.usl = usl;
			return this;
		}

		public Builder withIdCnt(Integer idCnt) {
			this.idCnt = idCnt;
			return this;
		}

		public Builder withIdGrp(Integer idGrp) {
			this.idGrp = idGrp;
			return this;
		}

		public Builder withGuid(String guid) {
			this.guid = guid;
			return this;
		}

		public Builder withUn(String un) {
			this.un = un;
			return this;
		}

		public Builder withServiceId(String serviceId) {
			this.serviceId = serviceId;
			return this;
		}

		public Builder withCd(String cd) {
			this.cd = cd;
			return this;
		}

		public Builder withObjTp(AddrTp objTp) {
			this.objTp = objTp;
			return this;
		}

		public Builder withAppTp(Integer appTp) {
			this.appTp = appTp;
			return this;
		}

		public Builder withObjTpx(Lst2 objTpx) {
			this.objTpx = objTpx;
			return this;
		}

		public Builder withKoObj(Ko koObj) {
			this.koObj = koObj;
			return this;
		}

		public Builder withParent(Eolink parent) {
			this.parent = parent;
			return this;
		}

		public Builder withUk(Eolink uk) {
			this.uk = uk;
			return this;
		}

		public Builder withOgrn(String ogrn) {
			this.ogrn = ogrn;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withEolinkPar(List<EolinkPar> eolinkPar) {
			this.eolinkPar = eolinkPar;
			return this;
		}

		public Builder withChild(List<Eolink> child) {
			this.child = child;
			return this;
		}

		public Builder withChildLinked(List<Eolink> childLinked) {
			this.childLinked = childLinked;
			return this;
		}

		public Builder withParentLinked(List<Eolink> parentLinked) {
			this.parentLinked = parentLinked;
			return this;
		}

		public Builder withStatus(Integer status) {
			this.status = status;
			return this;
		}

		public Builder withCLskId(Integer cLskId) {
			this.cLskId = cLskId;
			return this;
		}

		public Builder withCrtDt(Date crtDt) {
			this.crtDt = crtDt;
			return this;
		}

		public Builder withUpdDt(Date updDt) {
			this.updDt = updDt;
			return this;
		}

		public Builder withComm(String comm) {
			this.comm = comm;
			return this;
		}

		public Builder withKart(Kart kart) {
			this.kart = kart;
			return this;
		}

		public Eolink build() {
			return new Eolink(this);
		}
	}

}

