package com.accenture.avs.ca.be.listener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.accenture.avs.be.db.bean.Profile;
import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.HibernateUtil;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.util.Constants;
import com.accenture.avs.ca.be.dao.UserConcurrentStreamingDAO;
 

/**
 * @author siril.babu.nalluri
 * 
 * Note: Implemented the new session Listener mechanism for the following cases.
	Following are the cases where Frontend Apps/Web Applications unable to communicate with AVS.
•	App/Browser crashes.
•	network failures
•	Any other Situations.
If respective users streaming details are already with AVS, so in above cases user cannot be able to use other device (3rd Device) to watch the streaming.
SOLUTION:  
•	For each and every login JBoss will create a new Session and it will be last for 30 minutes. 
•	If a user Session is idle (in above scenarios) for 30 minutes then Jboss will invalidate/nullify the user session.
•	AVS will catch that event & will delete the respective user Session  details from DB (After 30 minutes user can use the other device to watch the streaming)
NOTE: 
•	With the Above scenarios - user has to wait for 30 minutes to watch the streaming using another (3rd  ) Device.
•	This 30 min value is configurable, but to change this value, AVS deployment is required.
•	But, this value can only be reduced to 20 minutes and not below than this.

NOTE: Existed Sessions to be deleted manually from DB,in case of if all JBoss restarted.

 *
 */
public class StopContentCAListener implements HttpSessionListener{
	

	private HttpSession session = null;
	Profile profile = null;
	protected SystemMessages systemMessages = null;
	private static Logger logger = Logger.getLogger(StopContentCAListener.class);
	//Session will be created automatically.
	public void sessionCreated(HttpSessionEvent event)  {
	String methodName="sessionCreated";
	session = event.getSession();
	LogUtil.commonInfoLog(logger, methodName,"New Session is created----"+session.getId());
	//System.out.println("----------------craeted ");
	
	}
	//Session will be destroyed based on time-out period automatically.
	public void sessionDestroyed(HttpSessionEvent event) {
		String methodName="sessionDestroyed";
		session = event.getSession();
		profile = (Profile) session.getAttribute(Constants.USERPROFILE);
		//"LISTENERSTOPPED" flag used for deleted by StopContentCAListener API and this flag is used for ConcurrentStreamingStatusReport .
		//"LOGOUT" flag used for if there is no profile and this flag is used for ConcurrentStreamingStatusReport.
		String status = "LISTENERSTOPPED";
		try {	
			if(profile==null)
			  status="LOGOUT";
			boolean flag = UserConcurrentStreamingDAO.deleteSessionFromListener(session.getId(),HibernateUtil.TENANT_DAFULT,status);
			if(flag)
				LogUtil.commonInfoLog(logger, methodName,"Session is deleted sucessfully----"+session.getId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.errorLog(logger, this.systemMessages.ERROR_BE_ACTION_3021_EXECUTE_REQUEST_ERROR, e);
		}
		
		
	}
 
}