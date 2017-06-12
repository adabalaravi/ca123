package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpEndpointInfoDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpAsynchronousFlowsHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantOperationsHelper;
import com.accenture.sdp.csm.helpers.SdpSolutionOfferHelper;
import com.accenture.sdp.csm.model.jpa.SdpPackage;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariantOperation;
import com.accenture.sdp.csm.model.jpa.SdpSolutionOffer;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpAsynchronousFlowsManager extends SdpBaseManager {

	private SdpAsynchronousFlowsManager() {
		super();
	}

	private static SdpAsynchronousFlowsManager instance;

	public static SdpAsynchronousFlowsManager getInstance() {
		if (instance == null) {
			instance = new SdpAsynchronousFlowsManager();
		}
		return instance;
	}

	public SearchServiceResponse getEndpointsInfo(Long solutionOfferId, String operationType) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SOLUTION_OFFER_ID, solutionOfferId);
		logMap.put("operationType", operationType);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		try {
			em = PersistenceManager.getEntityManager();
			SdpSolutionOfferHelper solutionOfferHelper = SdpSolutionOfferHelper.getInstance();
			SdpServiceVariantOperationsHelper serviceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
			SdpAsynchronousFlowsHelper asynchronousFlowsHelper = SdpAsynchronousFlowsHelper.getInstance();
			asynchronousFlowsHelper.validateGetEndpointsInfo(solutionOfferId, operationType);

			SdpSolutionOffer solutionOffer = solutionOfferHelper.searchSolutionOfferById(solutionOfferId, em);

			if (solutionOffer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SOLUTION_OFFER_ID, solutionOfferId);
			}
			List<SdpPackage> packages = (List<SdpPackage>) solutionOffer.getSdpPackages();

			List<SdpServiceVariantOperation> serviceVariantOperationList = new ArrayList<SdpServiceVariantOperation>();

			for (SdpPackage sdpPackage : packages) {

				if (sdpPackage.getSdpOffer() != null && sdpPackage.getSdpOffer().getSdpServiceVariant() != null) {
					log.logDebug("OFFER ID: %s", sdpPackage.getSdpOffer().getOfferId());
					log.logDebug("OFFER NAME: %s", sdpPackage.getSdpOffer().getOfferName());

					Long currentServiceVariantId = sdpPackage.getSdpOffer().getSdpServiceVariant().getServiceVariantId();

					log.logDebug("SERVICE VARIANT ID: %s", currentServiceVariantId);

					List<SdpServiceVariantOperation> tmpList = serviceVariantOperationsHelper.searchSdpServiceVariantOperationByOperationType(
							currentServiceVariantId, operationType, em);
					if (tmpList != null && tmpList.size() > 0) {
						serviceVariantOperationList.addAll(tmpList);

					}

				}
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			List<SdpEndpointInfoDto> endpointInfoDtos = BeanConverter.convertListOfSdpEndpointInfoDto(serviceVariantOperationList);
			resp.setSearchResult(endpointInfoDtos);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;

	}
}
