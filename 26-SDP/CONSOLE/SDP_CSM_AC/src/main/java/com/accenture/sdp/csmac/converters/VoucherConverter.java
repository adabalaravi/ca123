package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.VoucherBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherCampaignInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherInfoResp;

public abstract class VoucherConverter {

	public static List<VoucherBean> convertVouchers(List<VoucherInfoResp> infos) {
		List<VoucherBean> beanList = new ArrayList<VoucherBean>();
		for (VoucherInfoResp info : infos) {
			beanList.add(convertVoucher(info));
		}
		return beanList;
	}

	public static VoucherBean convertVoucher(VoucherInfoResp info) {
		VoucherBean bean = new VoucherBean();
		bean.setPartyId(info.getPartyId());
		bean.setSolutionOfferId(info.getSolutionOfferId());
		bean.setValidityPeriod(info.getValidityPeriod());
		bean.setVoucherCode(info.getVoucherCode());
		bean.setVoucherId(info.getVoucherId());
		bean.setVoucherType(info.getVoucherType());
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		return bean;
	}

	public static List<VoucherBean> convertVoucherTypes(List<VoucherCampaignInfoResp> infos) {
		List<VoucherBean> beanList = new ArrayList<VoucherBean>();
		for (VoucherCampaignInfoResp info : infos) {
			beanList.add(convertVoucherType(info));
		}
		return beanList;
	}

	public static VoucherBean convertVoucherType(VoucherCampaignInfoResp info) {
		VoucherBean bean = new VoucherBean();
		bean.setEndDate(Utilities.getDateFromGregorianCalendar(info.getEndDate()));
		bean.setStartDate(Utilities.getDateFromGregorianCalendar(info.getStartDate()));
		bean.setValidityPeriod(info.getValidityPeriod());
		bean.setVoucherType(info.getVoucherType());
		return bean;
	}
}
