package com.accenture.sdp.csmcc.common.beans;

import java.io.Serializable;


public class Param implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private int index;
	private boolean selected;
	
	public Param() {
		super();
	}
	
	

	public Param(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}

}
