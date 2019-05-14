package com.dic.bill.model.scott;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Документ перерасчета
 * @author lev
 * @version 1.01
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_CHANGE_DOCS", schema="SCOTT")
@Getter @Setter
public class ChangeDoc implements java.io.Serializable  {

	public ChangeDoc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHANGE_DOC_ID")
	@SequenceGenerator(name = "SEQ_CHANGE_DOC_ID", sequenceName = "SCOTT.CHANGES_ID", allocationSize = 1)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private Integer id;

	// период за который перерасчет (бред) - убрать бы это, ред.01.04.2019
	@Column(name = "MGCHANGE", updatable = false)
	private String mgchange;

	// дата перерасчета
	@Column(name = "DTEK", updatable = false)
	private Date dt;

	// период которым надо провести изменения (бред) - убрать бы это, ред.01.04.2019
	@Column(name = "MG2", updatable = false)
	private String mg2;

	// примечание - тип корректировки (PAY_SAL-корректировка сальдо, оплатой)
	@Column(name = "CD_TP", updatable = false)
	private String cdTp;

	// описание
	@Column(name = "TEXT", updatable = false)
	private String text;

	// пользователь
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", updatable = false, nullable = false)
	private Tuser user;

	// перерасчеты
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="DOC_ID", referencedColumnName="ID", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<Change> change = new ArrayList<>(0);

	// корректировки оплатой
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="FK_DOC", referencedColumnName="ID", updatable = false) // updatable = false - чтобы не было Update Foreign key
	private List<CorrectPay> correctPay = new ArrayList<>(0);


	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ChangeDoc))
	        return false;

	    ChangeDoc other = (ChangeDoc)o;

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

	public static final class ChangeDocBuilder {
		// период за который перерасчет (бред) - убрать бы это, ред.01.04.2019
		private String mgchange;
		// дата перерасчета
		private Date dt;
		// период которым надо провести изменения (бред) - убрать бы это, ред.01.04.2019
		private String mg2;
		// примечание - тип корректировки (PAY_SAL-корректировка сальдо, оплатой)
		private String cdTp;
		// описание
		private String text;
		// пользователь
		private Tuser user;
		// перерасчеты
		// updatable = false - чтобы не было Update Foreign key
		private List<Change> change = new ArrayList<>(0);
		// корректировки оплатой
		// updatable = false - чтобы не было Update Foreign key
		private List<CorrectPay> correctPay = new ArrayList<>(0);

		private ChangeDocBuilder() {
		}

		public static ChangeDocBuilder aChangeDoc() {
			return new ChangeDocBuilder();
		}

		public ChangeDocBuilder withMgchange(String mgchange) {
			this.mgchange = mgchange;
			return this;
		}

		public ChangeDocBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public ChangeDocBuilder withMg2(String mg2) {
			this.mg2 = mg2;
			return this;
		}

		public ChangeDocBuilder withCdTp(String cdTp) {
			this.cdTp = cdTp;
			return this;
		}

		public ChangeDocBuilder withText(String text) {
			this.text = text;
			return this;
		}

		public ChangeDocBuilder withUser(Tuser user) {
			this.user = user;
			return this;
		}

		public ChangeDocBuilder withChange(List<Change> change) {
			this.change = change;
			return this;
		}

		public ChangeDocBuilder withCorrectPay(List<CorrectPay> correctPay) {
			this.correctPay = correctPay;
			return this;
		}

		public ChangeDoc build() {
			ChangeDoc changeDoc = new ChangeDoc();
			changeDoc.setMgchange(mgchange);
			changeDoc.setDt(dt);
			changeDoc.setMg2(mg2);
			changeDoc.setCdTp(cdTp);
			changeDoc.setUser(user);
			changeDoc.setChange(change);
			changeDoc.setCorrectPay(correctPay);
			changeDoc.setText(text);
			return changeDoc;
		}
	}
}

