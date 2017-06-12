package com.accenture.sdp.csmac.business;

import java.util.List;

import com.accenture.sdp.csmac.beans.PackageBean;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.converters.PackageConverter;
import com.accenture.sdp.csmac.services.PackageService;
import com.accenture.sdp.csmfe.webservices.clients.packages.SearchPackageComplexRespPaginated;

public final class PackageBusiness {

	private PackageBusiness() {
	}

	public static List<PackageBean> searchPackagesBySolutionOfferName(String solutionOfferName) throws ServiceErrorException {
		PackageService service = Utilities.findBean(ApplicationConstants.PACKAGE_SERVICE_BEAN, PackageService.class);
		SearchPackageComplexRespPaginated resp = service.searchPackagesBySolutionOffer(solutionOfferName, Utilities.getTenantName());
		return PackageConverter.convertPackages(resp.getPackages().getPackage());
	}

}
