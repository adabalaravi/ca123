package com.accenture.ams.db.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountDeviceAMS;
import com.accenture.ams.db.bean.AccountProductBean;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.db.bean.CrmAccount;
import com.accenture.ams.db.bean.CrmAccountBean;
import com.accenture.ams.db.bean.PaymentType;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.bean.SdpSolutionOfferView;
import com.accenture.ams.db.bean.SdpVoucherView;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class AccountManagementDAO {

	public static void addPurchaseTransaction(List<Object> beanList,
			String tenantName) throws Exception {
		Transaction trx = null;
		long startTime = System.currentTimeMillis();

		try {
			// Effetuo la query
			Session session = HibernateUtil.getInstance()
					.getSessionFactory(tenantName).openSession();
			trx = session.beginTransaction();
			Iterator<Object> beanIt = null;
			if (beanList != null) {
				beanIt = beanList.iterator();
				while (beanIt.hasNext()) {
					Object item = beanIt.next();
					session.saveOrUpdate(item);
				}
			}
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"addPurchaseTransaction",
					AccountMgmtServiceConstant.INFO_BE_3025_DAO_EXECDELETE
							+ " | " + beanIt);

			trx.commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		} catch (Exception e) {
			if (trx != null) {
				trx.rollback();
			}
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " addPurchaseTransaction ", e);
			throw new Exception(
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " addPurchaseTransaction: " + e.getMessage());
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"addPurchaseTransaction", startTime);

	}

	public static List<PurchaseTransaction> getPurchaseTransactionBean(Long itemId,
			Long parentItemId, Long userId, String tenantName) throws Exception {
		Session session = null;
		List<PurchaseTransaction> purchaseTransaction = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select PT from PurchaseTransaction PT where PT.itemId = "
					+ itemId + " AND PT.userId=" + userId + " AND (PT.state = 1 OR PT.state = 11 OR PT.state = 12)";
			
			if (parentItemId == null)
				query = query + " AND PT.parentItemId is null";
			else
				query = query + " AND PT.parentItemId=" + parentItemId;
			
			purchaseTransaction = (List<PurchaseTransaction>) session.createQuery(
					query).list();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getPurchaseTransactionBean", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getPurchaseTransactionBean ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception(
					"Error retrieving PurchaseTransaction for itemId :'"
							+ itemId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getPurchaseTransactionBean", startTime);
		return purchaseTransaction;
	}

	public static List<AccountTechnicalPkg> getAccountTechPackage(Long pkgId,
			Long userId, String tenantName) throws Exception {
		Session session = null;
		List<AccountTechnicalPkg> atp = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select ATP from AccountTechnicalPkg ATP where ATP.techPackageId = "
					+ pkgId + " AND ATP.userId=" + userId;
			atp = (List<AccountTechnicalPkg>) session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getAccountTechPackage", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountTechPackage ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception(
					"Error retrieving AccountTechnicalPkg for pkgId :'" + pkgId
							+ "' and userId : '" + userId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getAccountTechPackage", startTime);
		return atp;
	}

	public static void deleteAccountTechPackage(List<AccountTechnicalPkg> atp,
			String tenantName) throws Exception {
		Transaction trx = null;
		long startTime = System.currentTimeMillis();
		try {
			Session session = HibernateUtil.getInstance()
					.getSessionFactory(tenantName).openSession();

			trx = session.beginTransaction();
			if (atp != null) {
				Iterator<AccountTechnicalPkg> atpIt = atp.iterator();
				while (atpIt.hasNext()) {
					AccountTechnicalPkg item = atpIt.next();
					session.delete(item);
				}
			}
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"deleteAccountTechPackage",
					AccountMgmtServiceConstant.INFO_BE_3025_DAO_EXECDELETE
							+ " | " + atp);

			trx.commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		} catch (Exception e) {
			if (trx != null) {
				trx.rollback();
			}
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " deleteAccountTechPackage ", e);
			throw new Exception(
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " deleteAccountTechPackage: " + e.getMessage());
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"deleteAccountTechPackage", startTime);
	}

	public static List<Object[]> getCommercialCategory(int solutionId,
			String tenantName) throws Exception {
		Session session = null;
		List<Object[]> listOfPackage = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select p.solutionOfferId, rp.technicalPackage.packageId, rt.packageValue "
					+ "from Product p, RelPackageCommCat rp, RefTechnicalPackage rt "
					+ "where p.commercialCategory.offerTypeId = rp.compId.offerTypeId "
					+ "and rp.refTechnicalPackage.packageValueId = rt.packageValueId "
					+ "and p.solutionOfferId = " + solutionId;
			listOfPackage = (List<Object[]>) session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getCommercialCatagory", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getCommercialCatagory ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving techPackages for solution :'"
					+ solutionId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getCommercialCatagory", startTime);
		return listOfPackage;

	}

	public static boolean isUserNamePresent(String username, String tenantName)
			throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c.crmaccountid from CrmAccount c where c.username = "
					+ username;
			String crmAccId = (String) session.createQuery(query)
					.uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isCrmAccountIdPresent", query);

			if (crmAccId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isCrmAccountIdPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + username + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isCrmAccountIdPresent", startTime);
		return ret;
	}

	public static boolean isCrmAccountIdPresent(String crmAcc, String tenantName)
			throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c from CrmAccount c where c.crmaccountid = '"
					+ crmAcc + "'";
			CrmAccount accountSelected = (CrmAccount) session
					.createQuery(query).uniqueResult();

			String crmAccId = null;
			if (accountSelected != null)
				crmAccId = accountSelected.getCrmaccountid();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isCrmAccountIdPresent", query);

			if (crmAccId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isCrmAccountIdPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + crmAcc + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isCrmAccountIdPresent", startTime);
		return ret;
	}

	public static boolean isChannelPresent(String channel, String tenantName)
			throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select p.platformName from Platform p where p.platformName = '"
					+ channel + "'";
			String pName = (String) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isChannelPresent", query);

			if (pName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isChannelPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving channel:'" + channel + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isChannelPresent", startTime);
		return ret;
	}

	public static boolean isValidStatusId(Long statusId, String tenantName)
			throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select ST.statusId from StatusType ST where ST.statusId = "
					+ statusId;
			Long pName = (Long) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isValidStatusId", query);

			if (pName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isValidStatusId ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving statusId:'" + statusId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isValidStatusId", startTime);
		return ret;
	}

	public static boolean isPaymentMethodPresent(Long paymentMethod,
			String tenantName) throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select c from PaymentType c where c.paymentTypeId = "
					+ paymentMethod;
			PaymentType pName = (PaymentType) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isPaymentMethodPresent", query);

			if (pName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isPaymentMethodPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving isPaymentMethodPresent:'" + paymentMethod
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isPaymentMethodPresent", startTime);
		return ret;
	}

	public static boolean isPcLevelCodePresent(Long pcLevel, String tenantName)
			throws Exception {
		Session session = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			String query = "Select PCL.value from PcLevel PCL where PCL.value = "
					+ pcLevel;
			String pName = (String) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isPcLevelCodePresent", query);

			if (pName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isPcLevelCodePresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving pcLevel:'" + pcLevel + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isPcLevelCodePresent", startTime);
		return ret;
	}

	public static boolean insertUpdateCrmAccount(CrmAccount account,
			AccountUserBean au, ArrayList<AccountAttribute> aa,
			ArrayList<AccountDeviceAMS> ad, ArrayList<AccountProductBean> ap,
			ArrayList<AccountTechnicalPkg> atp, String tenantName)
			throws Exception {
		Session session = null;

		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			Long userId = null;
			session.beginTransaction();
			if (au != null) {
				session.saveOrUpdate(au);
				/*
				 * after insert new record in accountUser retrieve the user_id
				 * assigned
				 */
				userId = au.getUsername();
			}

			if (account != null) {
				account.setUsername(userId);
				session.saveOrUpdate(account);
			}
			if (aa != null) {
				Iterator<AccountAttribute> it = aa.iterator();
				while (it.hasNext()) {
					AccountAttribute aaItem = it.next();
					aaItem.setUserId(userId);
					session.saveOrUpdate(aaItem);
				}
			}
			
			if (ad != null) {
				//add account device to user
				//before insert/update clean the table
				Criteria cri = session.createCriteria(AccountDeviceAMS.class);
				cri.add( Restrictions.eq("userId", userId) );
				List<AccountDeviceAMS> userDeviceList = (List<AccountDeviceAMS>) cri.list();
				
				if (userDeviceList!=null){
					Iterator<AccountDeviceAMS> deviceToClean = userDeviceList.iterator();
					while ( deviceToClean.hasNext() )
						session.delete(deviceToClean.next());
				}

				session.flush();
				session.clear();
				
				Iterator<AccountDeviceAMS> listOfDevice = ad.iterator();

				while (listOfDevice.hasNext()) {
					AccountDeviceAMS deviceBean = listOfDevice.next();
					deviceBean.setUserId(userId);
					session.saveOrUpdate(deviceBean);
				}
			}

			if (ap != null) {
				Iterator<AccountProductBean> apIt = ap.iterator();

				while (apIt.hasNext()) {
					AccountProductBean aaItem = apIt.next();
					aaItem.getCompId().setUsername(userId);
					// aaItemPk.setSolutionOfferId(aaItem.getCompId().getSolutionOfferId());
					session.saveOrUpdate(aaItem);
				}
			}

			if (atp != null) {
				Iterator<AccountTechnicalPkg> atpIt = atp.iterator();
				while (atpIt.hasNext()) {
					AccountTechnicalPkg aaItem = atpIt.next();
					aaItem.setUserId(userId);
					session.saveOrUpdate(aaItem);
				}
			}

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class, "insertUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + account);
			session.flush();
			session.getTransaction().commit();
			
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
					"insertUpdate", startTime);
			return (true);
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.clear();
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " insertUpdate ", e);
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class, "insertUpdate",
				startTime);
		return (false);
	}// Fine metodo..

	/**
	 * This method returns all the password of a user data from tables
	 * AccountAttribute and AccountUser order by update_date
	 * 
	 * @param userName
	 *            - filter by user_id
	 * @return String[] - password list
	 * @throws Exception
	 */
	public static String getAccountUser(Long userName, String tenantName)
			throws Exception {
		Session session = null;
		long startTime = System.currentTimeMillis();
		String pwd = null;
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			// String query =
			// "Select attr.attributeValue from AccountUser au, AccountAttribute attr where au.username = attr.userId and attr.attributeDetailId = 41 and au.username = "
			// + userName + " order by attr.updateDate desc";
			String query = "Select attr.attributeValue from AccountUser au, AccountAttribute attr where au.username = attr.userId and attr.attributeDetailId = 41 and au.username = "
					+ userName;
			pwd = (String) session.createQuery(query).uniqueResult();
			// au = list.toArray(new String[list.size()]);
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getAccountUser ", pwd);
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountUser ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving user:'" + userName + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getAccountUser", startTime);
		return pwd;
	}

	public static AccountAttribute getAccountAttribute(Long attr,
			Long userName, String tenantName) throws Exception {
		Session session = null;
		AccountAttribute au = null;
		List<AccountAttribute> list = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select aa from AccountAttribute aa "
					+ "where aa.attributeDetailId = " + attr + " "
					+ "and aa.userId = " + userName;
			list = (session.createQuery(query).list());
			au = list.get(0); // take date
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getAccountAttribute", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountAttribute ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving user:'" + userName + "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getAccountAttribute", startTime);
		return au;
	}

	public static boolean insertUpdateAccountAttribute(AccountAttribute aa,
			String tenantName) throws Exception {
		Session session = null;

		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			session.beginTransaction();
			session.saveOrUpdate(aa);

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"insertUpdateAccountAttribute",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + aa);
			session.getTransaction().commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
					"insertUpdateAccountAttribute", startTime);
			return (true);
		} catch (Exception e) {
			session.getTransaction().rollback();
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " insertUpdateAccountAttribute ", e);
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"insertUpdateAccountAttribute", startTime);
		return (false);
	}// Fine metodo..

	public static CrmAccountBean getCrmAccountBean(String crmAccountId,
			String tenantName) throws Exception {
		Session session = null;
		CrmAccountBean ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select C from CrmAccountBean C where C.crmaccountid = '"
					+ crmAccountId + "'";
			Query q = session.createQuery(query);

			ret = (CrmAccountBean) q.uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getCrmAccount", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getCrmAccount ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + crmAccountId
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class, "getCrmAccount",
				startTime);
		return ret;
	}

	public static CrmAccount getCrmAccount(String crmAccountId,
			String tenantName) throws Exception {
		Session session = null;
		CrmAccount ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select C from CrmAccount C where C.crmaccountid = '"
					+ crmAccountId + "'";

			ret = (CrmAccount) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getCrmAccount", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getCrmAccount ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + crmAccountId
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class, "getCrmAccount",
				startTime);
		return ret;
	}

	public static AccountUserBean getAccountUserBean(Long userName,
			String tenantName) throws Exception {
		Session session = null;
		AccountUserBean ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select C from AccountUserBean C where C.username = "
					+ userName;
			ret = (AccountUserBean) session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getAccountUser", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountUser ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving username:'" + userName + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getAccountUser", startTime);
		return ret;
	}

	public static List<AccountAttribute> getAccountAttributesBean(
			Long userName, String tenantName) throws Exception {
		Session session = null;
		List<AccountAttribute> ret = null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c from AccountAttribute c where c.userId = "
					+ userName;
			ret = (List<AccountAttribute>) session.createQuery(query).list();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"getAccountAttributesBean", query);

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountAttributesBean ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving attribute for username:'"
					+ userName + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getAccountAttributesBean", startTime);
		return ret;
	}

	public static boolean updateUserData(CrmAccountBean account,
			AccountUserBean au, ArrayList<AccountAttribute> aa,
			String tenantName) throws Exception {
		Session session = null;

		long startTime = System.currentTimeMillis();

		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			session.beginTransaction();

			session.update(au);
			session.update(account);
			if (aa != null) {
				Iterator<AccountAttribute> it = aa.iterator();
				while (it.hasNext()) {
					AccountAttribute aaItem = it.next();
					session.update(aaItem);
				}
			}

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class, "insertUpdate",
					AccountMgmtServiceConstant.INFO_BE_3026_DAO_EXECUPDATE
							+ " | " + account);
			session.getTransaction().commit();
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
					"insertUpdate", startTime);
			return (true);
		} catch (Exception e) {
			session.getTransaction().rollback();
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " insertUpdate ", e);
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class, "insertUpdate",
				startTime);
		return (false);
	}// Fine metodo..

	public static boolean deleteCommercialProfile(
			ArrayList<AccountProductBean> ap,
			ArrayList<AccountTechnicalPkg> atp, String tenantName)
			throws Exception {
		// Dichiaro la variabile per il ritorno
		boolean result = false;
		Transaction trx = null;
		long startTime = System.currentTimeMillis();

		try {

			// Ottengo un riferimeto al file di log

			// Effetuo la query
			Session session = HibernateUtil.getInstance()
					.getSessionFactory(tenantName).openSession();

			trx = session.beginTransaction();
			if (atp != null) {
				Iterator<AccountTechnicalPkg> atpIt = atp.iterator();
				while (atpIt.hasNext()) {
					AccountTechnicalPkg item = atpIt.next();
					session.delete(item);
				}
			}
			if (ap != null) {
				Iterator<AccountProductBean> apIt = ap.iterator();
				while (apIt.hasNext()) {
					AccountProductBean item = apIt.next();
					session.delete(item);
				}
			}

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"deleteCommercialProfile",
					AccountMgmtServiceConstant.INFO_BE_3025_DAO_EXECDELETE
							+ " | " + atp + " | " + ap);

			trx.commit();
			// esito OK
			result = true;
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		} catch (Exception e) {
			if (trx != null) {
				trx.rollback();
			}
			// esito KO
			result = false;
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " deleteCommercialProfile ", e);
			throw new Exception(
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " deleteCommercialProfile: " + e.getMessage());
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"deleteCommercialProfile", startTime);

		return result;
	}

	public static boolean addCommercialProfile(
			ArrayList<AccountProductBean> ap,
			ArrayList<AccountTechnicalPkg> atp, String tenantName)
			throws Exception {
		// Dichiaro la variabile per il ritorno
		boolean result = false;
		Transaction trx = null;
		long startTime = System.currentTimeMillis();

		try {

			// Ottengo un riferimeto al file di log

			// Effetuo la query
			Session session = HibernateUtil.getInstance()
					.getSessionFactory(tenantName).openSession();
			trx = session.beginTransaction();
			if (ap != null) {
				Iterator<AccountProductBean> apIt = ap.iterator();
				while (apIt.hasNext()) {
					AccountProductBean item = apIt.next();
					session.saveOrUpdate(item);
				}
			}
			if (atp != null) {
				Iterator<AccountTechnicalPkg> atpIt = atp.iterator();
				while (atpIt.hasNext()) {
					AccountTechnicalPkg item = atpIt.next();
					session.saveOrUpdate(item);
				}
			}

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"addCommercialProfile",
					AccountMgmtServiceConstant.INFO_BE_3025_DAO_EXECDELETE
							+ " | " + atp + " | " + ap);

			trx.commit();
			// esito OK
			result = true;
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		} catch (Exception e) {
			if (trx != null) {
				trx.rollback();
			}
			// esito KO
			result = false;
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " addCommercialProfile ", e);
			throw new Exception(
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " addCommercialProfile: " + e.getMessage());
		} finally {
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		}
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"addCommercialProfile", startTime);

		return result;
	}

	public static boolean isReatilerDomainPresent(String retailerId,
			String tenantName) throws Exception {
		Session session = null;
		List<Object> domain = null;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c from RetailerDomain c where c.retailerId = '"
					+ retailerId + "'";
			domain = (List<Object>) session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isReatilerDomainPresent", query);

			if (domain == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isReatilerDomainPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving domain for retailerId:'"
					+ retailerId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isReatilerDomainPresent", startTime);
		return ret;
	}

	public static boolean isCurrencyCodeValid(String currCode, String tenantName)
			throws Exception {
		Session session = null;
		Object curr = null;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c from RefCurrencyTypeView c where c.currencyTypeName = '"
					+ currCode + "'";
			curr = session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isCurrencyCodeValid", query);

			if (curr == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isCurrencyCodeValid ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving currency code:'" + currCode
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isCurrencyCodeValid", startTime);
		return ret;
	}

	public static boolean isProductIdValid(Long productId, String tenantName)
			throws Exception {
		Session session = null;
		Object productBean;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select p from Product p where p.solutionOfferId = "
					+ productId;
			productBean = session.createQuery(query).uniqueResult();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isProductIdValid", query);

			if (productBean == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isProductIdValid ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving product for Id:'" + productId
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isProductIdValid", startTime);
		return ret;
	}

	public static boolean isCountryLangCodeValide(String code, String tenantName)
			throws Exception {
		Session session = null;
		Object bean;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select c from CountryLangCode c where c.countryCode = '"
					+ code + "'";
			bean = session.createQuery(query).uniqueResult();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isCountryLangCodeValide", query);

			if (bean == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isCountryLangCodeValide ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving code:'" + code + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isCountryLangCodeValide", startTime);
		return ret;
	}

	public static boolean isDeviceTypeIdValid(String deviceId, String tenantName)
			throws Exception {
		Session session = null;
		Object bean;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "Select d from DeviceIdType d where d.typeId = "
					+ Long.parseLong(deviceId);
			bean = session.createQuery(query).uniqueResult();
			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isDeviceTypeIdValid", query);

			if (bean == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isDeviceTypeIdValid ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving code:'" + deviceId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isDeviceTypeIdValid", startTime);
		return ret;
	}

	public static boolean isDeviceIdPresent(String deviceId, Long userId,
			String tenantName) throws Exception {
		Session session = null;
		Object bean;
		boolean ret = false;

		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();

			String query = "";

			if (userId != null)
				query = "Select d from AccountDeviceAMS d where d.deviceId = '"
						+ deviceId + "' and d.userId <> " + userId;
			else
				query = "Select d from AccountDeviceAMS d where d.deviceId = '"
						+ deviceId + "'";
			bean = session.createQuery(query).uniqueResult();

			LogUtil.writeInfoDaoLog(AccountManagementDAO.class,
					"isDeviceIdPresent", query);

			if (bean == null)
				ret = false;
			else
				ret = true;

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " isDeviceIdPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving code:'" + deviceId + "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"isDeviceIdPresent", startTime);
		return ret;
	}

	public static Long getVoucherDuration(String voucherCode, String tenantName)
			throws Exception {
		Session session = null;
		Long duration = -1L;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			Criteria cri = session.createCriteria(SdpVoucherView.class);
			cri.add(Restrictions.eq("voucherCode", voucherCode));
			SdpVoucherView voucher = (SdpVoucherView) cri.uniqueResult();

			if (voucher != null)
				duration = 0L;//voucher.getValidityPeriod();
		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getVoucherDuration ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving voucher:'" + voucherCode
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getVoucherDuration", startTime);
		return duration;
	}
	
	public static SdpSolutionOfferView getSolutionOffer(Long solOfferId, String tenantName) throws Exception {
		Session session = null;
		SdpSolutionOfferView solOffer=null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName)
					.openSession();
			Criteria cri = session.createCriteria(SdpSolutionOfferView.class);
			cri.add(Restrictions.eq("solutionOfferID", solOfferId));
			solOffer = (SdpSolutionOfferView) cri.uniqueResult();

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getSolutionOffer ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving SolutionOffer:'" + solOfferId
					+ "'");
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,
				"getSolutionOffer", startTime);
		return solOffer;		
	}

	public static SdpVoucherView getVoucherDetails(String voucherCode, String tenantName) throws Exception{
		Session session = null;
		SdpVoucherView sdpVoucherView=null;
		long startTime = System.currentTimeMillis();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();
			Criteria cri = session.createCriteria(SdpVoucherView.class);
			cri.add(Restrictions.eq("voucherCode", voucherCode));
			sdpVoucherView = (SdpVoucherView) cri.uniqueResult();

		} catch (Exception e) {
			LogUtil.writeErrorLog(AccountManagementDAO.class,AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY + " getVoucherDetails ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving SdpVoucherView:'" + voucherCode	+ "'");
		}

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(AccountManagementDAO.class,"getVoucherDetails", startTime);
		return sdpVoucherView;
	}
	
}