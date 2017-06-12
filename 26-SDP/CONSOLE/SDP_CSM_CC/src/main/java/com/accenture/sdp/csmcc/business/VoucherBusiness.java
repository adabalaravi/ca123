package com.accenture.sdp.csmcc.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.beans.VoucherCampaignBean;
import com.accenture.sdp.csmcc.beans.VoucherInfo;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.common.utils.VoucherResponse;
import com.accenture.sdp.csmcc.converters.VoucherConverter;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.VoucherService;
import com.accenture.sdp.csmfe.webservices.clients.voucher.SearchVouchersResp;
import com.accenture.sdp.csmfe.webservices.clients.voucher.VoucherCampaignInfoResp;

public class VoucherBusiness {


	public static VoucherResponse searchAvailableVouchersBySolutionOfferId(SolutionOfferBean solutionOffer) throws ServiceErrorException{
		Long voucherAvailableTotal = 0L;
		Long voucherTotal = 0L;

		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		Long maxRecords = new Long(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "paginator.rowsPerPageDefault"));
		SearchVouchersResp resp = service.searchAvailableVouchersBySolutionOfferId(solutionOffer.getSolutionOfferId(), 0L, maxRecords, Utilities.getTenantName());
		SearchVouchersResp respTotal = service.searchVouchersBySolutionOfferId(solutionOffer.getSolutionOfferId(), 0L, 1L, Utilities.getTenantName());
		voucherAvailableTotal = resp.getTotalResult();
		voucherTotal = respTotal.getTotalResult();
		List<VoucherInfo> vouchers = VoucherConverter.convertVouchers(resp.getVouchers().getVoucher());

		return new VoucherResponse(voucherAvailableTotal, voucherTotal, vouchers);
	}

	public static List<VoucherInfo> searchAvailableVoucherTypes() throws ServiceErrorException{
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		List<VoucherCampaignInfoResp> infos=service.searchAvailableVoucherTypes( Utilities.getTenantName()).getVoucherTypeList().getVoucherType();
		return VoucherConverter.convertVoucherTypes(infos);
	}


	public static void addVoucherCompaign(SolutionOfferBean solutionOffer, VoucherInfo voucher) throws DatatypeConfigurationException{

		LoggerWrapper log = new LoggerWrapper(VoucherBusiness.class);
		VoucherService service = Utilities.findBean(ApplicationConstants.VOUCHER_SERVICE_BEAN, VoucherService.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, solutionOffer.getSolutionOfferId());
		validationMap.put(ApplicationConstants.SOLUTION_OFFER_VALIDATION_NAME_FIELD, voucher.getVoucherType());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging

			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, solutionOffer.getSolutionOfferName());
			logMap.put(MessageConstants.SOLUTION_OFFER_NAME_LBL, voucher.getVoucherType());
			logMap.put(MessageConstants.SOLUTION_OFFER_DESCRIPTION_LBL, voucher.getValidityPeriod());
			log.logStartFeature(logMap);
			logMap.clear();

			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String mex;
			String code;
			Holder<String> tenantName = Utilities.getTenantName();
			try {

				service.createVoucherCampaign(solutionOffer.getSolutionOfferId(), voucher.getValidityPeriod(), voucher.getVoucherType(),voucher.getStartDate(),voucher.getEndDate(),tenantName);
				code = ApplicationConstants.CODE_OK;
				mex = String.format(
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_VOUCHER_CAMPAIGN_MESSAGE),
						voucher.getVoucherType());


				msgBean.setNextParam(PathConstants.SOLUTION_OFFER_VIEW);
			} catch (ServiceErrorException e) {
				// MANUAL ROLLBACK

				code = e.getCode();
				mex = e.getMessage();

			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code, mex);
		}


	}

}
