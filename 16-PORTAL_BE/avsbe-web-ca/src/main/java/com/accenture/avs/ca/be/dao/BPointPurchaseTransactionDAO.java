package com.accenture.avs.ca.be.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.core.dao.PurchaseDAO;
import com.accenture.avs.be.db.bean.PurchaseTransaction;
import com.accenture.avs.be.db.dao.PurchaseTransactionDAO;
import com.accenture.avs.be.db.framework.ConstantsParameter;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;

/**
 * @author siril.babu.nalluri
 *
 */
public class BPointPurchaseTransactionDAO {
	private static ConstantsParameter constantsParameter = null;
	private static SystemMessages systemMessages = null;
	
	public static void deleteTransactionRecord(PurchaseDAO purchaseDAO, TenantConfigurator tenantConfigurator,String sequenceId) throws Exception {
		if (constantsParameter == null || systemMessages == null) {
			constantsParameter = tenantConfigurator.getConstantsParameter();
			systemMessages = tenantConfigurator.getSystemMessages();
		}
		long startTime = System.currentTimeMillis();
		Session session = null;
		String tenantId = tenantConfigurator.getTenantId();
		PurchaseTransaction purchaseTransaction = null;
		try {
			session = HibernateUtil.getSessionFactory(tenantId).openSession();
			session.getTransaction().begin();
			purchaseTransaction = PurchaseTransactionDAO.getPurchaseTransactionBySeqId(Long.valueOf(sequenceId),tenantConfigurator);
			session.delete(purchaseTransaction);
			session.getTransaction().commit();
			HibernateUtil.getSessionFactory(tenantId).close();
			
			LogUtil.writeInfoDaoLog(BPointPurchaseTransactionDAO.class, "Delete PurchaseTransaction", " ");
			LogUtil.writeInfoEndDaoLog(BPointPurchaseTransactionDAO.class, "Detele PurchaseTransaction", startTime);

		} catch (Exception e) {
			if (session != null){
				session.getTransaction().rollback();
			}
			HibernateUtil.getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(BPointPurchaseTransactionDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " Delete PurchaseTransaction", e);
			throw e;
		}

	}
	/**
	 * 
	 * @param transactionID
	 * @param tenantConfigurator
	 * @return
	 * @throws Exception
	 * 
	 * 
	 * //2nd Season - New CR - Auto Renewal of App packages.
	 * fetching the transaction details using the transactionID
	 */
	public static PurchaseTransaction getPurchaseTransactionByTransactionID(String transactionID, TenantConfigurator tenantConfigurator) throws Exception {

			       SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
			       Session session = null;
			       PurchaseTransaction returnObj = null;
			       String tenantId = tenantConfigurator.getTenantId();
			       try {
			         String transQuery = "select pt from PurchaseTransaction pt where pt.transactionId='"+transactionID+"' and pt.parentItemId is null";
			  
			         session = HibernateUtil.getSessionFactory(tenantId).openSession();
			         LogUtil.writeInfoDaoLog(BPointPurchaseTransactionDAO.class, "getPurchaseTransactionByTransactionID", transQuery);
			   
			         Query query = session.createQuery(transQuery);
			   
			         returnObj = (PurchaseTransaction)query.uniqueResult();
			         if (returnObj != null) {
			           return returnObj;
			         }
			         HibernateUtil.getSessionFactory(tenantId).close();
			       }
			       catch (Exception e) {
			         HibernateUtil.getSessionFactory(tenantId).close();
			         LogUtil.writeErrorLog(BPointPurchaseTransactionDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getPurchaseTransactionByTransactionID", e);
			         throw e;
			       }
		   
		       return returnObj;
		     }

	
	/**
	 * @param transactionID
	 * @param tenantConfigurator
	 * @return
	 * @throws Exception
	 * 
	 * Added as part of BT Integration.
	 */
	public static PurchaseTransaction getPurchaseTransactionByTransactionIDForBT(String transactionID, TenantConfigurator tenantConfigurator) throws Exception {

	       SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
	       Session session = null;
	       PurchaseTransaction returnObj = null;
	       String tenantId = tenantConfigurator.getTenantId();
	       try {
	         String transQuery = "select pt from PurchaseTransaction pt where pt.transactionId='"+transactionID+"' and pt.parentItemId is null and pt.endDate > CURRENT_TIMESTAMP";
	  
	         session = HibernateUtil.getSessionFactory(tenantId).openSession();
	         LogUtil.writeInfoDaoLog(BPointPurchaseTransactionDAO.class, "getPurchaseTransactionByTransactionIDForBT", transQuery);
	   
	         Query query = session.createQuery(transQuery);
	   
	         returnObj = (PurchaseTransaction)query.uniqueResult();
	         if (returnObj != null) {
	           return returnObj;
	         }
	         HibernateUtil.getSessionFactory(tenantId).close();
	       }
	       catch (Exception e) {
	         HibernateUtil.getSessionFactory(tenantId).close();
	         LogUtil.writeErrorLog(BPointPurchaseTransactionDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getPurchaseTransactionByTransactionIDForBT", e);
	         throw e;
	       }

    return returnObj;
  }
}
