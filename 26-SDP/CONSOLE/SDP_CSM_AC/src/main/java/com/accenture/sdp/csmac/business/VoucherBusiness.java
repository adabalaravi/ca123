package com.accenture.sdp.csmac.business;

import java.util.Date;
import java.util.List;

import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.VoucherBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.MessageConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.VoucherConverter;
import com.accenture.sdp.csmac.services.VoucherService;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVoucherResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVouchersResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherCampaignInfoResp;

public final class VoucherBusiness {

	private VoucherBusiness() {
	}

	public static VoucherBean searchVoucherByCode(String voucherCode) throws ServiceErrorException {
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		SearchVoucherResp resp = service.searchVoucherByCode(voucherCode, Utilities.getTenantName());
		return VoucherConverter.convertVoucher(resp.getVoucher());
	}

	public static List<VoucherBean> searchVouchersByCodeLike(String voucherCode) throws ServiceErrorException {
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		SearchVouchersResp resp = service.searchVouchersByCodeLike(voucherCode, Utilities.getTenantName());
		return VoucherConverter.convertVouchers(resp.getVouchers().getVoucher());
	}

	public static void modifyVoucher(Long voucherId, Long partyId) throws ServiceErrorException {
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		service.modifyVoucher(voucherId, partyId, Utilities.getTenantName());
	}

	public static List<VoucherBean> searchVouchersBySolutionOfferId(Long solutionOfferId) throws ServiceErrorException {
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		SearchVouchersResp resp = service.searchVouchersBySolutionOfferId(solutionOfferId, 0L, 0L, Utilities.getTenantName());
		return VoucherConverter.convertVouchers(resp.getVouchers().getVoucher());
	}

	// public List<String> searchAvailableVoucherTypes() throws ServiceErrorException {
	// VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
	// SearchVoucherTypeResp resp = service.searchAvailableVoucherTypes(Utilities.getTenantName());
	// return resp.getVoucherType();
	// }

	public static List<VoucherBean> searchAvailableVoucherTypes() throws ServiceErrorException {
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		List<VoucherCampaignInfoResp> infos = service.searchAvailableVoucherTypes(Utilities.getTenantName()).getVoucherTypeList().getVoucherType();
		return VoucherConverter.convertVoucherTypes(infos);
	}

	public static boolean checkVoucher(VoucherBean voucher) throws ServiceErrorException {
		if (voucher == null) {
			return false;
		}
		if (voucher.getPartyId() != null) {
			throw new ServiceErrorException(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.VOUCHER_ERROR_ALREADY_USED_LBL));
		}
		SolutionOfferBean solOffer = voucher.getSolutionOffer();
		if (solOffer == null) {
			throw new ServiceErrorException(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.VOUCHER_ERROR_NOT_ASSOCIATED));
		}
		Date now = new Date();
		if ((solOffer.getEndDate() != null && solOffer.getEndDate().before(now)) || (voucher.getEndDate() != null && voucher.getEndDate().before(now))) {
			throw new ServiceErrorException(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.VOUCHER_ERROR_END_DATE));
		}
//		(solOffer.getStartDate() != null && solOffer.getStartDate().after(now)) || 
		if ((voucher.getStartDate() != null && voucher.getStartDate().after(now))) {
			throw new ServiceErrorException(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.VOUCHER_ERROR_START_DATE));
		}
		return true;
	}
}
