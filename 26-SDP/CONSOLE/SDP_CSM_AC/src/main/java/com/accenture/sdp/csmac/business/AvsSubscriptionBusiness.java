package com.accenture.sdp.csmac.business;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.PackageBean;
import com.accenture.sdp.csmac.beans.SolutionOfferBean;
import com.accenture.sdp.csmac.beans.VoucherBean;
import com.accenture.sdp.csmac.beans.avs.dto.ExternalOfferBean;
import com.accenture.sdp.csmac.beans.avs.dto.UpdateCommProfOpBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionBean;
import com.accenture.sdp.csmac.beans.subscription.SubscriptionDetailBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.services.AvsAmsService;

public final class AvsSubscriptionBusiness {

	private AvsSubscriptionBusiness() {
	}

	public static void createSubscription(String crmAccountId, SolutionOfferBean solutionOffer) throws ServiceErrorException {
		createSubscription(crmAccountId, solutionOffer, null);
	}

	public static void createSubscription(String crmAccountId, VoucherBean voucher) throws ServiceErrorException {
		SolutionOfferBean solutionOffer = SolutionOfferBusiness.searchSolutionOfferById(voucher.getSolutionOfferId());
		createSubscription(crmAccountId, solutionOffer, voucher.getVoucherCode());
	}

	private static void createSubscription(String crmAccountId, SolutionOfferBean solutionOffer, String voucherCode) throws ServiceErrorException {
		AvsAmsService service = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		// preparo i DTO
		List<UpdateCommProfOpBean> orders = new ArrayList<UpdateCommProfOpBean>();
		UpdateCommProfOpBean order = new UpdateCommProfOpBean();
		orders.add(order);
		order.setCrmProductId(solutionOffer.getId());
		order.setVoucherCode(voucherCode);
		List<ExternalOfferBean> rows = new ArrayList<ExternalOfferBean>();
		order.setExternalOfferList(rows);
		// carico packages della solution offer
		List<PackageBean> packages = PackageBusiness.searchPackagesBySolutionOfferName(solutionOffer.getName());
		for (PackageBean pack : packages) {
			ExternalOfferBean row = new ExternalOfferBean();
			// carico l'offer per recuperare l'id di AVS
			row.setPackageId(OfferBusiness.searchOffer(pack.getOfferId()).getExternalId());
			row.setExternalOfferId(String.valueOf(pack.getOfferId()));
			row.setPrice(pack.getNrcPrice());
			rows.add(row);
		}
		service.addCrmAccountCommercialProfile(crmAccountId, orders);
	}

	public static void deleteSubscription(String crmAccountId, SubscriptionBean subscription) throws ServiceErrorException {
		AvsAmsService service = Utilities.findBean(ApplicationConstants.AVS_AMS_SERVICE_BEAN, AvsAmsService.class);
		// preparo i DTO
		List<UpdateCommProfOpBean> orders = new ArrayList<UpdateCommProfOpBean>();
		UpdateCommProfOpBean order = new UpdateCommProfOpBean();
		orders.add(order);
		order.setCrmProductId(subscription.getSolutionOfferId());
		order.setVoucherCode(null);
		List<ExternalOfferBean> rows = new ArrayList<ExternalOfferBean>();
		order.setExternalOfferList(rows);
		// carico details della subscription
		List<SubscriptionDetailBean> details = SubscriptionBusiness.searchSubscriptionDetails(subscription.getId());
		for (SubscriptionDetailBean detail : details) {
			ExternalOfferBean row = new ExternalOfferBean();
			// carico l'offer per recuperare l'id di AVS
			row.setPackageId(OfferBusiness.searchOffer(detail.getOfferId()).getExternalId());
			row.setExternalOfferId(String.valueOf(detail.getOfferId()));
			row.setPrice(null);
			rows.add(row);
		}
		service.deleteCrmAccountCommercialProfile(crmAccountId, orders);
	}

}
