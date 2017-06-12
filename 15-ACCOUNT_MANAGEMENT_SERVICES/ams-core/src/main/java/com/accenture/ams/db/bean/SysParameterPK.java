package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the SYS_PARAMETERS database table.
 * 
 * @author BEA Workshop
 */
public class SysParameterPK implements Serializable {
	//default serial version id, required for serializable classes.
	
	private static final long serialVersionUID = 1L;
	private String paramGroup;
	private Long paramId;
	private String paramName;

    public SysParameterPK() {
    }

	public String getParamGroup() {
		return this.paramGroup;
	}
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}

	public Long getParamId() {
		return this.paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return this.paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SysParameterPK)) {
			return false;
		}
		SysParameterPK castOther = (SysParameterPK)other;
		return new EqualsBuilder()
			.append(this.getParamGroup(), castOther.getParamGroup())
			.append(this.getParamId(), castOther.getParamId())
			.append(this.getParamName(), castOther.getParamName())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getParamGroup())
			.append(getParamId())
			.append(getParamName())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("paramGroup", getParamGroup())
			.append("paramId", getParamId())
			.append("paramName", getParamName())
			.toString();
	}
}