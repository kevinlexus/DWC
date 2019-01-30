package com.dic.bill.model.scott;

// суррогатный первичный ключ
public class UslRoundId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// услуга
	private Usl usl;
	// код REU
	private String reu;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UslRoundId that = (UslRoundId) o;

		if (usl != null ? !usl.equals(that.usl) : that.usl != null) return false;
		return reu != null ? reu.equals(that.reu) : that.reu == null;
	}

	@Override
	public int hashCode() {
		int result = usl != null ? usl.hashCode() : 0;
		result = 31 * result + (reu != null ? reu.hashCode() : 0);
		return result;
	}
}