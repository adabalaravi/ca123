package com.accenture.sdp.csmfe.webservices.response.voucher;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class VoucherListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<VoucherInfoResp> voucherList;

	@XmlElement(name = "voucher")
	public List<VoucherInfoResp> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<VoucherInfoResp> voucherList) {
		this.voucherList = voucherList;
	}

}
