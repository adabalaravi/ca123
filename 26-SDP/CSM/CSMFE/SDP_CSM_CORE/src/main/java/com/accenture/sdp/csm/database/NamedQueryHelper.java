package com.accenture.sdp.csm.database;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public abstract class NamedQueryHelper {

	private static LoggerWrapper log = new LoggerWrapper(NamedQueryHelper.class);
	private static final String LOG_STRING = "Query : %s \nExecuted in %s ms";

	private static void setParams(Query query, Map<String, Object> parametersMap) {
		if (parametersMap != null) {
			for (Entry<String, Object> entry : parametersMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	public static <T> T executeNamedQuerySingle(Class<T> clazz, String namedQueryName, Map<String, Object> parametersMap, EntityManager em) {
		Query query = em.createNamedQuery(namedQueryName);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();

		T result;
		try {
			result = (T) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		log.logDebug(LOG_STRING, namedQueryName, System.currentTimeMillis() - startTime);

		return result;
	}

	public static <T> List<T> executeNamedQuery(Class<T> clazz, String namedQueryName, Map<String, Object> parametersMap, EntityManager em) {
		// compatibile solo jpa2
		// TypedQuery<T> query = em.createNamedQuery(namedQueryName, clazz)
		Query query = em.createNamedQuery(namedQueryName);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();
		List<T> results = query.getResultList();
		log.logDebug(LOG_STRING, namedQueryName, System.currentTimeMillis() - startTime);

		if (results == null || results.isEmpty()) {
			return null;
		}
		return results;
	}

	public static <T> List<T> executePaginatedNamedQuery(Class<T> clazz, String namedQueryName, Map<String, Object> parametersMap, Long startPosition,
			Long maxResultsNumber, EntityManager em) {
		Query query = em.createNamedQuery(namedQueryName);
		setParams(query, parametersMap);

		query.setFirstResult(startPosition.intValue());
		// required to emulate toplink on hibernate
		// maxResults 0 means every, while it's nothing on hibernate
		if (maxResultsNumber > 0L) {
			query.setMaxResults(maxResultsNumber.intValue());
		}

		long startTime = System.currentTimeMillis();
		List<T> results = query.getResultList();
		log.logDebug(LOG_STRING, namedQueryName, System.currentTimeMillis() - startTime);

		if (results == null || results.isEmpty()) {
			return null;
		}
		return results;
	}

	public static Long executeNamedQueryCount(String namedQueryName, Map<String, Object> parametersMap, EntityManager em) {
		Query query = em.createNamedQuery(namedQueryName);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();
		Long result;
		try {
			result = (Long) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		log.logDebug(LOG_STRING, namedQueryName, System.currentTimeMillis() - startTime);

		return result;
	}

	public static <T> List<T> executeQueryString(Class<T> clazz, String queryString, Map<String, Object> parametersMap, EntityManager em) {
		Query query = em.createQuery(queryString);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();
		List<T> results = query.getResultList();
		log.logDebug(LOG_STRING, queryString, System.currentTimeMillis() - startTime);

		if (results == null || results.isEmpty()) {
			return null;
		}
		return results;
	}

	public static <T> List<T> executePaginatedQueryString(Class<T> clazz, String queryString, Map<String, Object> parametersMap, Long startPosition,
			Long maxResultsNumber, EntityManager em) {
		Query query = em.createQuery(queryString);
		setParams(query, parametersMap);

		query.setFirstResult(startPosition.intValue());
		// required to emulate toplink on hibernate
		// maxResults 0 means every, while it's nothing on hibernate
		if (maxResultsNumber > 0L) {
			query.setMaxResults(maxResultsNumber.intValue());
		}

		long startTime = System.currentTimeMillis();
		List<T> results = query.getResultList();
		log.logDebug(LOG_STRING, queryString, System.currentTimeMillis() - startTime);

		if (results == null || results.isEmpty()) {
			return null;
		}
		return results;
	}

	public static Long executeQueryStringCount(String queryString, Map<String, Object> parametersMap, EntityManager em) {
		Query query = em.createQuery(queryString);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();
		Long result;
		try {
			result = (Long) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		log.logDebug(LOG_STRING, queryString, System.currentTimeMillis() - startTime);

		return result;
	}

	public static int executeNamedQueryUpdate(String namedQueryName, Map<String, Object> parametersMap, EntityManager em) {
		Query query = em.createNamedQuery(namedQueryName);
		setParams(query, parametersMap);

		long startTime = System.currentTimeMillis();
		int result = query.executeUpdate();
		log.logDebug(LOG_STRING, namedQueryName, System.currentTimeMillis() - startTime);

		return result;
	}
}
