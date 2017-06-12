package com.accenture.sdp.csmfe.webservices.response.voucher;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class VoucherCampaignListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<VoucherCampaignInfoResp> voucherTypeList;

	@XmlElement(name = "voucherType")
	public List<VoucherCampaignInfoResp> getVoucherCampaignList() {
		return voucherTypeList;
	}

	public void setVoucherCampaignList(List<VoucherCampaignInfoResp> voucherTypeList) {
		this.voucherTypeList = voucherTypeList;
	}

}
