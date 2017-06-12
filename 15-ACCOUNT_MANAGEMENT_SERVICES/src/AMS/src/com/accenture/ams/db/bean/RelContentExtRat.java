package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the REL_CONTENT_EXT_RAT database table.
 * 
 * @author BEA Workshop
 */
public class RelContentExtRat  implements Serializable {
	public RelContentExtRat(RelContentExtRatPK compId, 
			PcExtendedRating pcExtendedRating) {
		super();
		this.compId = compId;
//		this.content = content;
		this.pcExtendedRating = pcExtendedRating;
	}

	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private RelContentExtRatPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
//	private Content content;
	private PcExtendedRating pcExtendedRating;

    public RelContentExtRat() {
    }

	public RelContentExtRatPK getCompId() {
		return this.compId;
	}
	public void setCompId(RelContentExtRatPK compId) {
		this.compId = compId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to Content
//	public Content getContent() {
//		return this.content;
//	}
//	public void setContent(Content content) {
//		this.content = content;
//	}

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
		if (!(other instanceof RelContentExtRat)) {
			return false;
		}
		RelContentExtRat castOther = (RelContentExtRat)other;
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
