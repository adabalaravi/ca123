package com.accenture.sdp.csmac.beans.avs;

import java.io.Serializable;
import java.util.Date;

public class AvsPaymentTypeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465531081650917652L;

	private Date creationDate;
	private String paymentMethod;
	private Long paymentTypeId;
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

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
