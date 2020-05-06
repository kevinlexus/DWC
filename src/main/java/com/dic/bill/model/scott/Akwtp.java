package com.dic.bill.model.scott;

//import com.dic.bill.model.exs.Pdoc;
import com.dic.bill.model.exs.Pdoc;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Оплата, заголовок - архив
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_KWTP", schema="SCOTT")
@Getter @Setter
public class Akwtp implements java.io.Serializable, KwtpPay  {

	public Akwtp() {
	}

	// Id
	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK")
	private Kart kart;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// пеня
	@Column(name = "PENYA", updatable = false)
	private BigDecimal penya;

	// дата
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// дата инкассации
	@Column(name = "DAT_INK", updatable = false)
	private Date dtInk;

	// детализация платежа по периодам
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="C_KWTP_ID", referencedColumnName="ID")
	private List<AkwtpMg> akwtpMg = new ArrayList<AkwtpMg>(0);

	// № платежного документа (из банка, из ГИС ЖКХ)
	@Column(name = "NUM_DOC", updatable = false)
	private String numDoc;

	// период оплаты - на этом уровне не используется, оставлено для совместимости
	@Column(name = "DOPL", updatable = false)
	private String dopl;

	// архивный период
	@Column(name = "MG", updatable = false)
	private String mg;

	// ПД из ГИС ЖКХ
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PDOC", referencedColumnName="ID")
	private Pdoc pdoc;

	// № компьютера
	@Column(name = "NKOM", updatable = false)
	private String nkom;

	// код операции
	@Column(name = "OPER", updatable = false)
	private String oper;

	// аннулировано?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "ANNUL", nullable = true)
	private Boolean isAnnul;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Akwtp))
	        return false;

	    Akwtp other = (Akwtp)o;

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

	public static final class AkwtpBuilder {
		// Id
        private Integer id;
		// лиц.счет
        private Kart kart;
		// сумма
        private BigDecimal summa;
		// пеня
        private BigDecimal penya;
		// дата
        private Date dt;
		// детализация платежа по периодам
        private List<AkwtpMg> akwtpMg = new ArrayList<AkwtpMg>(0);
		// № платежного документа (из банка, из ГИС ЖКХ)
        private String numDoc;
		// период оплаты - на этом уровне не используется, оставлено для совместимости
        private String dopl;
		// архивный период
        private String mg;
		// ПД из ГИС ЖКХ
        private Pdoc pdoc;
		// № компьютера
        private String nkom;
		// код операции
        private String oper;

		private AkwtpBuilder() {
		}

		public static AkwtpBuilder anAkwtp() {
			return new AkwtpBuilder();
		}

		public AkwtpBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public AkwtpBuilder withKart(Kart kart) {
			this.kart = kart;
			return this;
		}

		public AkwtpBuilder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public AkwtpBuilder withPenya(BigDecimal penya) {
			this.penya = penya;
			return this;
		}

		public AkwtpBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public AkwtpBuilder withAkwtpMg(List<AkwtpMg> akwtpMg) {
			this.akwtpMg = akwtpMg;
			return this;
		}

		public AkwtpBuilder withNumDoc(String numDoc) {
			this.numDoc = numDoc;
			return this;
		}

		public AkwtpBuilder withDopl(String dopl) {
			this.dopl = dopl;
			return this;
		}

		public AkwtpBuilder withMg(String mg) {
			this.mg = mg;
			return this;
		}

		public AkwtpBuilder withPdoc(Pdoc pdoc) {
			this.pdoc = pdoc;
			return this;
		}

		public AkwtpBuilder withNkom(String nkom) {
			this.nkom = nkom;
			return this;
		}

		public AkwtpBuilder withOper(String oper) {
			this.oper = oper;
			return this;
		}

		public Akwtp build() {
			Akwtp akwtp = new Akwtp();
			akwtp.setId(id);
			akwtp.setKart(kart);
			akwtp.setSumma(summa);
			akwtp.setPenya(penya);
			akwtp.setDt(dt);
			akwtp.setAkwtpMg(akwtpMg);
			akwtp.setNumDoc(numDoc);
			akwtp.setDopl(dopl);
			akwtp.setMg(mg);
			akwtp.setPdoc(pdoc);
			akwtp.setNkom(nkom);
			akwtp.setOper(oper);
			return akwtp;
		}
	}
}

