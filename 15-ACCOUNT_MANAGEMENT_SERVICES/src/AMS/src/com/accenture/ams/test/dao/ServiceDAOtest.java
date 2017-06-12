package com.accenture.ams.test.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.StatusType;
import com.accenture.ams.db.framework.SystemMessages;
import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;
import commontypes.accountmgmt.avs.accenture.FlagTypeMF;

/**
 * This class contains methods for querying the database useful to test
 * 
 * @author Valentina Bonafaccia
 * 
 */
public class ServiceDAOtest {

	/**
	 * This method returns the number of rows in the CRM_ACCOUNT table
	 * 
	 * @return long count(*) from CRM_ACCOUNT
	 */
	public static long counter(String tenantName) {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		session = HibernateUtil.getInstance().getInstance().getSessionFactory(tenantName).openSession();
		String query = "Select count(c) from CrmAccount c";
		long count = (Long) session.createQuery(query).uniqueResult();

		return count;

	}

	/**
	 * This method returns true if field is present in crm_account table
	 * 
	 * @param String
	 *            input crmAccountID to be filtered
	 * @param String
	 *            input field to filter the query
	 * @param String
	 *            input field's value to filter the query
	 * 
	 * @return true or false
	 */
	public static boolean isFieldPresent(String crmAccountID, String field, String value, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			String query = "";
			if (field.equalsIgnoreCase("name")) {
				query = "Select c.name from CrmAccount c where c.name = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			if (field.equalsIgnoreCase("email")) {
				query = "Select c.email from CrmAccount c where c.email = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			if (field.equalsIgnoreCase("surname")) {
				query = "Select c.surname from CrmAccount c where c.surname = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			if (field.equalsIgnoreCase("mobilePhone")) {
				query = "Select c.mobilePhone from CrmAccount c where c.mobilePhone = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			if (field.equalsIgnoreCase("zipCode")) {
				query = "Select c.cap from CrmAccount c where c.cap = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			if (field.equalsIgnoreCase("birthday")) {
				query = "Select c.birthday from CrmAccount c where c.birthday = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}
			String crmAccId = (String) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(ServiceDAOtest.class, "isFieldPresent", query);

			if (crmAccId != null) {
				ret = true;
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(ServiceDAOtest.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isFieldPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving field:'" + field + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(ServiceDAOtest.class, "isFieldPresent", startTime);
		return ret;
	}

	/**
	 * This method returns true if gender flag is present in crm_account table
	 * 
	 * @param String
	 *            input crmAccountID to be filtered
	 * @param String
	 *            input field to filter the query
	 * @param String
	 *            input gender flag's value to filter the query
	 * 
	 * @return true or false
	 */
	public static boolean isFlagPresent(String crmAccountID, String field, FlagTypeMF value, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			String query = "";
			if (field.equalsIgnoreCase("sesso")) {
				query = "Select c.sesso from CrmAccount c where c.sesso = '" + value + "' and c.crmaccountid = '" + crmAccountID + "'";
			}

			String crmAccId = (String) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(ServiceDAOtest.class, "isFieldPresent", query);

			if (crmAccId != null) {
				ret = true;
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(ServiceDAOtest.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isFieldPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving field:'" + field + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(ServiceDAOtest.class, "isFieldPresent", startTime);
		return ret;
	}

	/**
	 * This method checks insert in the table AccountDevice table of the channel
	 * for user
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * 
	 * @return true if the insertion was successful
	 * 
	 * @throws Exception
	 */
	public static boolean isChannelDevicePresent(String crmAccountID, String tenantName) throws Exception {
		Session session = null;
		Object bean;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
			Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
			String queryAccountDevice = "Select d.platformId from AccountDevice d where d.userId = '" + userId + "'";
			bean = session.createQuery(queryAccountDevice).uniqueResult();
			LogUtil.writeInfoDaoLog(ServiceDAOtest.class, "isChannelDevicePresent", queryAccountDevice);

			if (bean == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(ServiceDAOtest.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isChannelDevicePresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving code:'" + crmAccountID + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(ServiceDAOtest.class, "isChannelDevicePresent", startTime);
		return ret;
	}

	
	/**
	 * This method checks insert in the table AccountDevice table of the device_id
	 * for user
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * 
	 * @return true if the insertion was successful
	 * 
	 * @throws Exception
	 */
	public static String getDeviceId(String crmAccountID, String tenantName) throws Exception {
		Session session = null;
		List<String> deviceIds;
		String deviceId = null;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
			Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
			String queryAccountDevice = "Select d.deviceId from AccountDeviceAMS d where d.userId = '" + userId + "'";
			deviceIds = session.createQuery(queryAccountDevice).list();
			deviceId = deviceIds.get(0);
			LogUtil.writeInfoDaoLog(ServiceDAOtest.class, "getDeviceId", queryAccountDevice);

		} catch (Exception e) {
			LogUtil.writeErrorLog(ServiceDAOtest.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " getDeviceId ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving code:'" + crmAccountID + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(ServiceDAOtest.class, "getDeviceId", startTime);
		return deviceId;
	}

	/**
	 * This method returns the last password stored in the table
	 * account_attribute by a user_id
	 * 
	 * @param Long
	 *            input userId's value to filter the query
	 * 
	 * @return last password stored in the table
	 */
	public static String getPassword(Long userId, String tenantName) throws Exception {
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryAccountAttribute = "Select a.attributeValue from AccountAttribute a where a.userId = " + userId + " and a.attributeDetailId = 41 order by a.updateDate desc";
		List<String> listPwd = session.createQuery(queryAccountAttribute).list();
		String pwd = listPwd.get(0);
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();

		return pwd;
	}

	/**
	 * This method checks the input packet on the table AccountProduct
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * @return Long - How many packages were added to
	 * @throws Exception
	 */
	public Long countAccPackage(String crmAccountID, String tenantName) throws Exception {
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
		Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
		String query = "select count(a.compId.username) from AccountProduct a where a.compId.username = " + userId;
		Long num = (Long) session.createQuery(query).uniqueResult();
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return num;
	}

	/**
	 * This method checks the input packet on the table AccountTechnicalPkg
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * @return Long - How many packages were added to
	 * @throws Exception
	 */
	public Long countTecPkg(String crmAccountID, String tenantName) throws Exception {
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
		Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
		String queryAccountProduct = "Select count(a.compId.userId) from AccountTechnicalPkg a where a.compId.userId = '" + userId + "'";
		Long num = (Long) session.createQuery(queryAccountProduct).uniqueResult();
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return num;
	}

	/**
	 * This method checks for records in AccountAttribute table for a user
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * @return true if user is present
	 * @throws Exception
	 */
	public boolean isPresentAccountAttribute(String crmAccountID, String tenantName) throws Exception {
		boolean res = false;
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
		Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
		String query = "Select a from AccountAttribute a where a.userId = '" + userId + "'";
		List<AccountAttribute> list = session.createQuery(query).list();
		AccountAttribute value = list.get(0);
		if (value != null) {
			res = true;
		}
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return res;

	}

	/**
	 * This method checks for records in AccountProduct table for a user
	 * 
	 * @param crmAccountID
	 *            - user to filter
	 * @return true if user is present
	 * @throws Exception
	 */
	public boolean isPresentAccountProduct(String crmAccountID, String tenantName) throws Exception {
		boolean res = false;
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
		Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
		String query = "Select a from AccountProduct a where a.username = '" + userId + "'";
		List<String> list = session.createQuery(query).list();
		String value = list.get(0);
		if (!value.isEmpty() || value != null) {
			res = true;
		}
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return res;

	}

	/**
	 * This method returns the attributeValue given attributeDetailId for a user
	 * 
	 * @param crmAccountID
	 *            to be filtered
	 * @param field
	 *            - attributeDetailId to be filtered
	 * @return attributeValue
	 * @throws Exception
	 */
	public boolean isUpdateUser(String crmAccountID, int field, String tenantName) throws Exception {
		boolean res = false;
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.username from CrmAccount d where d.crmaccountid = '" + crmAccountID + "'";
		Long userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
		String queryAccountProduct = "SELECT a.attributeValue from AttributeDetail d, AccountAttribute a where a.userId = " + userId + " AND d.attributeDetailId = a.attributeDetailId and d.attributeDetailId = " + field + " ORDER BY a.updateDate DESC";
		List<String> list = session.createQuery(queryAccountProduct).list();
		String value = list.get(0);
		if (!value.isEmpty() || value != null) {
			res = true;
		}
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return res;
	}

	/**
	 * This method returns the username from the AccountUser table given a
	 * crmaccountid
	 * 
	 * @param crmAccountID
	 *            parameter to be filtered
	 * @return username associated with the crmaccountid
	 * @throws Exception
	 */
	public String getUsername(String crmAccountID, String tenantName) throws Exception {
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.name from AccountUser d where d.crmaccountid = '" + crmAccountID + "'";
		String username = (String) session.createQuery(queryCrmAccount).uniqueResult();
		if (username != null) {
			return username;
		}
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		return username;
	}

	/**
	 * This method returns the status id from the AccountUser table given a
	 * crmaccountid
	 * 
	 * @param crmAccountID
	 *            parameter to be filtered
	 * @return statusId associated with the crmaccountid
	 * @throws Exception
	 */
	public String getUserStatus(String crmAccountID, String tenantName) throws Exception {
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		String queryCrmAccount = "Select d.statusType from AccountUser d where d.crmaccountid = '" + crmAccountID + "'";
		StatusType statusId = (StatusType) session.createQuery(queryCrmAccount).uniqueResult();
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		String status = statusId.getStatusId().toString();
		if (!status.isEmpty() || status != null) {
			return status;
		}
		
		return status;
	}

	/**
	 * This method verifies that the packages were added / modified according to
	 * today's date
	 * 
	 * @param crmaccountid
	 *            parameter to be filtered
	 * @return true if the package was added
	 */
	public boolean checkTechPack(String crmaccountid, String tenantName) {
		boolean res = false;
		Session session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
		java.util.Date today = new java.util.Date();
		Timestamp timestampNow = new Timestamp(today.getTime());
		String query = "Select a from AccountTechnicalPackage a where a.updateDate = " + timestampNow;
		List list = session.createQuery(query).list();
		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		if (!list.isEmpty() || list != null) {
			res = true;
		}

		return res;

	}

}
