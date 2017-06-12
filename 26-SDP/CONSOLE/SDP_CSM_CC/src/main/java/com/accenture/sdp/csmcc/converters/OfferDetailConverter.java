/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmcc.beans.PackageBean;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.packages.PackageComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.DiscountInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.DiscountListRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SolutionOfferDetailInfoRequest;

public final class OfferDetailConverter {
	
	private OfferDetailConverter(){
		
	}

	public static PackageBean convertPackageComplexInfoRespToOfferDetailBean(PackageComplexInfoResp info) {
		String basicType = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_OFFER_TYPE_BASIC);
		String optionalType = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_SOLUTION_OFFER_TYPE_OPTIONAL);

		PackageBean bean = new PackageBean();
		bean.setPackageId(info.getPackageId());
		bean.setOfferId(info.getOfferId());
		bean.setOfferName(info.getOfferName());
		bean.setNrcPriceCatalog(info.getNrcPrice());
		bean.setRcPriceCatalog(info.getRcPrice());
		bean.setFrequencyName(info.getFrequencyPriceName());
		// bean.setFrequencyDays(info.getF)
		if (ApplicationConstants.YES_VALUE.equalsIgnoreCase(info.getIsMandatory())) {
			bean.setType(basicType);
			bean.setBasicFlag(true);
		} else {
			bean.setType(optionalType);
			bean.setBasicFlag(false);
		}
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatusName());
		bean.setParentOfferName(info.getBaseOfferName());
		bean.setMandatory(info.getIsMandatory());
		bean.setCurrencyTypeId(info.getCurrencyPriceId());
		bean.setGroupId(info.getGroupId());
		bean.setGroupName(info.getGroupName());
		bean.setRcInAdvance(info.getInAdvance());
		bean.setPackageExternalId(info.getExternalId());
		bean.setRcFlagProrate(info.getProrate());
		bean.setPackageProfile(info.getProfile());

		bean.setBasePackageId(info.getBasePackageId());

		bean.setNrcPriceCatalogId(info.getNrcPriceId());
		bean.setRcPriceCatalogId(info.getRcPriceId());
		bean.setRcFrequencyTypeId(info.getFrequencyPriceId());
		bean.setFrequencyDays(info.getFrequencyPriceDays());
		bean.setCurrencyPriceName(info.getCurrencyPriceName());

		bean.setDiscountId(info.getDiscountId());

		bean.setNewSetupFee(Utilities.getString(info.getNrcPrice()));
		bean.setNewRecurringFee(Utilities.getString(info.getRcPrice()));
		bean.setSetupFeeDiscount(Utilities.getString(info.getDiscountAbsNrc()));
		bean.setRecurringFeeDiscount(Utilities.getString(info.getDiscountAbsRc()));
		bean.setSetupFeeDiscountPercentage(Utilities.getString(info.getDiscountPercNrc()));
		bean.setRecurringFeeDiscountPercentage(Utilities.getString(info.getDiscountPercRc()));

		return bean;
	}

	public static List<PackageBean> buildOfferDetailTable(SearchPackageComplexRespPaginated resp) {
		List<PackageBean> resultList = new ArrayList<PackageBean>();
		List<PackageComplexInfoResp> packages = resp.getPackages().getPackage();
		for (PackageComplexInfoResp info : packages) {
			resultList.add(convertPackageComplexInfoRespToOfferDetailBean(info));
		}
		return resultList;
	}

	public static SolutionOfferDetailInfoRequest convertOfferDetailBeanToInfo(PackageBean bean) {
		SolutionOfferDetailInfoRequest info = new SolutionOfferDetailInfoRequest();
		info.setCurrencyTypeId(bean.getCurrencyTypeId());
		if (bean.getGroupName() != null) {
			info.setGroupName(bean.getGroupName());
		}

		if (bean.getNrcPriceCatalogId() == null) {
			bean.setNrcPriceCatalogId(1L);
		}

		info.setNrcPriceCatalogId(bean.getNrcPriceCatalogId());

		info.setOfferId(bean.getOfferId());

		if (bean.getParentOfferId() != null && bean.getParentOfferId() > 0) {
			info.setParentOfferId(bean.getParentOfferId());
		}

		if (bean.getRcFrequencyTypeId() == null) {
			bean.setRcFrequencyTypeId(1L);
		}

		info.setRcFrequencyTypeId(bean.getRcFrequencyTypeId());

		if (bean.getRcPriceCatalogId() == null) {
			bean.setRcPriceCatalogId(1L);
		}

		info.setRcPriceCatalogId(bean.getRcPriceCatalogId());

		info.setIsMandatory(bean.getMandatory());
		if (info.getRcPriceCatalogId() == null || info.getRcPriceCatalogId().equals(1L)) {
			info.setRcFlagProrate(ApplicationConstants.NO_VALUE);
			info.setRcInAdvance(ApplicationConstants.NO_VALUE);
		} else {
			info.setRcFlagProrate(ApplicationConstants.YES_VALUE);
			info.setRcInAdvance(ApplicationConstants.YES_VALUE);
		}

		return info;
	}

	public static List<SolutionOfferDetailInfoRequest> convertOfferDetailList(List<PackageBean> offerDetails) {
		ArrayList<SolutionOfferDetailInfoRequest> result = new ArrayList<SolutionOfferDetailInfoRequest>();
		for (PackageBean offerdetail : offerDetails) {
			result.add(convertOfferDetailBeanToInfo(offerdetail));
		}
		return result;
	}


	public static DiscountInfoRequest convertOfferDetailToDiscount(PackageBean bean) {
		DiscountInfoRequest discount = new DiscountInfoRequest();
		discount.setPackageId(bean.getPackageId());
		if (!Utilities.isEmptyString(bean.getSetupFeeDiscount())) {
			discount.setDiscountAbsNrc(new BigDecimal(bean.getSetupFeeDiscount()));
		}
		if (!Utilities.isEmptyString(bean.getRecurringFeeDiscount())){
			discount.setDiscountAbsRc(new BigDecimal(bean.getRecurringFeeDiscount()));
		}
		if (!Utilities.isEmptyString(bean.getSetupFeeDiscountPercentage())){
			discount.setDiscountPercNrc(new BigDecimal(bean.getSetupFeeDiscountPercentage()));
		}
		if (!Utilities.isEmptyString(bean.getRecurringFeeDiscountPercentage())){
			discount.setDiscountPercRc(new BigDecimal(bean.getRecurringFeeDiscountPercentage()));
		}
		return discount;
	}

	public static DiscountListRequest convertOfferDetailsToDiscounts(List<PackageBean> offerDetails) {
		DiscountListRequest discountList = new DiscountListRequest();
		for (PackageBean offerdetail : offerDetails) {
			discountList.getDiscount().add(convertOfferDetailToDiscount(offerdetail));
		}
		return discountList;
	}

}
