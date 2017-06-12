package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpVoucherDto;
import com.accenture.sdp.csmfe.webservices.response.voucher.VoucherCampaignInfoResp;
import com.accenture.sdp.csmfe.webservices.response.voucher.VoucherInfoResp;

public class VoucherConverter extends BaseBeanConverter {

	public static VoucherInfoResp convertVoucher(SdpVoucherDto dto) {
		if (dto == null) {
			return null;
		}
		VoucherInfoResp info = new VoucherInfoResp();
		convertBaseInfo(dto, info);
		info.setPartyId(dto.getPartyId());
		info.setSolutionOfferId(dto.getSolutionOfferId());
		info.setValidityPeriod(dto.getValidityPeriod());
		info.setVoucherId(dto.getVoucherId());
		info.setVoucherCode(dto.getVoucherCode());
		info.setVoucherType(dto.getVoucherType());
		info.setStartDate(dto.getStartDate());
		info.setEndDate(dto.getEndDate());
		return info;
	}

	public static List<VoucherInfoResp> convertVouchers(List<SdpVoucherDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<VoucherInfoResp> infos = new ArrayList<VoucherInfoResp>();
		for (SdpVoucherDto s : dtos) {
			infos.add(convertVoucher(s));
		}
		return infos;
	}
	
	
	
	public static VoucherCampaignInfoResp convertVoucherTypes(SdpVoucherDto dto) {
		if (dto == null) {
			return null;
		}
		VoucherCampaignInfoResp info = new VoucherCampaignInfoResp();
		convertBaseInfo(dto, info);
		info.setVoucherType(dto.getVoucherType());
		info.setValidityPeriod(dto.getValidityPeriod());
		info.setStartDate(dto.getStartDate());
		info.setEndDate(dto.getEndDate());
		return info;
	}

	public static List<VoucherCampaignInfoResp> convertVoucherTypes(List<SdpVoucherDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<VoucherCampaignInfoResp> infos = new ArrayList<VoucherCampaignInfoResp>();
		for (SdpVoucherDto s : dtos) {
			infos.add(convertVoucherTypes(s));
		}
		return infos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
