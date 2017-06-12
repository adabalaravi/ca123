package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_PRODUCT database table.
 * 
 * @author BEA Workshop
 */
public class AccountProduct  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private AccountProductPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private AccountUser accountUser;

    public AccountProduct() {
    }

	public AccountProductPK getCompId() {
		return this.compId;
	}
	public void setCompId(AccountProductPK compId) {
		this.compId = compId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to AccountUser
	public AccountUser getAccountUser() {
		return this.accountUser;
	}
	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountProduct)) {
			return false;
		}
		AccountProduct castOther = (AccountProduct)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}
