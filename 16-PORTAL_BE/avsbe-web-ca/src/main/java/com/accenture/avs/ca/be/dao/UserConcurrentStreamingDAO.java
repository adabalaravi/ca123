package com.accenture.avs.ca.be.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.ca.be.db.bean.UserConcurrentStreamingCA;
import com.accenture.avs.ca.be.util.ReportUtilCA;

public class UserConcurrentStreamingDAO {

	public static Logger logger = Logger.getLogger(UserConcurrentStreamingDAO.class);
	//Fetching the Active technical packages for the user.
		@SuppressWarnings("unchecked")
		public static List<UserConcurrentStreamingCA> getUserConcurrentStreamList(String crmAccountId, TenantConfigurator tenantConfigurator) throws Exception {
			String methodName = "getUserConcurrentStreamList";
			List<UserConcurrentStreamingCA> userConcurrentStreamingCAList=null;
			//SdpVoucherView voucher = null;
			long startTime = System.currentTimeMillis();

			String tenantId = tenantConfigurator.getTenantId();
			SystemMessages systemMessages = tenantConfigurator.getSystemMessages();

			Session session = HibernateUtil.getSessionFactory(tenantId).openSession();

			LogUtil.commonDebugLog(logger, methodName, "PARAM crmAccountId: " + crmAccountId);
			try {
				Query queryObj = null;
				String query ="select v from UserConcurrentStreamingCA v where v.crmAccountId =?";// and v.is_disabled=? ";
				//String query = "select uc.crm_account_id from UserConcurrentStreamingCA uc where uc.crm_account_id = ?";
				queryObj = session.createQuery(query);
				queryObj.setString(0, crmAccountId);
				//queryObj.setString(1, "FALSE");
				userConcurrentStreamingCAList = queryObj.list();

				LogUtil.commonDebugLog(logger, methodName, query);
				} catch (Exception e) {					
					LogUtil.writeErrorLog(UserConcurrentStreamingDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getUserConcurrentStreamList - ", e);
				    throw e;
			} finally {
				session.flush();
				HibernateUtil.getSessionFactory(tenantId).close();
			}
			LogUtil.writeInfoEndDaoLog(UserConcurrentStreamingDAO.class, methodName+" --get User Concurrent Stream List Sucessfully---", startTime);
			return userConcurrentStreamingCAList;
		}
		
		public static boolean insertUserStreaming(UserConcurrentStreamingCA userConcurrentStreamingCA, TenantConfigurator tenantConfigurator) throws Exception {
			long startTime = System.currentTimeMillis();
			String methodName = "insertUserStreaming";
			Session session = null;
			SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
			String tenantId = tenantConfigurator.getTenantId();
			boolean returnFlag = false;
			try {
				if (logger.isDebugEnabled()) {
					if (userConcurrentStreamingCA != null)
						LogUtil.commonDebugLog(logger, methodName, "INPUT PARAMETERS." + userConcurrentStreamingCA.getCrmAccountId());
					else
						LogUtil.commonDebugLog(logger, methodName, "INPUT PARAMETERS. AccountDevice is null");
				}
				session = HibernateUtil.getSessionFactory(tenantId).openSession();
				session.beginTransaction();
				session.save(userConcurrentStreamingCA);
				LogUtil.writeInfoDaoLog(logger, "insertAccountDevice", systemMessages.INFO_BE_3026_DAO_EXECUPDATE);
				session.getTransaction().commit();
				long execTime = System.currentTimeMillis() - startTime;
				LogUtil.writeInfoEndDaoLog(UserConcurrentStreamingDAO.class, "insertUserStreamingCount", execTime, "insertUserStreamingCount");
				returnFlag = true;
				
			} catch (Exception e) {
				session.getTransaction().rollback();				
				LogUtil.writeErrorLog(UserConcurrentStreamingDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " insertUserStreaming - ", e);
				throw e;
			} finally {
				LogUtil.writeInfoDaoLog(logger, methodName, (System.currentTimeMillis() - startTime));
				session.flush();
				HibernateUtil.getSessionFactory(tenantId).close();
			}
			
			return returnFlag;
		}
			
		
		
		@SuppressWarnings("unchecked")
		public static boolean deleteSessionFromListener(String sessionHttpID,String tenantId,String status) throws Exception{
			 Session session = null;
			 boolean returnFlag = false;
			 //SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
				 try {			 
					 LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, "deleteSessionFromListener - sessionHttpID:"+sessionHttpID, "");
			         session = HibernateUtil.getSessionFactory(tenantId).openSession();	
			         session.getTransaction().begin();
			         Query query;
			         
			         query = session.createQuery("select st from UserConcurrentStreamingCA st where st.sessionid=?");		         
			         
			          query.setParameter(0, sessionHttpID);
			         
			          List<UserConcurrentStreamingCA> userConcurrentStreamingTestList= query.list();	          
		        	  if(userConcurrentStreamingTestList !=null && userConcurrentStreamingTestList.size() > 0)
		        	  {
		        		 for(int i=0; i < userConcurrentStreamingTestList.size(); i++)
		        		 {
		        			 UserConcurrentStreamingCA userConcurrentStreamingCA = userConcurrentStreamingTestList.get(i);
		        			 session.delete(userConcurrentStreamingCA);
		        			 String channel=userConcurrentStreamingCA.getChannel();
		        			 String device=userConcurrentStreamingCA.getDeviceType();
		        			 String userName = userConcurrentStreamingCA.getCrmAccountId();
		        			 session.delete(userConcurrentStreamingCA);	
		        			 LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, "deleteSessionFromListener -details from DB -  sessionHttpID:"+userConcurrentStreamingCA.getSessionid() + "  -  crmAccountId:"+userConcurrentStreamingCA.getCrmAccountId() + " - device:"+userConcurrentStreamingCA.getDeviceType() +" - channel:"+userConcurrentStreamingCA.getChannel(), "");
		    		         sendUserConcurrentStreamingStatusReport(userName,sessionHttpID,device,channel,status);
		        		 }
		        	 }	     	        	 
		        	 session.getTransaction().commit();	 
		        	 returnFlag = true;		         
				 } catch (Exception e) {
					 session.getTransaction().rollback();			         
			         LogUtil.writeErrorLog(UserConcurrentStreamingDAO.class, "deleteSessionFromListener DAO EXCEPTION - " + " deleteUserSession", e);			        
			         returnFlag = false;
			    }finally{
			    	session.flush();
			    	HibernateUtil.getSessionFactory(tenantId).close();
			    }	
				 return returnFlag;
		}	
		
		
		//common method for StopContentCA & ClearUserSessions API to delete the session based on the condition for StopContentCA.
		@SuppressWarnings("unchecked")
		public static boolean deleteUserSession(String crmAccountId, String sessionHttpID, boolean flag, String status,TenantConfigurator tenantConfigurator) throws Exception{
			 Session session = null;
			 String tenantId = tenantConfigurator.getTenantId();
			 SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
			 String device;
			 String channel;
			 
			 boolean returnFlag = false;
				 try {			 
					 LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, "deleteUserSession - sessionHttpID:"+sessionHttpID + "  -  crmAccountId:"+crmAccountId, "");
					 
			         session = HibernateUtil.getSessionFactory(tenantId).openSession();	
			         session.getTransaction().begin();
			         Query query;
			         if(flag)
			        	 query = session.createQuery("select st from UserConcurrentStreamingCA st where st.crmAccountId=?");
			         else
			        	 query = session.createQuery("select st from UserConcurrentStreamingCA st where st.crmAccountId=? and st.sessionid=?");	
			         
			         query.setParameter(0,crmAccountId);
			         if(!flag)
			          query.setParameter(1, sessionHttpID);
			         
			          List<UserConcurrentStreamingCA> userConcurrentStreamingTestList= query.list();
			          
			          LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, "deleteUserSession - userConcurrentStreamingTestList size:"+userConcurrentStreamingTestList.size(), "");
			          
			          
		        	  if(userConcurrentStreamingTestList !=null && userConcurrentStreamingTestList.size() > 0)
		        	  {
		        		 for(int i=0; i < userConcurrentStreamingTestList.size(); i++)
		        		 {
		        			 UserConcurrentStreamingCA userConcurrentStreamingCA = userConcurrentStreamingTestList.get(i);
		        			 device=userConcurrentStreamingCA.getDeviceType();
		        			 channel=userConcurrentStreamingCA.getChannel();
		        			 sessionHttpID=userConcurrentStreamingCA.getSessionid();
		        			 session.delete(userConcurrentStreamingCA);	
		        			 LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, "deleteUserSession -details from DB -  sessionHttpID:"+userConcurrentStreamingCA.getSessionid() + "  -  crmAccountId:"+userConcurrentStreamingCA.getCrmAccountId() + " - device:"+userConcurrentStreamingCA.getDeviceType() +" - channel:"+userConcurrentStreamingCA.getChannel(), "");
		        			 sendUserConcurrentStreamingStatusReport(crmAccountId,sessionHttpID,device,channel,status);
		        		 }
		        	 }	     	        	 
		        	 session.getTransaction().commit();	 
				     
				     returnFlag = true;
				 } catch (Exception e) {
					 session.getTransaction().rollback();			         
			         LogUtil.writeErrorLog(UserConcurrentStreamingDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " deleteUserSession", e);
			        throw e;
			         
			    }finally{
			    	session.flush();			    	
			    	HibernateUtil.getSessionFactory(tenantId).close();
			    }
				 
				 return returnFlag;
		}	
		@SuppressWarnings("unchecked")
		public static List<Long> getTechnicalPkgIdsByChannel(String channels, TenantConfigurator tenantConfigurator) throws Exception {
			String methodName = "getTechnicalPkgIds";
			long startTime = System.currentTimeMillis();
			SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
			Session session = null;
			List<Long> techPkgList = new ArrayList<Long>();
			String tenantId = tenantConfigurator.getTenantId();
			Query resultQuery=null;

			if(channels!=null){
				try {
					String query = "select ct.compId.packageId from ChannelTechnicalPkg ct " +
							"where ct.channel IN (:channels)";

					session = HibernateUtil.getSessionFactory(tenantId).openSession();
					LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, methodName, query);

					resultQuery =  session.createQuery(query);

					techPkgList= resultQuery.setParameter("channels",channels).list();
					LogUtil.writeInfoDaoLog(UserConcurrentStreamingDAO.class, methodName,resultQuery.toString());
					

				} catch (Exception e) {
					LogUtil.writeErrorLog(UserConcurrentStreamingDAO.class, systemMessages.ERROR_BE_3022_DAO_QUERY + " getTechnicalPkgIds", e);					
					throw e;
				}
				finally
				{
					session.flush();
					HibernateUtil.getSessionFactory(tenantId).close();					
				}
			}
			LogUtil.writeInfoEndDaoLog(UserConcurrentStreamingDAO.class, methodName, startTime);
			return techPkgList;

		}
		public static void sendUserConcurrentStreamingStatusReport(String crmAccounId,String sessionId,String deviceType,String channel ,String fromSource) throws Exception {
			try {
			
					ReportUtilCA report = new ReportUtilCA();
					//sendUserConcurrentStreamingStatusReport(methodName,userConcurrentStreamingCA.getSno(),userConcurrentStreamingCA.getCreation_date(),profile.getCrmAccountId(),userConcurrentStreamingCA.getSessionid(),userConcurrentStreamingCA.getDeviceType(),userConcurrentStreamingCA.getChannel());		
					LogUtil.commonInfoLog(logger, "sendUserConcurrentStreamingStatusReport ", "LOG VALUES crmAccounId | "+ crmAccounId +" | sessionId:"+ sessionId +" | deviceType :"+deviceType+" | channel :"+channel);
					report.sendUserConcurrentStreamingStatusReport(
							crmAccounId, 
							sessionId, 
							deviceType, 	
							channel,fromSource);
												
				
			} catch (Exception e) {
				//LogUtil.errorLog(logger, systemMessages.ERROR_BE_ACTION_3128_ERRSENDREPORTSYSLOG, e);
				throw e;

			}
			
		}
		//Getting all the session for the provided user for - FetchUserSessions API 
		@SuppressWarnings("unchecked")
		public static List<UserConcurrentStreamingCA> getUserAllSession(String email, TenantConfigurator tenantConfigurator) throws Exception {
			String methodName = "getUserAllSession";			
			List<UserConcurrentStreamingCA> userConcurrentStreamingCAList=null;
			long startTime = System.currentTimeMillis();
			String tenantId = tenantConfigurator.getTenantId();
			Session session = HibernateUtil.getSessionFactory(tenantId).openSession();
			LogUtil.commonDebugLog(logger, methodName, "PARAM Email: " + email);
			try {
				Query queryObj = null;
				String query ="select v from UserConcurrentStreamingCA v where v.crmAccountId =?";				
				queryObj = session.createQuery(query);
				queryObj.setString(0, email);				
				userConcurrentStreamingCAList = queryObj.list();
				LogUtil.commonDebugLog(logger, methodName, query);
			} catch (Exception e) {
					e.printStackTrace();
				LogUtil.errorLog(logger, " email Id is not present: ", e);
				throw new Exception(" email Id is not present: " + e.getMessage());
			} finally {
				session.flush();		    	
				HibernateUtil.getSessionFactory(tenantId).close();
			}
			LogUtil.writeInfoEndDaoLog(UserConcurrentStreamingDAO.class, methodName+" Session Id's Sucessfully Fetched", startTime);
			return userConcurrentStreamingCAList;
		}
}
