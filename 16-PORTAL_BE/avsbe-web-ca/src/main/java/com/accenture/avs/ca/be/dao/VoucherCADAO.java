package com.accenture.avs.ca.be.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.core.bean.Subscription;
import com.accenture.avs.be.core.dao.PurchaseDAO;
import com.accenture.avs.be.core.factory.AvsDaoFactory;
import com.accenture.avs.be.db.bean.AccountTechnicalPkg;
import com.accenture.avs.be.db.bean.AccountTechnicalPkgPK;
import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.bean.PurchaseTransaction;
import com.accenture.avs.be.db.bean.PurchaseTransactionType;
import com.accenture.avs.be.db.bean.SdpVoucherView;
import com.accenture.avs.be.db.bean.StatusType;
import com.accenture.avs.be.db.dao.AccountTechPackageDAO;
import com.accenture.avs.be.db.dao.PurchaseTransactionDAO;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.db.util.VoucherUtil;

/**
 * 
 * @author 
 * 
 */
public class VoucherCADAO {
	public static Logger logger = Logger.getLogger(VoucherCADAO.class);

	public VoucherCADAO() {
		super();
	}

	/**
	 * This method returns SdpVoucherView filtered to voucherCode
	 * 
	 * @param voucherID
	 * @param tenantConfigurator
	 * @return SdpVoucherView
	 * @throws Exception
	 */
	public static SdpVoucherView getVoucherID(String voucherCode, TenantConfigurator tenantConfigurator) throws Exception {
		String methodName = "getVoucherID";
		SdpVoucherView voucher = null;
		long startTime = System.currentTimeMillis();

		String tenantId = tenantConfigurator.getTenantId();

		Session session = HibernateUtil.getSessionFactory(tenantId).openSession();

		LogUtil.commonDebugLog(logger, methodName, "PARAM voucher Code: " + voucherCode);
		try {
			Query queryObj = null;

			String query = "select v from SdpVoucherView v where v.voucherCode = ?";
			queryObj = session.createQuery(query);
			queryObj.setString(0, voucherCode);
			voucher = (SdpVoucherView) queryObj.uniqueResult();

			LogUtil.commonDebugLog(logger, methodName, query);
			if (voucher != null) {
				return voucher;
			}
		} catch (Exception e) {
			LogUtil.errorLog(logger, " Voucher ID is not present: ", e);
			throw new Exception(" Voucher ID is not present: " + e.getMessage());
		} finally {
			HibernateUtil.getSessionFactory(tenantId).close();
		}
		LogUtil.writeInfoEndDaoLog(VoucherCADAO.class, methodName+"get VoucherCode Sucessfully---", startTime);
		return null;
	}

