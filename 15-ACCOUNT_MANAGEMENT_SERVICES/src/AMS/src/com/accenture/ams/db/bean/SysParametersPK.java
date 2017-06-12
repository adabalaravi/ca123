package com.accenture.ams.db.bean;

import java.io.Serializable;

public class SysParametersPK implements Serializable{
	private long paramId=0;
	private String paramGroup="";
	private String paramName="";
	public long getParamId() {
		return paramId;
	}
	public void setParamId(long paramId) {
		this.paramId = paramId;
	}
	public String getParamGroup() {
		return paramGroup;
	}
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	
}
