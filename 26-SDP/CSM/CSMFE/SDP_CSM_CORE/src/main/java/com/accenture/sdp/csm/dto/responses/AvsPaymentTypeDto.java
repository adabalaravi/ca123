package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;

public class AvsPaymentTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465531081650917652L;

	private Date creationDate;
	private String paymentMethod;
	private int paymentTypeId;
	private Date updateDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
