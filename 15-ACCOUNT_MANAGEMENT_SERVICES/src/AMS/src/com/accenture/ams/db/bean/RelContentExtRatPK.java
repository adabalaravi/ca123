package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the REL_CONTENT_EXT_RAT database table.
 * 
 * @author BEA Workshop
 */
public class RelContentExtRatPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long contentId;
	private Long pcId;

    public RelContentExtRatPK() {
    }

	public Long getContentId() {
		return this.contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getPcId() {
		return this.pcId;
	}
	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelContentExtRatPK)) {
			return false;
		}
		RelContentExtRatPK castOther = (RelContentExtRatPK)other;
		return new EqualsBuilder()
			.append(this.getContentId(), castOther.getContentId())
			.append(this.getPcId(), castOther.getPcId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getContentId())
			.append(getPcId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("contentId", getContentId())
			.append("pcId", getPcId())
			.toString();
	}
}