package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the SYS_PARAMETER_GROUP database table.
 * 
 * @author BEA Workshop
 */
public class SysParameterGroup  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long paramGroupId;
	private java.sql.Timestamp creationDate;
	private String isUpdated;
	private String paramGroup;
	private java.sql.Timestamp updateDate;

    public SysParameterGroup() {
    }

	public Long getParamGroupId() {
		return this.paramGroupId;
	}
	public void setParamGroupId(Long paramGroupId) {
		this.paramGroupId = paramGroupId;
	}

	public java.sql.Timestamp getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(java.sql.Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getIsUpdated() {
		return this.isUpdated;
	}
	public void setIsUpdated(String isUpdated) {
		this.isUpdated = isUpdated;
	}

	public String getParamGroup() {
		return this.paramGroup;
	}
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}

	public java.sql.Timestamp getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SysParameterGroup)) {
			return false;
		}
		SysParameterGroup castOther = (SysParameterGroup)other;
		return new EqualsBuilder()
			.append(this.getParamGroupId(), castOther.getParamGroupId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getParamGroupId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("paramGroupId", getParamGroupId())
			.toString();
	}
}