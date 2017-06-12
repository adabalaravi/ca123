package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the STATUS_TYPE database table.
 * 
 * @author BEA Workshop
 */
public class StatusType  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long statusId;
//	private java.sql.Timestamp creationDate;
	private String statusDescription;
	private String statusName;
//	private java.sql.Timestamp updateDate;
	private java.util.Set<AccountContent> accountContents;
	private java.util.Set<AccountUser> accountUsers;
	private java.util.Set<Product> products;

    public StatusType() {
    }

	public Long getStatusId() {
		return this.statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getStatusDescription() {
		return this.statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatusName() {
		return this.statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to AccountContent
	public java.util.Set<AccountContent> getAccountContents() {
		return this.accountContents;
	}
	public void setAccountContents(java.util.Set<AccountContent> accountContents) {
		this.accountContents = accountContents;
	}

	//bi-directional many-to-one association to AccountUser
	public java.util.Set<AccountUser> getAccountUsers() {
		return this.accountUsers;
	}
	public void setAccountUsers(java.util.Set<AccountUser> accountUsers) {
		this.accountUsers = accountUsers;
	}

	//bi-directional many-to-one association to Product
	public java.util.Set<Product> getProducts() {
		return this.products;
	}
	public void setProducts(java.util.Set<Product> products) {
		this.products = products;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StatusType)) {
			return false;
		}
		StatusType castOther = (StatusType)other;
		return new EqualsBuilder()
			.append(this.getStatusId(), castOther.getStatusId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getStatusId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("statusId", getStatusId())
			.toString();
	}
}