package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CONTENT_EXTENSION database table.
 * 
 * @author BEA Workshop
 */
public class ContentExtension  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String externalId;
//	private java.sql.Timestamp creationDate;
	private long price;
//	private java.sql.Timestamp updateDate;
	private Content content;
	private java.util.Set<RelContentTechnical> relContentTechnicals;

    public ContentExtension() {
    }

	public String getExternalId() {
		return this.externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public long getPrice() {
		return this.price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to Content
	public Content getContent() {
		return this.content;
	}
	public void setContent(Content content) {
		this.content = content;
	}

	//bi-directional many-to-one association to RelContentTechnical
	public java.util.Set<RelContentTechnical> getRelContentTechnicals() {
		return this.relContentTechnicals;
	}
	public void setRelContentTechnicals(java.util.Set<RelContentTechnical> relContentTechnicals) {
		this.relContentTechnicals = relContentTechnicals;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContentExtension)) {
			return false;
		}
		ContentExtension castOther = (ContentExtension)other;
		return new EqualsBuilder()
			.append(this.getExternalId(), castOther.getExternalId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getExternalId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("externalId", getExternalId())
			.toString();
	}
}