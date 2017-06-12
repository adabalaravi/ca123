package com.accenture.sdp.csmfe.webservices.response.voucher;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchVoucherResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3519588581900619252L;

	private VoucherInfoResp voucher;

	public VoucherInfoResp getVoucher() {
		return voucher;
	}

	public void setVoucher(VoucherInfoResp voucher) {
		this.voucher = voucher;
	}
}
