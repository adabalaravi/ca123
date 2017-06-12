package com.accenture.ams.db.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.hibernate.Session;

import com.accenture.ams.db.bean.AccountUser;
import com.accenture.ams.db.bean.Content;
import com.accenture.ams.db.bean.LivePPV;
import com.accenture.ams.db.bean.TechnicalPackage;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.getpricebyuserservice.GetPriceByUserServiceConstant;
import com.accenture.ams.getpricebyuserservice.GetPriceByUserServiceEnum;

public class GetPriceByUserDAO {

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

			LogUtil.writeInfoDaoLog(GetPriceByUserDAO.class, "isUserIdPresentAccountUser", query);

			if (userName != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserDAO.class, GetPriceByUserServiceConstant.ERROR_BE_3022_DAO_QUERY + " isUserIdPresentAccountUser ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving userId:" + userId);
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(GetPriceByUserDAO.class, "isUserIdPresentAccountUser", startTime);
		return ret;
	}


	/**
	 * 
	 * @param operationType
	 * @param itemId
	 * @param tenantName
	 * @return
	 * @throws Exception
	 */
	public static boolean isItemIdPresent(GetPriceByUserServiceEnum operationType, String itemId, String tenantName) throws Exception {
		Session session = null;
		String query = null;
		String sItemId = null;
		boolean ret = false;
		long startTime = System.currentTimeMillis();
		java.util.Date today = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentDate = formatter.format(today);		
		
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantName).openSession();

			switch (operationType.getValue()) {
				case 0:	query = "Select lv from LivePPV lv where lv.ppvId = " + itemId;
				        LogUtil.writeInfoDaoLog(GetPriceByUserDAO.class, "isItemIdPresent", query);
						LivePPV livePPV = (LivePPV) session.createQuery(query).uniqueResult();
						if(livePPV != null){
							sItemId = livePPV.getPpvId().toString();
						}
					break;
					
				case 1: query = "Select c from Content c where c.contentId = " + itemId + " and c.contractStart < '" + currentDate + "' and c.contractEnd > '" + currentDate + "'";
						LogUtil.writeInfoDaoLog(GetPriceByUserDAO.class, "isItemIdPresent", query);
				        Content content = (Content)session.createQuery(query).uniqueResult();
						if (content != null){
							sItemId = content.getContentId().toString();
						}				
					break;
					
				case 2: query = ""; //TODO da aggiungere la query specifica per l'operazione getProductPrice
				        LogUtil.writeInfoDaoLog(GetPriceByUserDAO.class, "isItemIdPresent", query);
				        sItemId = "1"; //TODO da settare il valore ritornato dalla query
					break;
					
				case 3: query = "Select t from TechnicalPackage t where t.packageId = " + itemId;
						LogUtil.writeInfoDaoLog(GetPriceByUserDAO.class, "isItemIdPresent", query);
						TechnicalPackage technicalPackage = (TechnicalPackage) session.createQuery(query).uniqueResult();
						if (technicalPackage != null){
							sItemId = technicalPackage.getPackageId().toString();
						}
					break;
			}	


			if (sItemId != null) {
				ret = true;
			}// Fine if...
		} catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserDAO.class, GetPriceByUserServiceConstant.ERROR_BE_3022_DAO_QUERY + " isItemIdPresent ", e);
			HibernateUtil.getInstance().getSessionFactory(tenantName).close();
			throw new Exception("Error retrieving itemId:" + itemId);
		}// Fine try..catch...

		HibernateUtil.getInstance().getSessionFactory(tenantName).close();
		LogUtil.writeInfoEndDaoLog(GetPriceByUserDAO.class, "isItemIdPresent", startTime);
		return ret;
	}
	
}
