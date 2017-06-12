package com.accenture.ams.db.bean;

import java.io.Serializable;

public class SysParameters implements Serializable{
	private SysParametersPK compId=null;
	private String paramValue="";
	private String paramLabel="";
	private String paramPlatform="";
	private long paramOrder=0;
	private String paramDescription="";
	
	
	public SysParametersPK getCompId() {
		return compId;
	}
	public void setCompId(SysParametersPK compId) {
		this.compId = compId;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamLabel() {
		return paramLabel;
	}
	public void setParamLabel(String paramLabel) {
		this.paramLabel = paramLabel;
	}
	public String getParamPlatform() {
		return paramPlatform;
	}
	public void setParamPlatform(String paramPlatform) {
		this.paramPlatform = paramPlatform;
	}
	public long getParamOrder() {
		return paramOrder;
	}
	public void setParamOrder(long paramOrder) {
		this.paramOrder = paramOrder;
	}
	public String getParamDescription() {
		return paramDescription;
	}
	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
	}
	
	
	
}
