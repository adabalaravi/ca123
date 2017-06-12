package com.accenture.sdp.csmac.common.beans;


public class ProfileParam {

	private String name;
	private String value;
	private int index;
	private boolean selected;
	
	public ProfileParam() {
		super();
	}
	
	

	public ProfileParam(String name, String value) {
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
