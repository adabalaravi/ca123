/**
 * 
 */
package com.accenture.ams.db.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.AccountDeviceAMS;
import com.accenture.ams.db.bean.ChannelPlatform;
import com.accenture.ams.db.bean.TechnicalPackage;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

import commontypes.accountmgmt.avs.accenture.CrmAccountDeviceType;

/**
 * @author aditya.madhav.k
 *
 */
public class SdpAccountProfileDAO {

	public static List<AccountDeviceAMS> getAccountDevice(Long userId,String tenantId) throws Exception{
		Session currentSession=null;
		HibernateUtil hibernateUtil=HibernateUtil.getInstance();
		String channelList="";
		List<AccountDeviceAMS> listOfAccountDeviceAMSDTO=new ArrayList<AccountDeviceAMS>();
		long startTime = System.currentTimeMillis();
		try{
			
			currentSession=hibernateUtil.getSessionFactory(tenantId).openSession();
			
			String query = "Select ad  from AccountDeviceAMS ad where ad.user.username = '"+ userId + "'";
			List<AccountDeviceAMS> listOfAccountDeviceAMS = currentSession.createQuery(query).list();
			
			Iterator deviceIterator=listOfAccountDeviceAMS.iterator();
			
			//Create a DTO for all the objects received from DB
			while(deviceIterator.hasNext()){
				AccountDeviceAMS accountDeviceAMSDTO=(AccountDeviceAMS)deviceIterator.next();
				listOfAccountDeviceAMSDTO.add(accountDeviceAMSDTO);
			}
			
			LogUtil.writeInfoEndDaoLog(SdpAccountProfileDAO.class, "getAccountDevice",
					startTime);
			
		}catch(Exception e){
			LogUtil.writeErrorLog(SdpAccountProfileDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountDevice ", e);
			throw new Exception("Error retrieving Account Device Details for id :"+ userId);
		}finally{
			currentSession.close();
		}
		return listOfAccountDeviceAMSDTO;
	}
	
	public static String getAccountAttributeDetails(Long userId,Integer attributeDetailId, String tenantId) throws Exception{
		Session currentSession=null;
		HibernateUtil hibernateUtil=HibernateUtil.getInstance();
		long startTime = System.currentTimeMillis();
		try{
			
			currentSession=hibernateUtil.getSessionFactory(tenantId).openSession();
			
			String query = "Select at  from AccountAttribute at where at.userId = "+ userId + " and at.attributeDetailId="+attributeDetailId;
			AccountAttribute accountAttribute= (AccountAttribute) currentSession.createQuery(query).uniqueResult();
			
			LogUtil.writeInfoEndDaoLog(SdpAccountProfileDAO.class, "getAccountAttributeDetails",
					startTime);
			
			if (accountAttribute!=null)
				return accountAttribute.getAttributeValue();
			return null;
		}catch(Exception e){
			LogUtil.writeErrorLog(SdpAccountProfileDAO.class,
					AccountMgmtServiceConstant.ERROR_BE_3022_DAO_QUERY
							+ " getAccountAttributeDetails ", e);
			throw new Exception("Error retrieving Account Attribute Details for id :"+ userId + " and details Id :"+attributeDetailId);
		}finally{
			currentSession.close();
		}
	
	}
}
