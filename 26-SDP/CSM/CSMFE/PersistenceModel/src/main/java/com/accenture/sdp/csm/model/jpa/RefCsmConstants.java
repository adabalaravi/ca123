package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the REF_CSM_CONSTANTS database table.
 * 
 */
@Entity
@Table(name = "REF_CSM_CONSTANTS")
@NamedQueries({ @NamedQuery(name = RefCsmConstants.CONSTANTS_RETRIEVE_ALL, query = "select c from RefCsmConstants c") })
public class RefCsmConstants implements Serializable {
	private static final long serialVersionUID = -5734770716101617527L;
	
	public static final String CONSTANTS_RETRIEVE_ALL = "searchAllConstants";

	@Id
	@Column(name = "CONSTANT_KEY")
	private String constantKey;

	@Column(name = "CONSTANT_VALUE")
	private String constantValue;

	public String getConstantKey() {
		return constantKey;
	}

	public void setConstantKey(String constantKey) {
		this.constantKey = constantKey;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constantKey == null) ? 0 : constantKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RefCsmConstants other = (RefCsmConstants) obj;
		if (getConstantKey() == null) {
			if (other.getConstantKey() != null) {
				return false;
			}
		} else if (!getConstantKey().equals(other.getConstantKey())) {
			return false;
		}
		return true;
	}
}
