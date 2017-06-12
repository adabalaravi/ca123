package com.accenture.ams.db.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.Profile;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.framework.SystemMessages;
import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class UserDAO {

	Session session = null;

	/**
	 * Obtains the current session factory and open a session
	 * 
	 */
	private void openConnection(String tenantName) {
		this.session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

	}

	/**
	 * Obtains the current session factory and open a session
	 * 
	 * @param session
	 *            org.hibernate.Session
	 */
	public Session getSession() {
		return this.session;
	}

	/**
	 *Release all resources about the current session and set
	 * 
	 * @param session
	 *            org.hibernate.Session
	 */
	public void closeConnection(String tenantName) {

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		this.session = null;
	}

	public boolean changePasswordAccount(String query, String newPassword, String tenantName) {

		try {
			this.openConnection(tenantName);
			session.beginTransaction();
			AccountUser resultObj = (AccountUser) this.getSession().createQuery(query).uniqueResult();
			if (resultObj != null) {
				// resultObj.setPassword(newPassword);
				this.getSession().update(resultObj);
				session.getTransaction().commit();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
			session.getTransaction().rollback();
			return false;

		} finally {
			this.closeConnection(tenantName);
		}

	}

	public Profile csmGetProfile(String query, String tenantName) {
		Profile resultObj = null;
		try {
			this.openConnection(tenantName);
			resultObj = (Profile) this.getSession().createQuery(query).uniqueResult();

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
		} finally {
			this.closeConnection(tenantName);
		}
		return resultObj;
	}

	public boolean deleteCrmAccount(String query, String tenantName) {

		try {
			this.openConnection(tenantName);
			session.beginTransaction();
			Profile resultObj = (Profile) this.getSession().createQuery(query).uniqueResult();
			if (resultObj != null) {
				// resultObj.setCrmAccStatus(3);
				this.getSession().update(resultObj);
				session.getTransaction().commit();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
			session.getTransaction().rollback();
			return false;

		} finally {
			this.closeConnection(tenantName);
		}

	}

	public boolean updateCrmAccount(String query, String tenantName) {

		try {
			this.openConnection(tenantName);
			session.beginTransaction();
			Profile resultObj = (Profile) this.getSession().createQuery(query).uniqueResult();
			if (resultObj != null) {
				// resultObj.setCrmAccStatus(3);
				this.getSession().update(resultObj);
				session.getTransaction().commit();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
			session.getTransaction().rollback();
			return false;

		} finally {
			this.closeConnection(tenantName);
		}

	}

	public Profile getProfileToUpdate(String query, String tenantName) {
		Profile resultObj = null;
		try {
			this.openConnection(tenantName);
			resultObj = (Profile) this.getSession().createQuery(query).uniqueResult();

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
		} finally {
			// this.closeConnection();
		}
		return resultObj;
	}

	public boolean executeUpdateProfile(Profile p, String tenantName) {
		try {
			// this.openConnection();
			session.beginTransaction();
			this.getSession().update(p);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, " ", e);
			// e.printStackTrace();
			session.getTransaction().rollback();
			return false;

		} finally {
			this.closeConnection(tenantName);
		}

	}
	
	public static boolean isCrmAccountIdPresentAccountUser(String crmAcc, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String query = "Select c from AccountUser c where c.crmaccountid = '" + crmAcc + "'";
			AccountUser accountSelected = (AccountUser) session.createQuery(query).uniqueResult();

			String crmAccId = null;
			if (accountSelected != null)
				crmAccId = accountSelected.getCrmaccountid();

			LogUtil.writeInfoDaoLog(UserDAO.class, "isCrmAccountIdPresentAccountUser", query);

			if (crmAccId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isCrmAccountIdPresentAccountUser ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + crmAcc + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(UserDAO.class, "isCrmAccountIdPresentAccountUser", startTime);
		return ret;
	}
	
	public static List isCrmProductPresent(String crmAccountId, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		List products = null;
		Long userId = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			String queryCrmAccount = "Select c.username from CrmAccount c where c.crmaccountid = " + crmAccountId;
			userId = (Long) session.createQuery(queryCrmAccount).uniqueResult();
			String queryAccountProduct = "Select p.solutionOfferId from AccountProduct p where p.username = " + userId;
			products = (List) session.createQuery(queryAccountProduct);
			
			LogUtil.writeInfoDaoLog(UserDAO.class, "isCrmProductPresent", queryAccountProduct);


		} catch (Exception e) {
			LogUtil.writeErrorLog(UserDAO.class, AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " isCrmProductPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + userId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(UserDAO.class, "isCrmProductPresent", startTime);
		return products;
	}

}
