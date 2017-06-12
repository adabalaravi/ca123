package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the LIVE_CONTENT database table.
 * 
 * @author BEA Workshop
 */
public class LiveContent  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
//	private LiveContentPK compId;
//	private java.sql.Timestamp creationDate;
	private long duration;
	private java.sql.Timestamp endTime;
	private String externalContentId;
	private String isHd;
	private String longDescription;
	private String shortDescription;
	private java.sql.Timestamp startTime;
	private Long subscriptionId;
	private String title;

	private java.sql.Timestamp updateDate;
	private Category category;
	private Channel channel;
	private PcLevel pcLevelBean;
	private java.util.Set<RelLiveContentExtrat> relLiveContentExtrats;
	private Long liveContentId;
	private java.sql.Timestamp playlistPublishedDate;
	private Long channelId;
	private String status;
    public LiveContent() {
    }



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Long getLiveContentId() {
		return liveContentId;
	}

	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}

	public java.sql.Timestamp getPlaylistPublishedDate() {
		return playlistPublishedDate;
	}

	public void setPlaylistPublishedDate(java.sql.Timestamp playlistPublishedDate) {
		this.playlistPublishedDate = playlistPublishedDate;
	}

	public Long getChannelId() {
		return channelId;
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

	public long getDuration() {
		return this.duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

	public java.sql.Timestamp getEndTime() {
		return this.endTime;
	}
	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getExternalContentId() {
		return this.externalContentId;
	}
	public void setExternalContentId(String externalContentId) {
		this.externalContentId = externalContentId;
	}

	public String getIsHd() {
		return this.isHd;
	}
	public void setIsHd(String isHd) {
		this.isHd = isHd;
	}

	public String getLongDescription() {
		return this.longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public java.sql.Timestamp getStartTime() {
		return this.startTime;
	}
	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}

	public Long getSubscriptionId() {
		return this.subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}
//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to Category
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	//bi-directional many-to-one association to Channel
	public Channel getChannel() {
		return this.channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	//bi-directional many-to-one association to PcLevel
	public PcLevel getPcLevelBean() {
		return this.pcLevelBean;
	}
	public void setPcLevelBean(PcLevel pcLevelBean) {
		this.pcLevelBean = pcLevelBean;
	}

	//bi-directional many-to-one association to RelLiveContentExtrat
	public java.util.Set<RelLiveContentExtrat> getRelLiveContentExtrats() {
		return this.relLiveContentExtrats;
	}
	public void setRelLiveContentExtrats(java.util.Set<RelLiveContentExtrat> relLiveContentExtrats) {
		this.relLiveContentExtrats = relLiveContentExtrats;
	}

	

//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof LiveContent)) {
//			return false;
//		}
//		LiveContent castOther = (LiveContent)other;
//		return new EqualsBuilder()
//			.append(this.getCompId(), castOther.getCompId())
//			.isEquals();
//    }
//    
//	public int hashCode() {
//		return new HashCodeBuilder()
//			.append(getCompId())
//			.toHashCode();
//    }   
//
//	public String toString() {
//		return new ToStringBuilder(this)
//			.append("compId", getCompId())
//			.toString();
//	}
}