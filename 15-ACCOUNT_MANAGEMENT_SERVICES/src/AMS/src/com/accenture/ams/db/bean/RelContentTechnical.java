package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the REL_CONTENT_TECHNICAL database table.
 * 
 * @author BEA Workshop
 */
public class RelContentTechnical  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private RelContentTechnicalPK compId;
//	private java.sql.Timestamp creationDate;
//	private java.sql.Timestamp updateDate;
	//private ContentExtension contentExtension;
	private TechnicalPackage technicalPackage;

    public RelContentTechnical() {
    }

	public RelContentTechnicalPK getCompId() {
		return this.compId;
	}
	public void setCompId(RelContentTechnicalPK compId) {
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

	/*//bi-directional many-to-one association to ContentExtension
	public ContentExtension getContentExtension() {
		return this.contentExtension;
	}
	public void setContentExtension(ContentExtension contentExtension) {
		this.contentExtension = contentExtension;
	}*/

	//bi-directional many-to-one association to TechnicalPackage
	public TechnicalPackage getTechnicalPackage() {
		return this.technicalPackage;
	}
	public void setTechnicalPackage(TechnicalPackage technicalPackage) {
		this.technicalPackage = technicalPackage;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelContentTechnical)) {
			return false;
		}
		RelContentTechnical castOther = (RelContentTechnical)other;
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