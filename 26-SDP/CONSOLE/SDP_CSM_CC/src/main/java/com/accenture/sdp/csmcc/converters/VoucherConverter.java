package com.accenture.sdp.csmcc.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.VoucherCampaignBean;
import com.accenture.sdp.csmcc.beans.VoucherInfo;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherCampaignInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherInfoResp;

public final class VoucherConverter {
	
	private VoucherConverter(){
		
	}

	public static List<VoucherInfo> convertVouchers(List<VoucherInfoResp> infos) {
		List<VoucherInfo> beanList = new ArrayList<VoucherInfo>();
		for (VoucherInfoResp info : infos) {
			beanList.add(convertVoucher(info));
		}
		return beanList;
	}

	public static VoucherInfo convertVoucher(VoucherInfoResp info) {
		VoucherInfo bean = new VoucherInfo();
		bean.setPartyId(info.getPartyId());
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setValidityPeriod(info.getValidityPeriod());
		bean.setVoucherCode(info.getVoucherCode());
		bean.setVoucherId(info.getVoucherId());
		bean.setVoucherType(info.getVoucherType());
		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		return bean;
	}
	
	
	
	public static List<VoucherInfo> convertVoucherTypes(List<VoucherCampaignInfoResp> infos) {
		List<VoucherInfo> beanList = new ArrayList<VoucherInfo>();
		for (VoucherCampaignInfoResp info : infos) {
			beanList.add( convertVoucherType(info));
		}
		return beanList;
	}
	
	
	public static VoucherInfo convertVoucherType(VoucherCampaignInfoResp info) {
		VoucherInfo bean = new VoucherInfo();
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		bean.setValidityPeriod(info.getValidityPeriod());
		bean.setVoucherType(info.getVoucherType());		
		return bean;
	}
}
