package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.util.Date;

public class LivePPVPK implements Serializable{
	private Long liveContentId;
	private Long packageId;
	private Long channelId;
	private Date playlistPublishedDate;

	public LivePPVPK() {
		
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


	public Long getLiveContentId() {
		return liveContentId;
	}
	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	
}
