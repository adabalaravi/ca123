package com.accenture.sdp.csm.helpers;

import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.model.jpa.RefSolutionType;
import com.accenture.sdp.csm.utilities.Utilities;

public final class RefSolutionTypeHelper extends SdpBaseHelper {

	private static RefSolutionTypeHelper instance;

	private RefSolutionTypeHelper() {
		super();
	}

	public static RefSolutionTypeHelper getInstance() {
		if (instance == null) {
			instance = new RefSolutionTypeHelper();
		}
		return instance;
	}

	public RefSolutionType searchSolutionTypeById(Long solutionTypeId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (solutionTypeId == null) {
			return null;
		}
		return em.find(RefSolutionType.class, solutionTypeId);
	}

	public List<RefSolutionType> searchAllSolutionTypes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefSolutionType.class, RefSolutionType.SOLUTION_TYPE_RETRIEVE_ALL, null, em);
	}
}
