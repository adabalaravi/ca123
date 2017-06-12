package com.accenture.sdp.csmcc.common.beans;

import java.io.Serializable;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class BreadCrumbItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String link;
	
	public BreadCrumbItem() {
		super();
	}
	
	

	public BreadCrumbItem(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}
	public BreadCrumbItem(String name) {
		super();
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	public String getTitle() {		
		return Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, name);	
	}
	public String getDefaultTitle(){
		return Utilities.getDefaultMessage(ApplicationConstants.MESSAGE_BUNDLE, name);
	}
	
}
