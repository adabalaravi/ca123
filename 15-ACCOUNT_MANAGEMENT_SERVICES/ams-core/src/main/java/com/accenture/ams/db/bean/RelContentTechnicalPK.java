package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the REL_CONTENT_TECHNICAL database table.
 * 
 * @author BEA Workshop
 */
public class RelContentTechnicalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String externalId;
	private Long packageId;

    public RelContentTechnicalPK() {
    }

	public String getExternalId() {
		return this.externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getPackageId() {
		return this.packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelContentTechnicalPK)) {
			return false;
		}
		RelContentTechnicalPK castOther = (RelContentTechnicalPK)other;
		return new EqualsBuilder()
			.append(this.getExternalId(), castOther.getExternalId())
			.append(this.getPackageId(), castOther.getPackageId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getExternalId())
			.append(getPackageId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("externalId", getExternalId())
			.append("packageId", getPackageId())
			.toString();
	}
}