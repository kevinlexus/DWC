package com.dic.bill.model.scott;

// суррогатный первичный ключ
public class SaldoUslId  implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Kart kart; // лиц.счет
	private String usl; // код услуги
	private Integer org; // код организации
	private String mg; // период задолженности
	private String period; // период бухгалтерский

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kart == null) ? 0 : kart.hashCode());
		result = prime * result + ((mg == null) ? 0 : mg.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((usl == null) ? 0 : usl.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaldoUslId other = (SaldoUslId) obj;
		if (kart == null) {
			if (other.kart != null)
				return false;
		} else if (!kart.equals(other.kart))
			return false;
		if (mg == null) {
			if (other.mg != null)
				return false;
		} else if (!mg.equals(other.mg))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (usl == null) {
			if (other.usl != null)
				return false;
		} else if (!usl.equals(other.usl))
			return false;
		return true;
	}


}