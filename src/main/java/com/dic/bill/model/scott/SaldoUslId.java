package com.dic.bill.model.scott;

import java.util.Objects;

// суррогатный первичный ключ
public class SaldoUslId  implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Kart kart; // лиц.счет
	private Usl usl; // код услуги
	private Org org; // код организации
	private String mg; // период бухгалтерский

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SaldoUslId that = (SaldoUslId) o;
		return kart.equals(that.kart) &&
				usl.equals(that.usl) &&
				org.equals(that.org) &&
				mg.equals(that.mg);
	}

	@Override
	public int hashCode() {
		return Objects.hash(kart, usl, org, mg);
	}
}