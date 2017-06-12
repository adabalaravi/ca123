package com.accenture.ams.db.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * USE: 
 * 1) chiamare i metodi di tracciamento log passando la classe che necessita di tracciamento
 * @author Christian
 *
 */
public class LogUtil {

	private static Logger log = Logger.getLogger("com.accenture.ams");

	private static String printException(Exception e)
	{
		StringWriter sw = new StringWriter();					
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static void writeErrorLog(Class className,String message,Exception e){

		if(className == null || !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

		log.error(message+" | "+printException(e));
	}

	/**
	 * Questo metodo stampa sul log le informazioni relative alle chiamate dei DAO:
	 * USE: 
	 * 1)chiamarlo appena si recupera la query
	 * @param methodName
	 * @param query
	 */
	public static void writeInfoDaoLog(Class className,String methodName,String query){

		if(className == null || !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

		String logToWrite = "START | CALL TYPE: DB_QUERY | METHOD NAME: "+methodName;

		if(query != null && !query.equals(""))
			logToWrite = logToWrite +" | QUERY: "+query;

		log.info(logToWrite);
	}

	/**
	 * Questo metodo stampa sul log le informazioni relative alla chiusura dei metodi del DAO:
	 * USE: 
	 * 1)chiamarlo subito dopo aver recuperato il resultset della query 
	 * e valorizzare la variabile startTime appena si entra
	 * @param methodName
	 * @param startTime
	 */
	public static void writeInfoEndDaoLog(Class className,String methodName,long startTime){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

		long executionTime=System.currentTimeMillis()-startTime;
		log.info("END | CALL TYPE: DB_QUERY | METHOD NAME: "+methodName +" | EXECUTION TIME: "+executionTime+"(ms)");
	}
	public static void profileDao(Class className,String methodName,long executionTime){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

	
		log.info("END | CALL TYPE: DB_QUERY | METHOD NAME: "+methodName +" | EXECUTION TIME: "+executionTime+"(ms)");
	}
	public static void profileMethod(Class className,String methodName,String message,long executionTime){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

	
		log.info("END | CALL TYPE: PROFILING...... | METHOD NAME: "+methodName +" | MESSAGE : "+message+" | EXECUTION TIME: "+executionTime+"(ms)");
	}
	public static void profileAPI(Class className,String methodName,long executionTime){
	
		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

	
		log.info("END | CALL TYPE:  | API NAME: "+methodName +" | EXECUTION TIME: "+executionTime+"(ms)");
	}
	public static void profileJsonParser(Class className,String methodName,long executionTime){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

	
		log.info("END | CALL TYPE: DB_QUERY | METHOD NAME: "+methodName +" | EXECUTION TIME: "+executionTime+"(ms)");
	}
	/**
	 * It writes into AVSPortalBE.log information about query executed on Db.
	 * Input parameter:
	 * 
	 * @param methodName
	 * @param executionTime
	 * @param queryName
	 * @param className
	 */
	public static void writeInfoEndDaoLog(Class className,String methodName,long executionTime,String queryName){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);


		log.info("END | CALL TYPE: DB_QUERY | METHOD NAME: "+methodName +" | QUERY NAME: "+queryName+" | EXECUTION TIME: "+executionTime+"(ms)");
	}

	/** 
	 * Questo metodo puo essere invocato dalla classe BackEndServletController 
	 * alla fine dell'execute,stampa le informazioni relative alla chiamata e 
	 * il tempo di esecuzione della stessa
	 * @param azioneInvocata
	 * @param parameters (lista di parametri passati dalla URL)
	 * @param transactionNumber (invocare il metodo getTransactionNumber della classe SessionUtil)
	 * @param startTime (long recuperato all'inizio del metodo chiamante)
	 * @param crmAccountId (recuperato dalla sessione : ConstantsParameter.USERPROFILE) se vuoto viene sostituito con ANONYMOUS)
	 */
	public static void writeInfoActionLog(Class className,String azioneInvocata,Map<Object, Object> parameters,String transactionNumber,long startTime,String crmAccountId){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);
		
		String strparameters = "";

		Set list  = parameters.keySet();
		Iterator iter = list.iterator();

		while(iter.hasNext()) {
			Object key = iter.next();
			Object value = parameters.get(key);

			if(value == null || value.equals(""))
				strparameters = strparameters+key+"=N/A, ";
			else{
				if(key != null && key.equals("password"))
					strparameters = strparameters+key+"="+"###############"+", ";
				else
					strparameters = strparameters+key+"="+((String[])value)[0]+", ";
			}
		}

		if(!strparameters.isEmpty())
			strparameters = strparameters.substring(0, strparameters.length()-2);

		if(crmAccountId == null || crmAccountId.equals(""))
			crmAccountId = "ANONYMOUS";

		long executionTime=System.currentTimeMillis()-startTime;
		log.info("CALL TYPE: INTERNAL | API: " + azioneInvocata + " | PARAMETERS: " + strparameters +" | EXECUTION TIME: " + executionTime+"(ms)");
	}

	/**
	 * Metodo generico di log
	 * @param logLevel = INFO,DEBUG,WARN se vuoto scrive in INFO
	 * @param className
	 * @param callType = EXTERNAL/INTERNAL/DB_QUERY
	 * @param message
	 */
	public static void writeCommonLog(String logLevel,Class className,String callType,String message){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

		if(logLevel == null || logLevel.equals(""))
			log.info("CALL TYPE: "+callType+" | "+message);
		else {
			if(logLevel != null && logLevel.equals("INFO"))
				log.info("CALL TYPE: "+callType+" | "+message);

			else if(logLevel != null && logLevel.equals("DEBUG"))
				log.debug("CALL TYPE: "+callType+" | "+message);

			else if(logLevel != null && logLevel.equals("WARN"))	
				log.warn("CALL TYPE: "+callType+" | "+message);

			else if(logLevel != null && logLevel.equals("ERROR"))	
				log.error("CALL TYPE: "+callType+" | "+message);

			else if(logLevel != null && logLevel.equals("FATAL"))	
				log.fatal("CALL TYPE: "+callType+" | "+message);

			else // default value se viene scritto diversamente
				log.info("CALL TYPE: "+callType+" | "+message);
		}		
	}

	/**
	 * Metodo per il log delle chiamate a componenti esterni (WS,RMI)
	 * Il parametro startTime serve per la chiamata finale alla funzione esterna,
	 * con il -1 non viene fatto append del timing sul log
	 * @param className 
	 * @param externalComponent 
	 * @param message
	 * @param startTime 
	 */
	public static void writeInfoExternalCallLog(Class className,String externalComponent,String message,long startTime){

		if(className != null && !log.getName().equals(className.getName()))
			log = Logger.getLogger(className);

		long executionTime = 0;
		if(startTime > -1)
			executionTime=System.currentTimeMillis()-startTime;

		if(startTime > -1)
			log.info("CALL TYPE: EXTERNAL | EXTERNAL COMPONENT CALL: "+externalComponent+" | MESSAGE: "+message+ " | EXECUTION TIME: "+executionTime+"(ms)");
		else
			log.info("CALL TYPE: EXTERNAL | EXTERNAL COMPONENT CALL: "+externalComponent+" | MESSAGE: "+message);
	}

}
