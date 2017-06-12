package com.accenture.ams.db.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AttributeDetail;
import com.accenture.ams.db.bean.TechnicalPackage;
import com.accenture.ams.db.bean.TechnicalPackageAttribute;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class PkgIngestorDAO {

	public static TechnicalPackage getTechnicalPackage(String externalId,
			String tenantName) throws Exception {
		Session session = null;
		TechnicalPackage packageBean = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select tp from TechnicalPackage tp where tp.externalId = '"
					+ externalId + "'";
			packageBean = (TechnicalPackage) session.createQuery(query)
					.uniqueResult();
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class,
					"getTechnicalPackage", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getTechnicalPackage ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving techPackages for id :'"
					+ externalId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class, "getTechnicalPackage",
				startTime);
		return packageBean;
	}

	public static void saveUpdate(TechnicalPackage tpBean, String tenantName)
			throws Exception {
		Session session = null;
		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			
			session.beginTransaction();
			
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class, "saveUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + tpBean.getExternalId());
			
			session.saveOrUpdate(tpBean);
			
			session.getTransaction().commit();
			
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			
			LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class, "saveUpdate",
					startTime);
		} 
		catch (Exception e) {
			session.getTransaction().rollback();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " saveUpdate ", e);
			throw new Exception("Error saving data : " + e.getMessage());
		} 
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class, "saveUpdate",
				startTime);

	}

	public static void saveUpdate(List<TechnicalPackageAttribute> bean, String tenantName)
			throws Exception {
		Session session = null;
		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			session.beginTransaction();
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class, "saveUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + bean.toString());

			Iterator<TechnicalPackageAttribute> it = bean.iterator();
			while (it.hasNext())
				session.saveOrUpdate(it.next());

			session.getTransaction().commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class, "saveUpdate",
					startTime);
		} 
		catch (Exception e) {
			session.getTransaction().rollback();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " saveUpdate ", e);
			throw new Exception("Error saving data : " + e.getMessage());
		} 
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class, "saveUpdate",
				startTime);
	}

	public static List<TechnicalPackageAttribute> getTechPackageAttribute(
			Long packageId, String tenantName) throws Exception {
		Session session = null;
		List<TechnicalPackageAttribute> packageAttributeBean = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select tpa from TechnicalPackageAttribute tpa where tpa.packageId = "
					+ packageId;
			packageAttributeBean = (List<TechnicalPackageAttribute>) session
					.createQuery(query).list();
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class,
					"getTechPackageAttribute", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getTechPackageAttribute ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception(
					"Error retrieving technical package attributes for packageId :'"
							+ packageId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class,
				"getTechPackageAttribute", startTime);
		return packageAttributeBean;
	}

	public static HashMap<String, Long> getTechPackageAttributeSet(
			String[] attrName, String tenantName) throws Exception {
		Session session = null;
		List<AttributeDetail> attrDetail = null;
		long startTime = System.currentTimeMillis();
		String inClause = "(";
		try {

			for (int i = 0; i < attrName.length; i++)
				inClause = inClause + "'" + attrName[i] + "',";
			// delete last comma
			inClause = inClause.substring(0, inClause.length() - 1);
			inClause = inClause + ")";
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select ad from AttributeDetail ad where ad.attributeDetailName in "
					+ inClause;
			attrDetail = (List<AttributeDetail>) session.createQuery(query)
					.list();
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class,
					"getTechPackageAttributeSet()", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getTechPackageAttributeSet() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception(
					"Error retrieving technical package attributes list :"
							+ inClause);
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class,
				"getTechPackageAttributeSet()", startTime);

		HashMap<String, Long> attrDetailMap = new HashMap<String, Long>();

		Iterator<AttributeDetail> attrIt = attrDetail.iterator();
		while (attrIt.hasNext()) {
			AttributeDetail ad = attrIt.next();
			String name = ad.getAttributeDetailName();
			Long id = ad.getAttributeDetailId();
			attrDetailMap.put(name, id);
		}

		return attrDetailMap;
	}
	
	public static void cleanTechPkgAttribute(Long pkgId, String tenantName) throws Exception{
		Session session = null;
		List<TechnicalPackageAttribute> attr = null;
		long startTime = System.currentTimeMillis();

		try {

			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			session.beginTransaction();
			String query = "Select tpa from TechnicalPackageAttribute tpa where tpa.packageId = " + pkgId;

			attr = (List<TechnicalPackageAttribute>) session.createQuery(query).list();
			
			Iterator<TechnicalPackageAttribute> it = attr.iterator();
			
			while (it.hasNext())
				session.delete(it.next());
			
			session.getTransaction().commit();
			
			LogUtil.writeInfoDaoLog(PkgIngestorDAO.class,
					"cleanTechPkgAttribute()", query);
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(PkgIngestorDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " cleanTechPkgAttribute() ", e);
			session.getTransaction().rollback();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception(
					"Error cleaning technical package attributes list for package :" + pkgId);
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(PkgIngestorDAO.class,
				"cleanTechPkgAttribute()", startTime);		
	}
}
