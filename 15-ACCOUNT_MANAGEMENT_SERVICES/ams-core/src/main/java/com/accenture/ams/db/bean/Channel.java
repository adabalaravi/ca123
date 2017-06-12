package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CHANNEL database table.
 * 
 * @author BEA Workshop
 */
public class Channel  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long channelId;
//	private java.sql.Timestamp creationDate;
	private String externalId;
	private String isAdult;
	private String name;
	private Long orderId;
	private String type;
//	private java.sql.Timestamp updateDate;
	private String videoUrl;
	private java.util.Set<ChannelPlatform> channelPlatforms;
	private java.util.Set<ChannelTechnicalPkg> channelTechnicalPkgs;
	private java.util.Set<LiveContent> liveContents;
	//private java.util.Set<VlcContent> vlcContents;
	
	public Channel() {
    }

	public Long getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getExternalId() {
		return this.externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getIsAdult() {
		return this.isAdult;
	}
	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getOrderId() {
		return this.orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	public String getVideoUrl() {
		return this.videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	//bi-directional many-to-one association to ChannelPlatform
	public java.util.Set<ChannelPlatform> getChannelPlatforms() {
		return this.channelPlatforms;
	}
	public void setChannelPlatforms(java.util.Set<ChannelPlatform> channelPlatforms) {
		this.channelPlatforms = channelPlatforms;
	}

	//bi-directional many-to-one association to ChannelTechnicalPkg
	public java.util.Set<ChannelTechnicalPkg> getChannelTechnicalPkgs() {
		return this.channelTechnicalPkgs;
	}
	public void setChannelTechnicalPkgs(java.util.Set<ChannelTechnicalPkg> channelTechnicalPkgs) {
		this.channelTechnicalPkgs = channelTechnicalPkgs;
	}

	
	public java.util.Set<LiveContent> getLiveContents() {
		return this.liveContents;
	}
	public void setLiveContents(java.util.Set<LiveContent> liveContents) {
		this.liveContents = liveContents;
	}

	/*
	public java.util.Set<VlcContent> getVlcContents() {
		return this.vlcContents;
	}
	public void setVlcContents(java.util.Set<VlcContent> vlcContents) {
		this.vlcContents = vlcContents;
	}
	*/

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Channel)) {
			return false;
		}
		Channel castOther = (Channel)other;
		return new EqualsBuilder()
			.append(this.getChannelId(), castOther.getChannelId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getChannelId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("channelId", getChannelId())
			.toString();
	}
}
