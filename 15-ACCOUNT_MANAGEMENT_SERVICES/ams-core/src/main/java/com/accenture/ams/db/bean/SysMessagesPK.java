package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SysMessagesPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String messageKey;
	private String language;
	
	public SysMessagesPK() {
    
    }
	
	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
    public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SysMessagesPK)) {
			return false;
		}
		SysMessagesPK castOther = (SysMessagesPK)other;
		return new EqualsBuilder()
			.append(this.getLanguage(), castOther.getLanguage())
			.append(this.getMessageKey(), castOther.getMessageKey())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMessageKey())
			.append(getLanguage())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("language", getLanguage())
			.append("messagekey", getMessageKey())
			.toString();
	}
}