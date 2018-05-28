package com.dic.bill.model.scott;


// суррогатный первичный ключ
public class ApenyaId  implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Kart kart; // лиц.счет
	private Integer mg1; // период задолженности
	private String mg; // архивный период
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kart == null) ? 0 : kart.hashCode());
		result = prime * result + ((mg == null) ? 0 : mg.hashCode());
		result = prime * result + ((mg1 == null) ? 0 : mg1.hashCode());
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
		ApenyaId other = (ApenyaId) obj;
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
		if (mg1 == null) {
			if (other.mg1 != null)
				return false;
		} else if (!mg1.equals(other.mg1))
			return false;
		return true;
	}


}