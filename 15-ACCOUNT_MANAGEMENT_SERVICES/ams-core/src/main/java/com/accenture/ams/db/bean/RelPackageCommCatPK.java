package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the REL_PACKAGE_COMM_CAT database table.
 * 
 * @author BEA Workshop
 */
public class RelPackageCommCatPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long offerTypeId;
	private Long packageId;
	private Long pod;

    public RelPackageCommCatPK() {
    }

	public Long getOfferTypeId() {
		return this.offerTypeId;
	}
	public void setOfferTypeId(Long offerTypeId) {
		this.offerTypeId = offerTypeId;
	}

	public Long getPackageId() {
		return this.packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getPod() {
		return this.pod;
	}
	public void setPod(Long pod) {
		this.pod = pod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelPackageCommCatPK)) {
			return false;
		}
		RelPackageCommCatPK castOther = (RelPackageCommCatPK)other;
		return new EqualsBuilder()
			.append(this.getOfferTypeId(), castOther.getOfferTypeId())
			.append(this.getPackageId(), castOther.getPackageId())
			.append(this.getPod(), castOther.getPod())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOfferTypeId())
			.append(getPackageId())
			.append(getPod())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("offerTypeId", getOfferTypeId())
			.append("packageId", getPackageId())
			.append("pod", getPod())
			.toString();
	}
}