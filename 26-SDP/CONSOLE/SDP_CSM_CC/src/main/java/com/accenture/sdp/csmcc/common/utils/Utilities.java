package com.accenture.sdp.csmcc.common.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.beans.TenantBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.beans.Param;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.OperatorConverter;
import com.accenture.sdp.csmcc.services.OperatorService;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchTenantResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.TenantInfoResp;

public final class Utilities {

	private static LoggerWrapper log = new LoggerWrapper(Utilities.class);

	private Utilities() {

	}

	public static String getCurrentMethodName() {
		StackTraceElement stackTraceElements[] = (new Exception()).getStackTrace();
		return stackTraceElements[1].getMethodName();
	}

	public static String getCurrentClassAndMethod() {
		StackTraceElement stackTraceElements[] = (new Exception()).getStackTrace();
		StackTraceElement caller = stackTraceElements[1];
		return String.format("%s.%s", caller.getClassName().substring(caller.getClassName().lastIndexOf(".") + 1), caller.getMethodName());
	}

	public static String trim(String s) {
		if (s == null) {
			return s;
		}
		return s.trim();
	}

	public static boolean isEmptyString(String s) {
		return (s == null || s.trim().length() == 0);
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		if (date == null) {
			return null;
		}

		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String timezone = propertyManager.getProperty(ApplicationConstants.TIMEZONE);
		GregorianCalendar dateCalendar = new GregorianCalendar(TimeZone.getTimeZone(timezone));

		dateCalendar.setTime(date);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateCalendar);
	}
	
//	public static void convertToEndOfDate(XMLGregorianCalendar date) {
//		date.setHour(23);
//		date.setMinute(59);
//		date.setSecond(59);
//		date.setMillisecond(0);
//	}

	public static Date getDateFromGregorianCalendar(XMLGregorianCalendar date) {
		if (date == null) {
			return null;
		}
		return date.toGregorianCalendar().getTime();

	}

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		return df.format(date);
	}

	public static Date getDateFromShortString(String dateToParse) {
		Date result = null;
		if (isEmptyString(dateToParse)) {
			return null;
		}
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			result = (Date) formatter.parse(dateToParse.replace('-', '/'));
		} catch (ParseException e) {
			log.logException(e.getMessage(), e);
		}

		return result;
	}

	public static <T> T findBean(String managedBeanName, Class<T> beanClass) {
		FacesContext context = FacesContext.getCurrentInstance();
		return beanClass.cast(context.getApplication().evaluateExpressionGet(context, "#{" + managedBeanName + "}", beanClass));
	}

	public static String parseErrorParameter(String errorCode, List<String[]> parameters) {
		String desc = MessageConstants.ERROR_COMPOSITE_CODE + errorCode + MessageConstants.ERROR_COMPOSITE_DESC;
		String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, desc);
		if (errorCode.equals(ApplicationConstants.CODE_MANDATORY_FIELD_MISSING)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_START_POSITION_OUT_OF_RANGE)) {
			return mex;
		} else if (errorCode.equals(ApplicationConstants.CODE_VALUE_NOT_ALLOWED)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_DUPLICATE_ENTRY)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_GENERIC_ERROR)) {
			return mex;
		} else if (errorCode.equals(ApplicationConstants.CODE_ZERO_RECORD_FOUND)) {
			return mex;
		} else if (errorCode.equals(ApplicationConstants.CODE_ELEMENT_NOT_FOUND)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_ELEMENT_ALREADY_ASSOCIATED)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_DELETE_FAILED)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_STATUS_TRANSACTION_ERROR)) {
			String from = "";
			String to = "";
			for (String[] param : parameters) {
				if (param[0].equals("fromStatus")) {
					from = param[1];
				}
				if (param[0].equals("toStatus")) {
					to = param[1];
				}
			}
			mex = String.format(mex, from, to);
		} else if (errorCode.equals(ApplicationConstants.CONSTRAINT_VIOLATED)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else {
			mex = errorCode;
		}

		return mex;
	}

	public static String getMessage(String bundleName, String param) {
		Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle messages = ResourceBundle.getBundle(bundleName, currentLocale);
		return messages.getString(param);
	}

	public static String getDefaultMessage(String bundleName, String name) {
		Locale defaultLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		ResourceBundle messages = ResourceBundle.getBundle(bundleName, defaultLocale);
		return messages.getString(name);
	}

	public static int compareStringIgnoreCase(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) {
			return 0;
		} else if (s1 != null) {
			return s1.compareToIgnoreCase(s2);
		}
		return -1;
	}

	public static int compareDate(Date d1, Date d2) {
		if ((d1 != null) && (d2 != null)) {
			return d1.compareTo(d2);
		} else if (d1 != null) {
			return 1;
		} else if (d2 != null) {
			return -1;
		} else {
			return 0;
		}
	}

	public static Holder<String> getTenantName() {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		return infoSessionBean.getTenant();
	}

	public static List<Param> readProfile(String profile) {
		ArrayList<Param> profileList = new ArrayList<Param>();
		if (!isEmptyString(profile)) {
			String[] params = profile.split(";");
			for (String param : params) {
				if (!isEmptyString(param)) {
					String[] values = param.split("=");
					if (values.length > 1) {
						profileList.add(new Param(values[0], values[1]));
					} else if (!isEmptyString(values[0])) {
						profileList.add(new Param(values[0], null));
					}
				}
			}
		}
		return profileList;

	}

	public static String writeProfile(List<Param> paramList) {
		if (paramList == null || paramList.size() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (Param param : paramList) {
			buffer.append(getProfileElement(param));
		}
		return buffer.toString();

	}

	private static String getProfileElement(Param param) {
		if (param == null || param.getName() == null || param.getValue() == null) {
			return "";
		}
		String result = param.getName() + "=";
		if (param.getValue() != null) {
			result += param.getValue();
		}
		result += ";";
		return result;
	}

	public static List<TenantBean> getAllTenants() throws ServiceErrorException {

		List<TenantBean> returnList = new ArrayList<TenantBean>();

		OperatorService service = Utilities.findBean(ApplicationConstants.OPERATOR_SERVICE_BEAN, OperatorService.class);
		SearchTenantResp response = service.selectAllTenants();
		if (response.getTenants().getTenant() != null) {
			for (TenantInfoResp t : response.getTenants().getTenant()) {
				returnList.add(OperatorConverter.convertTenantInfoToBean(t));
			}
		}
		return returnList;

	}

	public static String getPageUrl(String name) {
		ResourceBundle messages = ResourceBundle.getBundle(ApplicationConstants.PAGE_URL_BUNDLE);
		return messages.getString(name);
	}

	public static void addSoapHandler(BindingProvider port, String urlString) {
		BindingProvider bp = port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
		Binding binding = bp.getBinding();
		// Add the logging handler
		List handlerList = binding.getHandlerChain();
		if (handlerList == null) {
			handlerList = new ArrayList();
		}
		LoggingHandler loggingHandler = new LoggingHandler();
		handlerList.add(loggingHandler);
		binding.setHandlerChain(handlerList);
	}

	public static String getString(BigDecimal value) {
		String result = null;
		if (value != null) {
			return value.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
		}
		return result;

	}

}
