package com.dic.bill.dto;

import com.dic.bill.model.scott.Org;
import com.dic.bill.model.scott.Usl;

import java.math.BigDecimal;
import java.util.Date;

/** DTO для хранения параметров для расчета начисления:
 *  Организацию, наличие счетчика, кол-во проживающих и т.п.
 *
 */
public class UslOrgPers {

  // дата начала
  public Date dtFrom;
  // дата окончания
  public Date dtTo;
  // услуга
  public Usl usl;
  // организация
  public Org org;
  // наличие счетчика
  public boolean isCounter;
  // пустая квартира?
  public boolean isEmpty;
  // соц.норма
  public BigDecimal socStdt = BigDecimal.ZERO;
  // кол-во проживающих
  public BigDecimal kpr = BigDecimal.ZERO;
  // кол-во временно зарегистрированных
  public BigDecimal kprWr = BigDecimal.ZERO;
  // кол-во временно отсутствующих
  public BigDecimal kprOt = BigDecimal.ZERO;

  // закрытый от доступа конструктор
  private UslOrgPers() {
  }



  public static final class UslOrgPersBuilder {
    // дата начала
    public Date dtFrom;
    // дата окончания
    public Date dtTo;
    // услуга
    public Usl usl;
    // организация
    public Org org;
    // наличие счетчика
    public boolean isCounter;
    // пустая квартира?
    public boolean isEmpty;
    // соц.норма
    public BigDecimal socStdt = BigDecimal.ZERO;
    // кол-во проживающих
    public BigDecimal kpr = BigDecimal.ZERO;
    // кол-во временно зарегистрированных
    public BigDecimal kprWr = BigDecimal.ZERO;
    // кол-во временно отсутствующих
    public BigDecimal kprOt = BigDecimal.ZERO;
    // доля дня в месяце
    public BigDecimal partDayMonth = BigDecimal.ZERO;

    private UslOrgPersBuilder() {
    }

    public static UslOrgPersBuilder anUslOrgPers() {
      return new UslOrgPersBuilder();
    }

    public UslOrgPersBuilder withDtFrom(Date dtFrom) {
      this.dtFrom = dtFrom;
      return this;
    }

    public UslOrgPersBuilder withDtTo(Date dtTo) {
      this.dtTo = dtTo;
      return this;
    }

    public UslOrgPersBuilder withUsl(Usl usl) {
      this.usl = usl;
      return this;
    }

    public UslOrgPersBuilder withOrg(Org org) {
      this.org = org;
      return this;
    }

    public UslOrgPersBuilder withIsCounter(boolean isCounter) {
      this.isCounter = isCounter;
      return this;
    }

    public UslOrgPersBuilder withIsEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
      return this;
    }

    public UslOrgPersBuilder withSocStdt(BigDecimal socStdt) {
      this.socStdt = socStdt;
      return this;
    }

    public UslOrgPersBuilder withKpr(int kpr) {
      this.kpr = BigDecimal.valueOf(kpr);
      return this;
    }

    public UslOrgPersBuilder withKprWr(int kprWr) {
      this.kprWr = BigDecimal.valueOf(kprWr);
      return this;
    }

    public UslOrgPersBuilder withKprOt(int kprOt) {
      this.kprOt = BigDecimal.valueOf(kprOt);
      return this;
    }

    public UslOrgPersBuilder withPartDayMonth(BigDecimal partDayMonth) {
      this.partDayMonth = partDayMonth;
      return this;
    }

    public UslOrgPers build() {
      UslOrgPers uslOrgPers = new UslOrgPers();
      uslOrgPers.dtTo = this.dtTo;
      uslOrgPers.dtFrom = this.dtFrom;
      uslOrgPers.usl = this.usl;
      uslOrgPers.org = this.org;
      uslOrgPers.isCounter = this.isCounter;
      uslOrgPers.isEmpty = this.isEmpty;
      uslOrgPers.socStdt = this.socStdt;
      // получить долю от значения
      uslOrgPers.kpr = this.kpr.multiply(this.partDayMonth);
      uslOrgPers.kprWr = this.kprWr.multiply(this.partDayMonth);
      uslOrgPers.kprOt = this.kprOt.multiply(this.partDayMonth);
      return uslOrgPers;
    }
  }
}
