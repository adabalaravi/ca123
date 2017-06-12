package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the FAVOURITE database table.
 * 
 * @author BEA Workshop
 */
public class FavouritePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long contentId;
	private String rowId;

    public FavouritePK() {
    }

	public Long getContentId() {
		return this.contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getRowId() {
		return this.rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FavouritePK)) {
			return false;
		}
		FavouritePK castOther = (FavouritePK)other;
		return new EqualsBuilder()
			.append(this.getContentId(), castOther.getContentId())
			.append(this.getRowId(), castOther.getRowId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getContentId())
			.append(getRowId())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("contentId", getContentId())
			.append("rowId", getRowId())
			.toString();
	}
}