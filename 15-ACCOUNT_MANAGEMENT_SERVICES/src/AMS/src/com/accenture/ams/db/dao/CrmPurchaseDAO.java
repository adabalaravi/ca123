package com.accenture.ams.db.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.crmContentPurchase.TechPackageBean;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.bean.LiveContent;
import com.accenture.ams.db.bean.LivePPV;
import com.accenture.ams.db.bean.PaymentType;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.bean.PurchaseTransactionType;
import com.accenture.ams.db.bean.StatusType;
import com.accenture.ams.db.bean.TechnicalPackage;
import com.accenture.ams.db.bean.TechnicalPackageAttribute;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class CrmPurchaseDAO {

	public static LivePPV getLivePpvByContentId(String externalId,
			String tenantId) throws Exception {
		long startTime = System.currentTimeMillis();
		Session session = null;
		LivePPV result = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantId)
					.openSession();

			String query = "Select lpv From LivePPV lpv where lpv.externalId='"
					+ externalId + "'";

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getLivePpvByContentId", query);

			result = (LivePPV) session.createQuery(query).uniqueResult();

			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
		} catch (Exception e) {
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(CrmPurchaseDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY, e);
			throw e;
		}
		return result;
	}

	public static LiveContent getLiveContentByContentId(Long liveContentId,
			String tenantId) throws Exception {
		long startTime = System.currentTimeMillis();
		Session session = null;
		LiveContent result = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantId)
					.openSession();

			String query = "Select LC From LiveContent LC where LC.liveContentId="+liveContentId;

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getPcExtendedRatingsFromContentId", query);

			result = (LiveContent) session.createQuery(query).uniqueResult();

			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
		} catch (Exception e) {
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(CrmPurchaseDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY, e);
			throw e;
		}
		return result;
	}

	/*
	 * load all needed data from avs DB the method return an array of object :
	 * ret[0] -> HashMap<Long, String> mapOfTechPkg ret[1] ->
	 * ArrayList<AvsContentSnapshot> listOfSnapShot ret[2] -> Long userId ret[3]
	 * ->
	 */
	public static Object[] loadData(String tenantName,
			ArrayList<String> pkgsId, ArrayList<String> subscriptionId,
			String crmAccountId) throws Exception {

		Session session = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving session from SessionFactory'");
		}

		// load packageId/packageType map
		HashMap<Long, TechPackageBean> pkgMap = null;
		try {
			if (pkgsId != null)
				pkgMap = CrmPurchaseDAO.getTechPackageInfo(pkgsId, session,
						tenantName);
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving packages map");
		}

		// load userId
		Long userId = null;
		try {
			userId = CrmPurchaseDAO.getUserId(crmAccountId, session);
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving userId");
		}

		// load paymentType Map
		HashMap<String, Long> paymentTypeMap = null;
		try {
			paymentTypeMap = CrmPurchaseDAO.getPaymentTypeMap(session);
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving paymentTypeMap");
		}

		// load paymentType Map
		HashMap<String, Long> statusTypeMap = null;
		try {
			statusTypeMap = CrmPurchaseDAO.getStatusTypeMap(session);
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving statusTypeMap");
		}

		// load PurchaseTransactionType Map
		HashMap<String, Long> purchaseTransactionTypeMap = null;
		try {
			purchaseTransactionTypeMap = CrmPurchaseDAO
					.getPurchaseTransactionTypeMap(session);
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " loadData() ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving purchaseTransactionTypeMap");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();

		Object[] ret = new Object[5];
		ret[0] = pkgMap;
		ret[1] = userId;
		ret[2] = statusTypeMap;
		ret[3] = paymentTypeMap;
		ret[4] = purchaseTransactionTypeMap;
		return ret;
	}

	public static void insertUpdate(List<Object> beans, String tenantName)
			throws Exception {
		Session session = null;

		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			session.beginTransaction();
			Iterator<Object> beansIt = beans.iterator();
			while (beansIt.hasNext())
				session.saveOrUpdate(beansIt.next());

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class, "insertUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + beans);
			session.getTransaction().commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class, "insertUpdate",
					startTime);
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeErrorLog(CrmPurchaseDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " insertUpdate ", e);
			throw new Exception("Error saving beans");
		}
	}// Fine metodo..

	private static HashMap<Long, TechPackageBean> getTechPackageInfo(
			ArrayList<String> pkgsId, Session session, String tenantName)
			throws Exception {
		long startTime = System.currentTimeMillis();
		List<TechnicalPackage> pkgBean = null;
		String inClause = "(";
		try {
			Iterator<String> it = pkgsId.iterator();
			while (it.hasNext()) {
				inClause = inClause + it.next() + ",";
			}
			// strip last comma
			inClause = inClause.substring(0, inClause.length() - 1);
			inClause = inClause + ")";

			String query = "Select tp from TechnicalPackage tp where tp.packageId in "
					+ inClause;
			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getTechPackageInfo()", query);
			pkgBean = (List<TechnicalPackage>) session.createQuery(query)
					.list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getTechPackageInfo()", startTime);

			HashMap<Long, TechPackageBean> packageMap = new HashMap<Long, TechPackageBean>();

			Iterator<TechnicalPackage> tpIt = pkgBean.iterator();

			while (tpIt.hasNext()) {
				TechPackageBean tpb = new TechPackageBean();
				TechnicalPackage item = tpIt.next();
				tpb.setPackageId(item.getPackageId());
				tpb.setPackageType(item.getPackageType());
				tpb.setExternalId(item.getExternalId());

				List<TechnicalPackageAttribute> listOfTpa = (List<TechnicalPackageAttribute>) CrmPurchaseDAO
						.getPackageAttribute(item.getPackageId(), session);
				HashMap<String, Long> pkgAttribSet = PkgIngestorDAO
						.getTechPackageAttributeSet(
								AccountMgmtServiceConstant.PKG_ATTRIBUTE_SET,
								tenantName);

				Iterator<TechnicalPackageAttribute> it2 = listOfTpa.iterator();
				while (it2.hasNext()) {
					TechnicalPackageAttribute pkgAttr = it2.next();

					if (pkgAttr.getAttributeDetailId() == pkgAttribSet
							.get(AccountMgmtServiceConstant.PKG_END_DATE))
						tpb.setEndDate(pkgAttr.getAttributeValue());
					else if (pkgAttr.getAttributeDetailId() == pkgAttribSet
							.get(AccountMgmtServiceConstant.PKG_START_DATE))
						tpb.setStartDate(pkgAttr.getAttributeValue());
					else if (pkgAttr.getAttributeDetailId() == pkgAttribSet
							.get(AccountMgmtServiceConstant.PKG_VALIDITY_PERIOD))
						tpb.setValidityPeriod(pkgAttr.getAttributeValue());
					else if (pkgAttr.getAttributeDetailId() == pkgAttribSet
							.get(AccountMgmtServiceConstant.PKG_VIEWS_NUMBER))
						tpb.setViewNumber(pkgAttr.getAttributeValue());

				}
				packageMap.put(item.getPackageId(), tpb);
			}

			return packageMap;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getTechPackageInfo() ", e);
			throw new Exception("Error retrieving package:'" + inClause + "'");
		}
	}

	private static Long getUserId(String crmAccountId, Session session)
			throws Exception {

		long startTime = System.currentTimeMillis();
		CrmAccount bean = null;
		try {
			String query = "Select ca from CrmAccount ca where ca.crmaccountid = '"
					+ crmAccountId + "'";
			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getAvsContentSnapshot()", query);
			bean = (CrmAccount) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getAvsContentSnapshot()", startTime);

			if (bean == null)
				throw new Exception("UserId not found");

			return bean.getUsername();
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getUserId() ", e);
			throw new Exception("Error retrieving userId for crmAccount : '"
					+ crmAccountId + "'");
		}
	}

	private static List<TechnicalPackageAttribute> getPackageAttribute(
			Long packageId, Session session) throws Exception {
		long startTime = System.currentTimeMillis();
		List<TechnicalPackageAttribute> bean = null;
		try {
			String query = "Select tpa from TechnicalPackageAttribute tpa where tpa.packageId = "
					+ packageId;

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getPackageAttribute()", query);
			bean = (List<TechnicalPackageAttribute>) session.createQuery(query)
					.list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getPackageAttribute()", startTime);

			if (bean == null)
				throw new Exception("UserId not found");

			return bean;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getUserId() ", e);
			throw new Exception(
					"Error retrieving Technical Package attribute : '"
							+ packageId + "'");
		}
	}

	private static HashMap<String, Long> getPaymentTypeMap(Session session)
			throws Exception {
		long startTime = System.currentTimeMillis();
		List<PaymentType> beans = null;
		try {
			String query = "Select pt from PaymentType pt";

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getPaymentTypeMap()", query);
			beans = (List<PaymentType>) session.createQuery(query).list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getPaymentTypeMap()", startTime);

			if (beans == null)
				throw new Exception("Error loading paymentType map");

			Iterator<PaymentType> it = beans.iterator();
			HashMap<String, Long> retMap = new HashMap<String, Long>();

			while (it.hasNext()) {
				PaymentType ptBean = it.next();
				retMap.put(ptBean.getPaymentMethod().toUpperCase(),
						ptBean.getPaymentTypeId());
			}
			return retMap;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getPaymentTypeMap() ", e);
			throw new Exception("Error retrieving PaymentType map");
		}
	}

	private static HashMap<String, Long> getStatusTypeMap(Session session)
			throws Exception {
		long startTime = System.currentTimeMillis();
		List<StatusType> beans = null;
		try {
			String query = "Select st from StatusType st";

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class, "getStatusTypeMap()",
					query);
			beans = (List<StatusType>) session.createQuery(query).list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getStatusTypeMap()", startTime);

			if (beans == null)
				throw new Exception("Error loading statusType map");

			Iterator<StatusType> it = beans.iterator();
			HashMap<String, Long> retMap = new HashMap<String, Long>();

			while (it.hasNext()) {
				StatusType stBean = it.next();
				retMap.put(stBean.getStatusDescription().toUpperCase(),
						stBean.getStatusId());
			}
			return retMap;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getStatusTypeMap() ", e);
			throw new Exception("Error retrieving statusType map");
		}
	}

	private static HashMap<String, Long> getPurchaseTransactionTypeMap(
			Session session) throws Exception {
		long startTime = System.currentTimeMillis();
		List<PurchaseTransactionType> beans = null;
		try {
			String query = "Select ptt from PurchaseTransactionType ptt";

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getPurchaseTransactionTypeMap()", query);
			beans = (List<PurchaseTransactionType>) session.createQuery(query)
					.list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getPurchaseTransactionTypeMap()", startTime);

			if (beans == null)
				throw new Exception("Error loading purchaseTransactionType map");

			Iterator<PurchaseTransactionType> it = beans.iterator();
			HashMap<String, Long> retMap = new HashMap<String, Long>();

			while (it.hasNext()) {
				PurchaseTransactionType stBean = it.next();
				retMap.put(stBean.getItemDescription().toUpperCase(),
						stBean.getItemType());
			}
			return retMap;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getPurchaseTransactionTypeMap() ", e);
			throw new Exception("Error retrieving purchaseTransactionType map");
		}
	}

	private static HashMap<Long, LiveContent> getLiveContent(
			ArrayList<Long> ppvId, Session session) throws Exception {
		long startTime = System.currentTimeMillis();
		List<LiveContent> liveBean = null;
		String inClause = "(";
		try {
			Iterator<Long> it = ppvId.iterator();
			while (it.hasNext()) {
				inClause = inClause + it.next() + ",";
			}
			// strip last comma
			inClause = inClause.substring(0, inClause.length() - 1);
			inClause = inClause + ")";

			String query = "Select lc from LiveContent lc where lc.liveContentId in "
					+ inClause;
			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class, "getLiveContent()",
					query);
			liveBean = (List<LiveContent>) session.createQuery(query).list();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getLiveContent()", startTime);

			HashMap<Long, LiveContent> liveContentMap = new HashMap<Long, LiveContent>();

			Iterator<LiveContent> tpIt = liveBean.iterator();

			while (tpIt.hasNext()) {
				LiveContent item = tpIt.next();
				liveContentMap.put(item.getLiveContentId(), item);
			}

			return liveContentMap;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getLiveContent() ", e);
			throw new Exception("Error retrieving live content/s:'" + inClause
					+ "'");
		}
	}

	public static PurchaseTransaction getPurchaseTransaction(String itemId,
			Long userId, String tenantName) throws Exception {
		long startTime = System.currentTimeMillis();
		PurchaseTransaction bean = null;
		Session session = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select PT from PurchaseTransaction PT where PT.itemId = '"
					+ itemId + "' AND PT.userId = " + userId;

			LogUtil.writeInfoDaoLog(CrmPurchaseDAO.class,
					"getPurchaseTransaction()", query);
			bean = (PurchaseTransaction) session.createQuery(query)
					.uniqueResult();

			LogUtil.writeInfoEndDaoLog(CrmPurchaseDAO.class,
					"getPurchaseTransaction()", startTime);

			if (bean == null)
				throw new Exception("UserId not found");

			return bean;
		} catch (Exception e) {
			LogUtil.writeErrorLog(CrmAccountDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getPurchaseTransaction() ", e);
			throw new Exception(
					"Error retrieving PurchaseTransaction bean for itemId = "
							+ itemId + " AND userId = " + userId);
		}
	}
}
