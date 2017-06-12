package com.accenture.sdp.csmfe.webservices.request.voucher;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchVouchersByVoucherCodeRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8651843589727995890L;

	private String voucherCode;
	private Long startPosition;
	private Long maxRecordsNumber;

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Long getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Long startPosition) {
		this.startPosition = startPosition;
	}

	public Long getMaxRecordsNumber() {
		return maxRecordsNumber;
	}

	public void setMaxRecordsNumber(Long maxRecordsNumber) {
		this.maxRecordsNumber = maxRecordsNumber;
	}

}
