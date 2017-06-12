package com.accenture.sdp.csmfe.webservices.response.voucher;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchVoucherTypeResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3519588581900619252L;

	private VoucherCampaignListResp voucherTypeList;
	
	public SearchVoucherTypeResp() {
		this.voucherTypeList = new VoucherCampaignListResp();
	}

	public VoucherCampaignListResp getVoucherTypeList() {
		return voucherTypeList;
	}

	public void setVoucherTypeList(VoucherCampaignListResp voucherTypeList) {
		this.voucherTypeList = voucherTypeList;
	}

	
}