	/**
	 * This method returns false if the voucher has already been used, true if
	 * the voucher has not been used and assigns the user inserting a row in the
	 * table AccountTechnicalPkg
	 * 
	 * @param userIDfromProfile
	 * @param techPacks
	 * @param voucher
	 * @param tenantConfigurator
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean setVoucherToAccountTechPkg(Profile profile, List<String> techPacks, SdpVoucherView voucher, TenantConfigurator tenantConfigurator) throws Exception {
		String methodName = "setVoucherToAccountTechPkg";
		long startTime = System.currentTimeMillis();
		String tenantId = tenantConfigurator.getTenantId();
		AccountTechnicalPkg accTechPKG = new AccountTechnicalPkg();
		AccountTechnicalPkgPK accTechPK = new AccountTechnicalPkgPK();
		
		Timestamp endDate = getEndDate(voucher.getSdpVoucherCampaignView().getValidityPeriod());
		try {
			for (Iterator<String> iterator = techPacks.iterator(); iterator.hasNext();) {
				String techPKG = (String) iterator.next();
					accTechPK.setUserId(profile.getUserId());
					accTechPK.setTechPackageId(techPKG);
					accTechPKG.setCrmAccountId(profile.getCrmAccountId());
					accTechPKG.setCompId(accTechPK);
					accTechPKG.setValidityPeriod(endDate);
					accTechPKG.setStartDate(voucher.getCreatedDate());
					AccountTechPackageDAO.saveAccountTechPkg(accTechPKG, tenantConfigurator);
			}

		} catch (Exception e) {
			LogUtil.errorLog(logger, " ERROR: ", e);
			throw new Exception(" ERROR: " + e.getMessage());
		} finally {
			HibernateUtil.getSessionFactory(tenantId).close();
		}
		LogUtil.writeInfoEndDaoLog(VoucherCADAO.class, methodName+"Voucher Code---:"+voucher.getVoucherCode()+"--UserID---:"+profile.getUserId().toString()+"----Entry Made sucessfully into Account Technical table Sucessfully", startTime);
		return true;

	}

	/**
	 * This method inserts the fake purchase of a voucher by a user in the table
	 * purchase_transaction
	 * 
	 * @param Profile
	 * @param SdpVoucherView
	 * @param techPacks
	 * @param solutionOffer
	 * @param TenantConfigurator
	 * @throws Exception
	 * 
	 */
	public static void preparePurchaseTransactionByVoucher(Profile profile, SdpVoucherView voucher, List<String> techPacks, Long solutionOffer,String channel, TenantConfigurator tenantConfigurator) throws Exception {
		String methodName = "preparePurchaseTransactionByVoucher";
		long startTime = System.currentTimeMillis();
		PurchaseTransaction purchaseTran = new PurchaseTransaction();
		PurchaseTransactionType purchaseType = new PurchaseTransactionType();
		Long itemId = null;
		Long parentItenId = null;
		//Item type has been changed from 5 to 4,for the OPTUS voucher requirement to be shown in purchase history API and AVS console packages list.
		purchaseType.setItemType(4l);
		
		//ALL java.sql.timestamp
		//voucher.getSdpVoucherCampaignView().getStartDate();
		//voucher.getSdpVoucherCampaignView().getEndDate();
		//voucher.getSdpVoucherCampaignView().getValidityPeriod();
		
		PurchaseDAO pDAO = AvsDaoFactory.getPurchaseDAO(tenantConfigurator);
		Subscription sub = pDAO.getProductPrice(solutionOffer, profile.getUserId(), tenantConfigurator);
		String currency = sub.getCurrency();
		
		Timestamp subscriptionStartDate = sub.getStartDate();
		Timestamp subscriptionEndDate = sub.getEndDate();
		
		VoucherUtil voucherUtil = new VoucherUtil(voucher.getSdpVoucherCampaignView().getStartDate(), voucher.getSdpVoucherCampaignView().getEndDate(), voucher.getSdpVoucherCampaignView().getValidityPeriod(), subscriptionStartDate, subscriptionEndDate);
		Timestamp voucherStartDate = voucherUtil.getStartDate();
		Timestamp voucherEndDate = voucherUtil.getEndDate();
		LogUtil.commonInfoLog(logger, " preparePurchaseTransactionByVoucher", "INFO START_DATE - END_DATE : ["+voucherStartDate +"-"+ voucherEndDate +"]");
		
		//Timestamp endDate = getEndDate(voucher.getValidityPeriod(),voucher.getSdpVoucherCampaignView().getEndDate() , voucher.getCreatedDate());
		//add parent (solutionOfferId) record
		
		PurchaseTransaction parent = new PurchaseTransaction();
		parent.setUser(profile);
//		parent.setStartDate(new Timestamp(System.currentTimeMillis()));
//		parent.setEndDate(endDate);
		parent.setStartDate(voucherStartDate);
		parent.setEndDate(voucherEndDate);
		parent.setItemId(Long.toString(solutionOffer));
		parent.setParentItemId(null);
		parent.setOriginalPrice("0");
		//parent.setCurrency("EUR");
		parent.setCurrency(currency);
		parent.setItemType(purchaseType);
		
		StatusType stParent = new StatusType();
		stParent.setStatusId(12L);
		parent.setState( stParent );
		parent.setToken("VID_"+voucher.getVoucherCode());
		//Inserting voucher code into 'ipn_tnx_id' column of purchase transaction table for OPTUS voucher code implementation-to avoid the duplicate voucher code package associations
		//and this column has been made unique key in DB.
		parent.setIpnTnxId(voucher.getVoucherCode());
		//Inserting channel into 'ipn_tnx_type' column of purchase transaction table for OPTUS voucher code implementation to come to know device by which voucher got redeemed.
		parent.setIpnTnxType(channel);
		PurchaseTransactionDAO.savePurchaseTransaction(parent, tenantConfigurator);
		for (Iterator<String> iterator = techPacks.iterator(); iterator.hasNext();) {
			String techPack = (String) iterator.next();
			purchaseTran.setUser(profile);
			purchaseTran.setStartDate(new Timestamp(System.currentTimeMillis()));
//			purchaseTran.setEndDate(endDate);
			purchaseTran.setEndDate(voucherEndDate);
			itemId = Long.parseLong(techPack);
			parentItenId = solutionOffer;
			purchaseTran.setItemId(String.valueOf(itemId));
			purchaseTran.setParentItemId(parentItenId);
			purchaseTran.setOriginalPrice("0");
			//purchaseTran.setCurrency("EUR");
			purchaseTran.setCurrency(currency);
			purchaseTran.setItemType(purchaseType);
//			purchaseTran.setStartDate(new Timestamp(System.currentTimeMillis()));
			purchaseTran.setStartDate(voucherStartDate);
			
			StatusType st = new StatusType();
			st.setStatusId(12L);
			purchaseTran.setState( st );
			//purchaseTran.setToken("VID_"+voucher.getVoucherCode());

			PurchaseTransactionDAO.savePurchaseTransaction(purchaseTran, tenantConfigurator);
			LogUtil.writeInfoEndDaoLog(VoucherCADAO.class, methodName+"--Voucher Code---: "+voucher.getVoucherCode()+"--UserID---:"+profile.getUserId().toString()+"----Entry Made sucessfully into Purchase Transaction table Sucessfully", startTime);
		}
	}

