package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CHANNEL_TECHNICAL_PKG database table.
 * 
 * @author BEA Workshop
 */
public class ChannelTechnicalPkgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long channelId;
	private Long packageId;

    public ChannelTechnicalPkgPK() {
    }

	public Long getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
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
		if (!(other instanceof ChannelTechnicalPkgPK)) {
			return false;
		}
		ChannelTechnicalPkgPK castOther = (ChannelTechnicalPkgPK)other;
		return new EqualsBuilder()
			.append(this.getChannelId(), castOther.getChannelId())
			.append(this.getPackageId(), castOther.getPackageId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getChannelId())
			.append(getPackageId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("channelId", getChannelId())
			.append("packageId", getPackageId())
			.toString();
	}
}