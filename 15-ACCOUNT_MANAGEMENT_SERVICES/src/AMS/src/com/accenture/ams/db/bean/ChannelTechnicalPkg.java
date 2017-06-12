package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CHANNEL_TECHNICAL_PKG database table.
 * 
 * @author BEA Workshop
 */
public class ChannelTechnicalPkg  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private ChannelTechnicalPkgPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	private Channel channel;
	private TechnicalPackageProfile technicalPackage;

    public ChannelTechnicalPkg() {
    }

	public ChannelTechnicalPkgPK getCompId() {
		return this.compId;
	}
	public void setCompId(ChannelTechnicalPkgPK compId) {
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

	//bi-directional many-to-one association to Channel
	public Channel getChannel() {
		return this.channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	//bi-directional many-to-one association to TechnicalPackage
	public TechnicalPackageProfile getTechnicalPackage() {
		return this.technicalPackage;
	}
	public void setTechnicalPackage(TechnicalPackageProfile technicalPackage) {
		this.technicalPackage = technicalPackage;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChannelTechnicalPkg)) {
			return false;
		}
		ChannelTechnicalPkg castOther = (ChannelTechnicalPkg)other;
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
