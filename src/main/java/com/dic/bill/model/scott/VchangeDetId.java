package com.dic.bill.model.scott;

import java.util.Date;

// суррогатный первичный ключ
public class VchangeDetId  implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Kart kart;
	private String mgchange;
	private Date dt;
	private Usl usl;
	private Org org;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dt == null) ? 0 : dt.hashCode());
		result = prime * result + ((kart == null) ? 0 : kart.hashCode());
		result = prime * result + ((mgchange == null) ? 0 : mgchange.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
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
		VchangeDetId other = (VchangeDetId) obj;
		if (dt == null) {
			if (other.dt != null)
				return false;
		} else if (!dt.equals(other.dt))
			return false;
		if (kart == null) {
			if (other.kart != null)
				return false;
		} else if (!kart.equals(other.kart))
			return false;
		if (mgchange == null) {
			if (other.mgchange != null)
				return false;
		} else if (!mgchange.equals(other.mgchange))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (usl == null) {
			if (other.usl != null)
				return false;
		} else if (!usl.equals(other.usl))
			return false;
		return true;
	}



}