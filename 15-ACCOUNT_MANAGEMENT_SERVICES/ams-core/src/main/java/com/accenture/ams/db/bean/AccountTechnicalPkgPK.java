package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the ACCOUNT_TECHNICAL_PKG database table.
 * 
 * @author BEA Workshop
 */
public class AccountTechnicalPkgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String techPackageId;
	private Long userId;

    public AccountTechnicalPkgPK() {
    }

	public String getTechPackageId() {
		return this.techPackageId;
	}
	public void setTechPackageId(String techPackageId) {
		this.techPackageId = techPackageId;
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountTechnicalPkgPK)) {
			return false;
		}
		AccountTechnicalPkgPK castOther = (AccountTechnicalPkgPK)other;
		return new EqualsBuilder()
			.append(this.getTechPackageId(), castOther.getTechPackageId())
			.append(this.getUserId(), castOther.getUserId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTechPackageId())
			.append(getUserId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("techPackageId", getTechPackageId())
			.append("username", getUserId())
			.toString();
	}
}