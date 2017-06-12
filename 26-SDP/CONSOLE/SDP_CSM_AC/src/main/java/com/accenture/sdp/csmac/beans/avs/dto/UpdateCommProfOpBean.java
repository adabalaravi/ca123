package com.accenture.sdp.csmac.beans.avs.dto;

import java.io.Serializable;
import java.util.List;


public class UpdateCommProfOpBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long crmProductId;
	private String operationType;
	private String voucherCode;
	private List<ExternalOfferBean> externalOfferList;

	public long getCrmProductId() {
		return crmProductId;
	}

	public void setCrmProductId(long crmProductId) {
		this.crmProductId = crmProductId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public List<ExternalOfferBean> getExternalOfferList() {
		return externalOfferList;
	}

	public void setExternalOfferList(List<ExternalOfferBean> externalOfferList) {
		this.externalOfferList = externalOfferList;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

}
