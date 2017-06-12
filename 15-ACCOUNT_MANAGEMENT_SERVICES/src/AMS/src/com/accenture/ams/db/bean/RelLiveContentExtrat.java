package com.accenture.ams.db.bean;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the REL_LIVE_CONTENT_EXTRAT database table.
 * 
 * @author BEA Workshop
 */
public class RelLiveContentExtrat  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private RelLiveContentExtratPK compId;
	//private LiveContent liveContent;
	private PcExtendedRating pcExtendedRating;

    public RelLiveContentExtrat() {
    }

	public RelLiveContentExtratPK getCompId() {
		return this.compId;
	}
	public void setCompId(RelLiveContentExtratPK compId) {
		this.compId = compId;
	}


	/*
	public LiveContent getLiveContent() {
		return this.liveContent;
	}
	public void setLiveContent(LiveContent liveContent) {
		this.liveContent = liveContent;
	}
*/
	//bi-directional many-to-one association to PcExtendedRating
	public PcExtendedRating getPcExtendedRating() {
		return this.pcExtendedRating;
	}
	public void setPcExtendedRating(PcExtendedRating pcExtendedRating) {
		this.pcExtendedRating = pcExtendedRating;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelLiveContentExtrat)) {
			return false;
		}
		RelLiveContentExtrat castOther = (RelLiveContentExtrat)other;
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