package com.accenture.sdp.csm.managers;

import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefStatusType;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;

public final class RefStatusTypeManager extends SdpBaseManager {

	private RefStatusTypeManager() {
		super();
	}

	private static RefStatusTypeManager instance;

	public static RefStatusTypeManager getInstance() {
		if (instance == null) {
			instance = new RefStatusTypeManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to return the informations on all Frequencies in the catalog.
	 * </p>
	 * 
	 * @param startPosition
	 * @param maxRecordsNumber
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to all Frequencies and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllStatus() throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		log.logStartMethod(startTime, null);
		try {
			em = PersistenceManager.getEntityManager();
			List<RefStatusType> statusList = StatusIdConverter.searchAllStatus(em);
			if (statusList == null || statusList.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertListOfStatus(statusList));
			resp.setTotalResult(Long.valueOf(statusList.size()));
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
