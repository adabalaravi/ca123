package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CHANNEL_PLATFORM database table.
 * 
 * @author BEA Workshop
 */
public class ChannelPlatform  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private ChannelPlatformPK compId;
//	private java.sql.Timestamp creationDate;
	private String isPublished;
	private String logoBig;
	private String logoMedium;
	private String logSmall;
	private Long orderId;
	private String picturesUrl;
	private String trailerUrl;
//	private java.sql.Timestamp updateDate;
	private String videoUrl;
	private Channel channel;
	private String group;
	private Long backgroundRgbR;
	private Long backgroundRgbG;
	private Long backgroundRgbB;
	private String channelCarouselThumbnail;
	

    public ChannelPlatform() {
    }
    
    

	public String getChannelCarouselThumbnail() {
		return channelCarouselThumbnail;
	}



	public void setChannelCarouselThumbnail(String channelCarouselThumbnail) {
		this.channelCarouselThumbnail = channelCarouselThumbnail;
	}



	public String getGroup() {
		return group;
	}



	public void setGroup(String group) {
		this.group = group;
	}



	public Long getBackgroundRgbR() {
		return backgroundRgbR;
	}



	public void setBackgroundRgbR(Long backgroundRgbR) {
		this.backgroundRgbR = backgroundRgbR;
	}



	public Long getBackgroundRgbG() {
		return backgroundRgbG;
	}



	public void setBackgroundRgbG(Long backgroundRgbG) {
		this.backgroundRgbG = backgroundRgbG;
	}



	public Long getBackgroundRgbB() {
		return backgroundRgbB;
	}



	public void setBackgroundRgbB(Long backgroundRgbB) {
		this.backgroundRgbB = backgroundRgbB;
	}



	public ChannelPlatformPK getCompId() {
		return this.compId;
	}
	public void setCompId(ChannelPlatformPK compId) {
		this.compId = compId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getIsPublished() {
		return this.isPublished;
	}
	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public String getLogoBig() {
		return this.logoBig;
	}
	public void setLogoBig(String logoBig) {
		this.logoBig = logoBig;
	}

	public String getLogoMedium() {
		return this.logoMedium;
	}
	public void setLogoMedium(String logoMedium) {
		this.logoMedium = logoMedium;
	}

	public String getLogSmall() {
		return this.logSmall;
	}
	public void setLogSmall(String logSmall) {
		this.logSmall = logSmall;
	}

	public Long getOrderId() {
		return this.orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPicturesUrl() {
		return this.picturesUrl;
	}
	public void setPicturesUrl(String picturesUrl) {
		this.picturesUrl = picturesUrl;
	}

	public String getTrailerUrl() {
		return this.trailerUrl;
	}
	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
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

	//bi-directional many-to-one association to Channel
	public Channel getChannel() {
		return this.channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChannelPlatform)) {
			return false;
		}
		ChannelPlatform castOther = (ChannelPlatform)other;
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
