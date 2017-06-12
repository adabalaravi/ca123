package com.accenture.ams.db.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import com.accenture.ams.db.bean.SysMessages;
import com.accenture.ams.db.bean.SysParameter;
import com.accenture.ams.db.framework.SystemMessages;
import com.accenture.ams.db.util.Configurator;
import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;

public class SysParamDAO {

	public static String getSysDate(String tenantId) throws Exception {// recupero la sysdate dal db
		long startTime = System.currentTimeMillis();
		try {
			Session session = HibernateUtil.getInstance().getSessionFactory(tenantId).openSession();
			String query = "select sysdate() from DUAL";
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getSysDate", query);
			Date returnObj = (Date) session.createSQLQuery(query).uniqueResult();
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String out=formatter.format(returnObj);
			LogUtil.writeInfoEndDaoLog(SysParamDAO.class, "getSysDate", startTime);
			LogUtil.writeCommonLog("INFO", SysParamDAO.class, "DB_QUERY", " DATE DI COMPARAZIONE: "+out);
			return(out);
		} catch (Exception e) {
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(SysParamDAO.class, Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getSysDate ", e);
			throw new Exception(Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getSysDate: " + e.getMessage());
		}// Fine try..catch...
	}//End method...

	public static List<SysParameter> getGroupUpdatedParam(String paramLastUpdated, String tenantId) throws Exception {// trovo tutti i parametri per un gruppo, richiama quando vanno aggiornati
		long startTime = System.currentTimeMillis();
		try {
			Session session = HibernateUtil.getInstance().getSessionFactory(tenantId).openSession();
			String where="";
			if(paramLastUpdated!=null && !paramLastUpdated.equalsIgnoreCase("")){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");//yyyyMMddHHmmss
				Date data=formatter.parse(paramLastUpdated);
				SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//yyyyMMddHHmmss
				String out=form.format(data);
				where="	where '"+out+"'<c.updateDate";
			}
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getGroupUpdatedParam", "select c from SysParameter c " +where);
			@SuppressWarnings("unchecked")
			List<SysParameter> returnObj = (List<SysParameter>)session.createQuery("select c " +
															"	from SysParameter c " + where ).list();
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getGroupUpdatedParam", "select c from SysParameter c "+ where);
			LogUtil.writeInfoEndDaoLog(SysParamDAO.class, "getGroupUpdatedParam", startTime);
			return returnObj;
		} catch (Exception e) {
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(SysParamDAO.class, Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getGroupUpdatedParam ", e);
			throw new Exception(Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getGroupUpdatedParam: " + e.getMessage());
		}// Fine try..catch...
	}//End method...

	@SuppressWarnings("unchecked")
	public static List<SysMessages> getMessagesFromLanguage(String language, String paramLastUpdated, String tenantId) throws Exception {
		long startTime = System.currentTimeMillis();
		Session session = null;
		List<SysMessages> lstReturnObj = new ArrayList<SysMessages>();
		try {
			session = HibernateUtil.getInstance().getSessionFactory(tenantId).openSession();
			if (language == null || language.equals("")){
				language = "en";
			}//End if..
			String query = "";
			
			if (paramLastUpdated == null || paramLastUpdated.equals("")){
				query = "	select c " +
						"	from SysMessages c " +
						"	where c.compId.language ='" + language + "'";
			}else{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");//yyyyMMddHHmmss
				Date data=formatter.parse(paramLastUpdated);
				SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//yyyyMMddHHmmss
				String out=form.format(data);
				query = "	select c " +
						"	from SysMessages c " +
						"	where c.compId.language ='" + language + "' "+
						"	AND 	'"+out+"'<c.updateDate";
			}//End if..else..
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getMessagesFromLanguage", query);
			lstReturnObj = (List<SysMessages>)session.createQuery(query).list();
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getMessagesFromLanguage", query);
		} catch (Exception e) {
			LogUtil.writeErrorLog(SysParamDAO.class, Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getMessagesFromLanguage "+paramLastUpdated, e);
			throw new Exception(Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getMessagesFromLanguage: " + e.getMessage());
		} finally {
			session.close();
		}//End try...catch..
		LogUtil.writeInfoEndDaoLog(SysParamDAO.class, "getMessagesFromLanguage", startTime);
		return lstReturnObj;
	}//End method...

	public static List<SysParameter> getInitAllParameters(String tenantId) throws Exception {													
		long startTime = System.currentTimeMillis();
		try {
			Session session = HibernateUtil.getInstance().getSessionFactory(tenantId).openSession();
			@SuppressWarnings("unchecked")
			List<SysParameter> returnObj = session.createQuery("select c from SysParameter c").list();
			LogUtil.writeInfoDaoLog(SysParamDAO.class, "getInitAllParameters", "select c from SysParameter c");
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeInfoEndDaoLog(SysParamDAO.class, "getInitAllParameters", startTime);
			return returnObj;
		} catch (Exception e) {
			HibernateUtil.getInstance().getSessionFactory(tenantId).close();
			LogUtil.writeErrorLog(SysParamDAO.class, Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getInitAllParameters ", e);
			throw new Exception(Configurator.getSystemMessage(tenantId).ERROR_BE_3022_DAO_QUERY + " getInitAllParameters: " + e.getMessage());
		}// Fine try..catch...
	}//End method...
}// Fine classe...