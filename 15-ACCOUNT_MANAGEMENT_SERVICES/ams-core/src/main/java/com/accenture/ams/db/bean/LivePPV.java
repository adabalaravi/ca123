package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class LivePPV implements Serializable{
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long ppvId;
	
	private String externalId;
	private Long liveContentId;	
	private Long channelId;
	private Date playlistPublishedDate;
	private Long packageId;
	
	//LivePPVPK compId;
	private Long price;

	public LivePPV(){}
	
	
	/*
	public LivePPVPK getCompId() {
		return compId;
	}



	public void setCompId(LivePPVPK compId) {
		this.compId = compId;
	}
	*/



	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public Long getPpvId() {
		return ppvId;
	}


	public void setPpvId(Long ppvId) {
		this.ppvId = ppvId;
	}


	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	
	
	
	public Long getLiveContentId() {
		return liveContentId;
	}


	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}


	public Long getChannelId() {
		return channelId;
	}


	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}




	public Date getPlaylistPublishedDate() {
		return playlistPublishedDate;
	}


	public void setPlaylistPublishedDate(Date playlistPublishedDate) {
		this.playlistPublishedDate = playlistPublishedDate;
	}


	public Long getPackageId() {
		return packageId;
	}


	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LivePPV)) {
			return false;
		}
		LivePPV castOther = (LivePPV)other;
		return new EqualsBuilder()
			.append(this.getPpvId(), castOther.getPpvId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPpvId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("ppvId", getPpvId())
			.toString();
	}

}
