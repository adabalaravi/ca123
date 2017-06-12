package com.accenture.ams.db.dao;

import java.util.List;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.bean.PaymentType;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class SdpPartyProfileDAO {

	/**
	 * 
	 * @param userId
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static List<AccountAttribute> getAccountAttributesBean(Long userId, String tenantName) throws Exception {
		Session session = null;
		List<AccountAttribute> ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String query = "Select c from AccountAttribute c where c.userId = "	+ userId;
			ret = (List<AccountAttribute>) session.createQuery(query).list();

			LogUtil.writeInfoDaoLog(SdpPartyProfileDAO.class,
					"getAccountAttributesBean", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountAttributesBean ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving AccountAttribute for userId:'" + userId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(SdpPartyProfileDAO.class,"getAccountAttributesBean", startTime);
		return ret;
	}
	
	/**
	 * 
	 * @param userId
	 * @param crmAccountId
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> getCrmAccountUser(Long userId, String crmAccountId, String tenantName) throws Exception {
		Session session = null;
		List<Object[]> listData = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getInstance().getSessionFactory(tenantName).openSession();
			String query = "select crma.vip, au.bloccoAcquisti, au.purchaseThresholdBlocking, au.purchaseValue, au.consumption, au.purchaseAlerting, au.consumptionThreshold "
					+ "from AccountUser au, CrmAccount crma "
					+ "where crma.username = au.username and crma.crmaccountid=au.crmaccountid "
					+ "and au.username = " + userId + " and au.crmaccountid = '" + crmAccountId + "'"; 
			
			
			listData = (List<Object[]>) session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(SdpPartyProfileDAO.class,
					"getCrmAccountUser", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getCrmAccountUser ", e);
			HibernateUtil.getInstance().getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving CrmAccount - AccountUser for userId: "
					+ userId + " and crmAccountId: '" + crmAccountId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(SdpPartyProfileDAO.class, "getCrmAccountUser", startTime);
		return listData;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static List<PaymentType> getPaymentTypeBean(String tenantName) throws Exception {
		Session session = null;
		List<PaymentType> ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String query = "Select c from PaymentType c";
			ret = (List<PaymentType>) session.createQuery(query).list();

			LogUtil.writeInfoDaoLog(SdpPartyProfileDAO.class,
					"getPaymentTypeBean", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getPaymentTypeBean ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving PaymentType");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(SdpPartyProfileDAO.class,"getPaymentTypeBean", startTime);
		return ret;
	}
	
	
	
	/**
	 * 
	 * @param userId
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static boolean isUserIdPresentAccountUser(Long userId, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String query = "Select au from AccountUser au where au.username = " + userId;
			AccountUser accountSelected = (AccountUser) session.createQuery(query).uniqueResult();

			Long userName = null;
			if (accountSelected != null)
				userName = accountSelected.getUsername();

			LogUtil.writeInfoDaoLog(SdpPartyProfileDAO.class, "isUserIdPresentAccountUser", query);

			if (userName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDAO.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isUserIdPresentAccountUser ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving userId:" + userId);
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(SdpPartyProfileDAO.class, "isUserIdPresentAccountUser", startTime);
		return ret;
	}

	/**
	 * 
	 * @param crmAccountId
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static boolean isCrmAccountIdPresentCrmAccount(String crmAccountId, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String query = "Select c from CrmAccount c where c.crmaccountid = '" + crmAccountId + "'";
			CrmAccount crmAccountRes = (CrmAccount) session.createQuery(query).uniqueResult();

			String dbCrmAccountId = null;
			if (crmAccountRes != null)
				dbCrmAccountId = crmAccountRes.getCrmaccountid();

			LogUtil.writeInfoDaoLog(SdpPartyProfileDAO.class, "isCrmAccountIdPresentCrmAccount", query);

			if (dbCrmAccountId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDAO.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isCrmAccountIdPresentCrmAccount ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving crmAccountId:'" + crmAccountId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(SdpPartyProfileDAO.class, "isCrmAccountIdPresentCrmAccount", startTime);
		return ret;
	}
	
	
	
}
