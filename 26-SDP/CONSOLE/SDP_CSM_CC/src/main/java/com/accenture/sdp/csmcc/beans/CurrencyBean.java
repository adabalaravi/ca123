package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.Date;

public class CurrencyBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date creationDate;
	private Long currencyTypeId;
	private String currencyTypeName;
	
	
	public CurrencyBean() {
		super();
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}


	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}


	public String getCurrencyTypeName() {
		return currencyTypeName;
	}


	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}
	
	
	

}
