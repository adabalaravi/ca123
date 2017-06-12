package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the RETAILER_DOMAIN database table.
 * 
 * @author BEA Workshop
 */
public class RetailerDomain  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String retailerId;
	private String hostDomain;

	
	
    public String getRetailerId() {
		return retailerId;
	}


	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}


	public String getHostDomain() {
		return hostDomain;
	}


	public void setHostDomain(String hostDomain) {
		this.hostDomain = hostDomain;
	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
//		if (!(other instanceof SysParameterPK)) {
//			return false;
//		}
		RetailerDomain castOther = (RetailerDomain)other;
		return new EqualsBuilder()
			.append(this.getRetailerId(), castOther.getRetailerId())
			.append(this.getHostDomain(), castOther.getHostDomain())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRetailerId())
			.append(getHostDomain())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("retailerId", getRetailerId())
			.append("hostDomain", getHostDomain())
			.toString();
	}
	
}