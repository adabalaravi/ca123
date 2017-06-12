package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the REL_LIVE_CONTENT_EXTRAT database table.
 * 
 * @author BEA Workshop
 */
public class RelLiveContentExtratPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long channelId;
	private Long liveContentId;
	private Long pcId;
	private java.sql.Timestamp playlistPublishedDate;

    public RelLiveContentExtratPK() {
    }

	public Long getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getLiveContentId() {
		return this.liveContentId;
	}
	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}

	public Long getPcId() {
		return this.pcId;
	}
	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

	public java.sql.Timestamp getPlaylistPublishedDate() {
		return this.playlistPublishedDate;
	}
	public void setPlaylistPublishedDate(java.sql.Timestamp playlistPublishedDate) {
		this.playlistPublishedDate = playlistPublishedDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelLiveContentExtratPK)) {
			return false;
		}
		RelLiveContentExtratPK castOther = (RelLiveContentExtratPK)other;
		return new EqualsBuilder()
			.append(this.getChannelId(), castOther.getChannelId())
			.append(this.getLiveContentId(), castOther.getLiveContentId())
			.append(this.getPcId(), castOther.getPcId())
			.append(this.getPlaylistPublishedDate(), castOther.getPlaylistPublishedDate())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getChannelId())
			.append(getLiveContentId())
			.append(getPcId())
			.append(getPlaylistPublishedDate())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("channelId", getChannelId())
			.append("liveContentId", getLiveContentId())
			.append("pcId", getPcId())
			.append("playlistPublishedDate", getPlaylistPublishedDate())
			.toString();
	}
}