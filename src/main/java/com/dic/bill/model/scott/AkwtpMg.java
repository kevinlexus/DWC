package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Оплата по периоду - архив
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "A_KWTP_MG", schema="SCOTT")
@Getter @Setter
public class AkwtpMg implements java.io.Serializable  {

	public AkwtpMg() {
	}

	// Id
	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// сумма
	@Column(name = "SUMMA", updatable = false)
	private BigDecimal summa;

	// пеня
	@Column(name = "PENYA", updatable = false)
	private BigDecimal penya;

	// период оплаты
	@Column(name = "DOPL", updatable = false)
	private String dopl;

	// дата
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// № компьютера
	@Column(name = "NKOM", updatable = false)
	private String nkom;

	// код операции
	@Column(name = "OPER", updatable = false)
	private String oper;

	// архивный период
	@Column(name = "MG", updatable = false)
	private String mg;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof AkwtpMg))
	        return false;

	    AkwtpMg other = (AkwtpMg)o;

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

	public static final class AkwtpMgBuilder {
		// Id
        private Integer id;
		// сумма
        private BigDecimal summa;
		// пеня
        private BigDecimal penya;
		// период оплаты
        private String dopl;
		// дата
        private Date dt;
		// № компьютера
        private String nkom;
		// код операции
        private String oper;
		// архивный период
        private String mg;

		private AkwtpMgBuilder() {
		}

		public static AkwtpMgBuilder anAkwtpMg() {
			return new AkwtpMgBuilder();
		}

		public AkwtpMgBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public AkwtpMgBuilder withSumma(BigDecimal summa) {
			this.summa = summa;
			return this;
		}

		public AkwtpMgBuilder withPenya(BigDecimal penya) {
			this.penya = penya;
			return this;
		}

		public AkwtpMgBuilder withDopl(String dopl) {
			this.dopl = dopl;
			return this;
		}

		public AkwtpMgBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public AkwtpMgBuilder withNkom(String nkom) {
			this.nkom = nkom;
			return this;
		}

		public AkwtpMgBuilder withOper(String oper) {
			this.oper = oper;
			return this;
		}

		public AkwtpMgBuilder withMg(String mg) {
			this.mg = mg;
			return this;
		}

		public AkwtpMg build() {
			AkwtpMg akwtpMg = new AkwtpMg();
			akwtpMg.setId(id);
			akwtpMg.setSumma(summa);
			akwtpMg.setPenya(penya);
			akwtpMg.setDopl(dopl);
			akwtpMg.setDt(dt);
			akwtpMg.setNkom(nkom);
			akwtpMg.setOper(oper);
			akwtpMg.setMg(mg);
			return akwtpMg;
		}
	}
}

