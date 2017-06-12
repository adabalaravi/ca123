package com.accenture.avs.ca.be.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.bean.Channel;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.ca.be.db.bean.UserPurchasesCA;

public class PurchaseTransactionDAOCA
{


	//Added as part of GetEligibleChaanelCA API
	//Fetching the Active Channels from DB
	@SuppressWarnings("unchecked")
	public static List<Channel> getChannelsByID(List<Long> technicalPKGList, TenantConfigurator tenantConfigurator) throws Exception {
		String methodName = "getChannelsByID";
		long startTime = System.currentTimeMillis();
		SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
		Session session = null;
		List<Channel> listChannel=new  ArrayList<Channel>();
		String tenantId = tenantConfigurator.getTenantId();
		Query resultQuery=null;

		if(technicalPKGList!=null && technicalPKGList.size() >0){
			try {
				String query = "select ct.channel from ChannelTechnicalPkg ct " +
						"where ct.technicalPackage IN (:techPackageIds)";

				session = HibernateUtil.getSessionFactory(tenantId).openSession();
				LogUtil.writeInfoDaoLog(PurchaseTransactionDAOCA.class, methodName, query);

				resultQuery =  session.createQuery(query);

				listChannel= resultQuery.setParameterList("techPackageIds",technicalPKGList).list();
				LogUtil.writeInfoDaoLog(PurchaseTransactionDAOCA.class, methodName,resultQuery.toString());

				//this set created  for remove the duplication of channels
				HashSet<Channel> distinctChannels=new HashSet<Channel>(listChannel);
				listChannel.clear();	             
				listChannel.addAll(distinctChannels);

			} catch (Exception e) {
				LogUtil.writeErrorLog(PurchaseTransactionDAOCA.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getChannelsByID", e);
				HibernateUtil.getSessionFactory(tenantId).close();
				throw e;
			}
			finally
			{
				HibernateUtil.getSessionFactory(tenantId).close();
				LogUtil.writeInfoEndDaoLog(PurchaseTransactionDAOCA.class, methodName, startTime);
			}
		}
		return listChannel;

	}

	//Fetching the Active technical packages for the user.
	@SuppressWarnings("unchecked")
	public static List<UserPurchasesCA> getPurchaseTransactionHistory(Integer userid, TenantConfigurator tenantConfigurator)
			throws Exception
			{
		String methodName="getPurchaseTransactionHistory";
		long startTime = System.currentTimeMillis();
		SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
		Session session = null;
		List<UserPurchasesCA> returnObj = null;
		String tenantId = tenantConfigurator.getTenantId();
		try
		{
			session = HibernateUtil.getSessionFactory(tenantId).openSession();
			Long user_id = new Long(userid.longValue());
			Query query = null;

			query = session.getNamedQuery("getActiveSubscriptionDetailsCA");
			query.setLong(0, user_id.longValue());

			returnObj = query.list();
			if ((returnObj != null) && (returnObj.size() > 0)) {
				return returnObj;
			}
			HibernateUtil.getSessionFactory(tenantId).close();
		}
		catch (Exception e)
		{
			LogUtil.writeErrorLog(PurchaseTransactionDAOCA.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getPurchaseHistory", e);
			HibernateUtil.getSessionFactory(tenantId).close();
			throw e;
		} finally
		{
			HibernateUtil.getSessionFactory(tenantId).close();
			LogUtil.writeInfoEndDaoLog(PurchaseTransactionDAOCA.class, methodName, startTime);
		}
		return returnObj;
			}


}
