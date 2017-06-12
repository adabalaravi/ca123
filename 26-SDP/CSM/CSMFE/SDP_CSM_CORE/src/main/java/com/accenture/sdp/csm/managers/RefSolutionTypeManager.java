package com.accenture.sdp.csm.managers;

import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefSolutionTypeHelper;
import com.accenture.sdp.csm.model.jpa.RefSolutionType;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class RefSolutionTypeManager extends SdpBaseManager {

	private RefSolutionTypeManager() {
		super();
	}

	private static RefSolutionTypeManager instance;

	public static RefSolutionTypeManager getInstance() {
		if (instance == null) {
			instance = new RefSolutionTypeManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method returns the information on all Solution Types in the catalog.
	 * </p>
	 * 
	 * @return Returns a SearchServiceResponse containing all the information related to all Solution Types
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllSolutionTypes(String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			List<RefSolutionType> types = RefSolutionTypeHelper.getInstance().searchAllSolutionTypes(em);
			if (types == null || types.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertRefSolutionTypeResponseDto(types));
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
