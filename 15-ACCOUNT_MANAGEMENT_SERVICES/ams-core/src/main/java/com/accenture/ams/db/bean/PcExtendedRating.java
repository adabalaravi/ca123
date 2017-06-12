package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PC_EXTENDED_RATING database table.
 * 
 * @author BEA Workshop
 */
public class PcExtendedRating  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long pcId;
//	private java.sql.Timestamp creationDate;
	private String pcDescription;
	private String pcValue;
//	private java.sql.Timestamp updateDate;
	private java.util.Set<RelContentExtRat> relContentExtRats;
	private java.util.Set<RelLiveContentExtrat> relLiveContentExtrats;

    public java.util.Set<RelLiveContentExtrat> getRelLiveContentExtrats() {
		return relLiveContentExtrats;
	}

	public void setRelLiveContentExtrats(
			java.util.Set<RelLiveContentExtrat> relLiveContentExtrats) {
		this.relLiveContentExtrats = relLiveContentExtrats;
	}

	public PcExtendedRating() {
    }

	public Long getPcId() {
		return this.pcId;
	}
	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getPcDescription() {
		return this.pcDescription;
	}
	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}

	public String getPcValue() {
		return this.pcValue;
	}
	public void setPcValue(String pcValue) {
		this.pcValue = pcValue;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to RelContentExtRat
	public java.util.Set<RelContentExtRat> getRelContentExtRats() {
		return this.relContentExtRats;
	}
	public void setRelContentExtRats(java.util.Set<RelContentExtRat> relContentExtRats) {
		this.relContentExtRats = relContentExtRats;
	}

	//bi-directional many-to-one association to RelLiveContentExtrat


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PcExtendedRating)) {
			return false;
		}
		PcExtendedRating castOther = (PcExtendedRating)other;
		return new EqualsBuilder()
			.append(this.getPcId(), castOther.getPcId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPcId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("pcId", getPcId())
			.toString();
	}
}