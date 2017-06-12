package com.accenture.sdp.csmfe.webservices.response.voucher;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchVouchersResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3519588581900619252L;

	private VoucherListResp vouchers;
	
	public SearchVouchersResp() {
		this.vouchers = new VoucherListResp();
	}

	public VoucherListResp getVouchers() {
		return vouchers;
	}

	public void setVouchers(VoucherListResp vouchers) {
		this.vouchers = vouchers;
	}

}
