package com.accenture.avs.ca.be.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.bean.PurchaseTransaction;
import com.accenture.avs.be.db.framework.ConstantsParameter;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.ca.be.db.bean.SolutionOfferinclDurationDetailsBean;

/**
 * @author h.kumar.satkuri
 *
 */
public class SolutionOfferinclDurationDAO {
	
	private static ConstantsParameter constantsParameter = null;
	private static SystemMessages systemMessages = null;
	
	public SolutionOfferinclDurationDetailsBean getDurationofSolutionOffer(Long solutionOfferId, TenantConfigurator tenantConfigurator) throws Exception {
		if (constantsParameter == null || systemMessages == null) {
			constantsParameter = tenantConfigurator.getConstantsParameter();
			systemMessages = tenantConfigurator.getSystemMessages();
		}
		long startTime = System.currentTimeMillis();
		Session session = null;
		String tenantId = tenantConfigurator.getTenantId();
		SolutionOfferinclDurationDetailsBean solutionOfferinclDurationDetailsBean = null;
		List<SolutionOfferinclDurationDetailsBean> retObjects = null;
		try {
			session = HibernateUtil.getSessionFactory(tenantId).openSession();
			// session.getTransaction().begin();
			Query queryObj = null;
			queryObj = session.getNamedQuery("getDurationInfoSolutionOffer");
			queryObj.setLong(0, solutionOfferId);
			retObjects = queryObj.list();

			if(retObjects!=null && retObjects.size() > 0) {
				solutionOfferinclDurationDetailsBean = retObjects.get(0);
				LogUtil.writeInfoDaoLog(SolutionOfferinclDurationDAO.class, "getDurationofSolutionOffer Duration -- "+solutionOfferinclDurationDetailsBean.getDuration(), " ");
				LogUtil.writeInfoDaoLog(SolutionOfferinclDurationDAO.class, "getDurationofSolutionOffer SolutionOfferType -- "+solutionOfferinclDurationDetailsBean.getSolutionOfferType(), " ");
			}
			
			LogUtil.writeInfoDaoLog(SolutionOfferinclDurationDAO.class, "getDurationofSolutionOffer ", " ");
			LogUtil.writeInfoEndDaoLog(SolutionOfferinclDurationDAO.class, "getDurationofSolutionOffer ", startTime);
//			HibernateUtil.getSessionFactory(tenantId).close();
			return solutionOfferinclDurationDetailsBean;
		} catch (Exception e) {
			if (session != null){
				session.getTransaction().rollback();
			}
//			HibernateUtil.getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(SolutionOfferinclDurationDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " SolutionOfferinclDurationDAO ", e);
			throw e;
		} finally {
			HibernateUtil.getSessionFactory(tenantId).close();
		}

	}
	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	/**
	 * Method is for checking if any conditional packages available with an user. 
	 * @param userId
	 * @param conditionalPackageDetailsList
	 * @param tenantConfigurator
	 * @return
	 * @throws Exception
	 */
	public static List<PurchaseTransaction> getConditionalPackageIdsDetail(Long userId, List<String> conditionalPackageDetailsList, TenantConfigurator tenantConfigurator) throws Exception {
		// SystemMessage and TenantID Initialise
		SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
		String tenantId = tenantConfigurator.getTenantId();
		
		List<PurchaseTransaction> resultData = null;
		Session session = null;
		String methodName = "getConditionalPackageIdsDetail";
		long startTime = System.currentTimeMillis(); 
		
		try {
			
			//conditionalPackageIdsQuery for getting the conditionalPackageDetailsList
			
			 String conditionalPackageIdsQuery = "SELECT pt FROM PurchaseTransaction pt WHERE pt.user.userId= :userId "+						
						   " and (pt.endDate >= now() or pt.endDate is null)" +
						   " and pt.parentItemId is null " +
						   " and pt.itemId IN (:conditionalPackageIds)" +
						   " and pt.state.statusId IN (1,11,12)";
			
			 session = HibernateUtil.getSessionFactory(tenantId).openSession();		                   
		     
		     Query resultQuery= session.createQuery(conditionalPackageIdsQuery);
		 	 resultQuery.setLong("userId", userId); //Setting user ID
		 	 resultQuery.setParameterList("conditionalPackageIds",conditionalPackageDetailsList); //setting the conditionalPackageDetailsList
		 	 
		 	 LogUtil.writeInfoDaoLog(SolutionOfferinclDurationDAO.class, methodName,resultQuery.toString());
		 	
		     resultData = resultQuery.list();
		     
		} catch (Exception e) {
			HibernateUtil.getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(SolutionOfferinclDurationDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getConditionalPackageIdsDetail: ", e);

			throw new Exception(systemMessages.ERROR_BE_3022_DAO_QUERY + " : " + methodName + ":" + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		LogUtil.writeInfoEndDaoLog(SolutionOfferinclDurationDAO.class, methodName, startTime);
		return resultData;
	 
	  }
}// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