	/**
	 * This method assigns a voucher to a user
	 * 
	 * @param voucher
	 * @param tenantConfigurator
	 * @return true if the record has been inserted
	 */
	public static boolean insertPartyID(SdpVoucherView voucher, TenantConfigurator tenantConfigurator) {
		String methodName = "insertPartyID";
		long startTime = System.currentTimeMillis();
		String tenantId = tenantConfigurator.getTenantId();
		Session session = HibernateUtil.getSessionFactory(tenantId).openSession();
		boolean inserted = false;

		try {
			session.getTransaction().begin();
			voucher.setPartyID(voucher.getPartyID());
			session.saveOrUpdate(voucher);
			session.getTransaction().commit();
			inserted = true;

		} catch (Exception e) {
			session.getTransaction().rollback();
			LogUtil.errorLog(logger, " ERROR: ", e);
		} finally {
			session.close();
			HibernateUtil.getSessionFactory(tenantId).close();
		}
		LogUtil.writeInfoEndDaoLog(VoucherCADAO.class, methodName+"Voucher Code---:"+voucher.getVoucherCode()+"--UserID---:"+voucher.getPartyID().toString()+"----Party Id is associated to Voucher ", startTime);
		return inserted;

	}

	/**
	 * Utility method to calculate the end date
	 * 
	 * @param validityPeriod
	 * @return if validityEndDate = null  return  else return endDateNew (voucherCreatonDate + validityPeriod)  else return validityEndDate
	 */
	private static Timestamp getEndDate(Long validityPeriod) {
		String methodName = "getEndDate";
		long startTime = System.currentTimeMillis();
		Timestamp endDateNew = null;
		Date sysdate = null;
		long validityPeriodMilliSec=0L;
		Timestamp voucherCreationDate;
		
		if(validityPeriod!=null){
			validityPeriodMilliSec = validityPeriod * 86400000;
		}
		
		sysdate = new Date();
		voucherCreationDate = new Timestamp(sysdate.getTime());
		
		long endDateMilliSec = voucherCreationDate.getTime() + validityPeriodMilliSec;
		endDateNew = new Timestamp(endDateMilliSec);
		LogUtil.writeInfoEndDaoLog(VoucherCADAO.class, methodName+"Validity Period---:"+validityPeriod+"--Voucher Code End date---:"+endDateNew,startTime);
		return endDateNew;

	}
	
}
