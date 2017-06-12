package com.accenture.ams.db.bean;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the SYS_PARAMETERS database table.
 * 
 * @author BEA Workshop
 */
public class SysParameter implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private SysParameterPK compId;
	private java.util.Date creationDate;
	private String paramDescription;
	private String paramLabel;
	private Long paramOrder;
	private String paramPlatform;
	private String paramValue;
	private String paramName;
	private String paramGroup;
	private String paramType;

	private java.util.Date updateDate;

    public SysParameter() {
    }

	public SysParameterPK getCompId() {
		return this.compId;
	}
	public void setCompId(SysParameterPK compId) {
		this.compId = compId;
	}

	public java.util.Date getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getParamName() {
		return this.paramName;
	}
	public void setParamName(String paramName) {
		this.paramName= paramName;
	}
	public String getParamDescription() {
		return this.paramDescription;
	}
	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
	}

	public String getParamLabel() {
		return this.paramLabel;
	}
	public void setParamLabel(String paramLabel) {
		this.paramLabel = paramLabel;
	}
	
	
	public String getParamGroup() {
		return this.compId.getParamGroup();
	}
	public void setParamGroup(String paramGroup) {
		this.compId.setParamGroup(paramGroup);
	}

	public Long getParamOrder() {
		return this.paramOrder;
	}
	public void setParamOrder(Long paramOrder) {
		this.paramOrder = paramOrder;
	}

	public String getParamPlatform() {
		return this.paramPlatform;
	}
	public void setParamPlatform(String paramPlatform) {
		this.paramPlatform = paramPlatform;
	}

	public String getParamValue() {
		return this.paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}


	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SysParameter)) {
			return false;
		}
		SysParameter castOther = (SysParameter)other;
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