package com.accenture.sdp.csm.managers;

import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefServiceTypeHelper;
import com.accenture.sdp.csm.model.jpa.RefServiceType;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class RefServiceTypeManager extends SdpBaseManager {

	private RefServiceTypeManager() {
		super();
	}

	private static RefServiceTypeManager instance;

	public static RefServiceTypeManager getInstance() {
		if (instance == null) {
			instance = new RefServiceTypeManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method returns the information on all Service Types in the catalog.
	 * </p>
	 * 
	 * @return Returns a SearchServiceResponse containing all the information related to all Service Types
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllServiceTypes(String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		EntityManager em = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			List<RefServiceType> types = RefServiceTypeHelper.getInstance().searchAllServiceTypes(em);
			if (types == null || types.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertRefServiceTypeResponseDto(types));
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