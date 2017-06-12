package com.accenture.sdp.csmcc.common.utils;

import java.util.List;

import com.accenture.sdp.csmcc.beans.VoucherInfo;

public class VoucherResponse {

	private Long voucherAvailableTotal;
	private Long voucherTotal;
	private List<VoucherInfo> vouchers;
	
	public Long getVoucherAvailableTotal() {
		return voucherAvailableTotal;
	}
	public void setVoucherAvailableTotal(Long voucherAvailableTotal) {
		this.voucherAvailableTotal = voucherAvailableTotal;
	}
	public Long getVoucherTotal() {
		return voucherTotal;
	}
	public void setVoucherTotal(Long voucherTotal) {
		this.voucherTotal = voucherTotal;
	}
	public List<VoucherInfo> getVouchers() {
		return vouchers;
	}
	public void setVouchers(List<VoucherInfo> vouchers) {
		this.vouchers = vouchers;
	}
	public VoucherResponse(Long voucherAvailableTotal, Long voucherTotal,
			List<VoucherInfo> vouchers) {
		super();
		this.voucherAvailableTotal = voucherAvailableTotal;
		this.voucherTotal = voucherTotal;
		this.vouchers = vouchers;
	}
	
	
	

}
