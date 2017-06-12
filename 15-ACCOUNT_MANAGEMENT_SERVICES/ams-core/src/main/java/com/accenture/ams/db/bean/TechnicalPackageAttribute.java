package com.accenture.ams.db.bean;

public class TechnicalPackageAttribute {
	
	private Long id;
	private Long packageId;
	private Long attributeDetailId;
	private String attributeValue;
	
	public TechnicalPackageAttribute(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getAttributeDetailId() {
		return attributeDetailId;
	}

	public void setAttributeDetailId(Long attributeDetailId) {
		this.attributeDetailId = attributeDetailId;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
}
