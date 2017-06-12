package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AvsDigitalGoodDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3215254536952808632L;

	private Double id;
	private String name;
	private String description;
	private String type;
	private String externalId;
	private Date creationDate;
	private Date updateDate;

	// price is related to offer
	private BigDecimal price;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
