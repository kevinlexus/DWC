package com.dic.bill.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для группировки услуг и организаций
 * @author Lev
 *
 */
@Getter @Setter
public class UslOrg {

	public UslOrg(String uslId, Integer orgId) {
		super();
		this.uslId = uslId;
		this.orgId = orgId;
	}
	private String uslId;
	private Integer orgId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((uslId == null) ? 0 : uslId.hashCode());
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
		UslOrg other = (UslOrg) obj;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (uslId == null) {
			if (other.uslId != null)
				return false;
		} else if (!uslId.equals(other.uslId))
			return false;
		return true;
	}

}
