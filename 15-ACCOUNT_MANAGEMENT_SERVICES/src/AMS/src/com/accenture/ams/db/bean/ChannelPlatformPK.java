package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CHANNEL_PLATFORM database table.
 * 
 * @author BEA Workshop
 */
public class ChannelPlatformPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long channelId;
	private String platformName;

    public ChannelPlatformPK() {
    }

	public Long getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getPlatformName() {
		return this.platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChannelPlatformPK)) {
			return false;
		}
		ChannelPlatformPK castOther = (ChannelPlatformPK)other;
		return new EqualsBuilder()
			.append(this.getChannelId(), castOther.getChannelId())
			.append(this.getPlatformName(), castOther.getPlatformName())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getChannelId())
			.append(getPlatformName())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("channelId", getChannelId())
			.append("platformName", getPlatformName())
			.toString();
	}
}