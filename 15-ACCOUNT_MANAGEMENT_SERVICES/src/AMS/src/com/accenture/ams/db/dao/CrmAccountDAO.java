package com.accenture.ams.db.dao;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.framework.SystemMessages;
import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class CrmAccountDAO {

	public static boolean isUserNamePresent(String username, String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			String query = "Select c.crmaccountid from CrmAccount c where c.username = "
					+ username;
			String crmAccId = (String) session.createQuery(query)
					.uniqueResult();

			LogUtil.writeInfoDaoLog(CrmAccountDAO.class,
					"isCrmAccountIdPresent", query);

			if (crmAccId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isCrmAccountIdPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + username + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(CrmAccountDAO.class,
				"isCrmAccountIdPresent", startTime);
		return ret;
	}

	public static boolean insertUpdate(CrmAccount account, String tenantName) throws Exception {
		Session session = null;

		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			session.beginTransaction();
			session.saveOrUpdate(account);
			LogUtil.writeInfoDaoLog(CrmAccountDAO.class, "insertUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE + " | "
							+ account);
			session.getTransaction().commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(CrmAccountDAO.class, "insertUpdate",
					startTime);
			return (true);
		} catch (Exception e) {
			session.getTransaction().rollback();
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " insertUpdate ",
					e);
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(CrmAccountDAO.class, "insertUpdate",
				startTime);
		return (false);
	}// Fine metodo..
}
