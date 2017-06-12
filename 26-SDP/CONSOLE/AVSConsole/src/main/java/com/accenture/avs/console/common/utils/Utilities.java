package com.accenture.avs.console.common.utils;

import java.text.SimpleDateFormat;
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
import javax.xml.ws.Holder;

import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.constants.MessageConstants;
import com.accenture.avs.console.controllers.SessionController;

public abstract class Utilities {

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

	public static boolean isNull(String s) {
		return (s == null || s.length() == 0);
	}

	public static boolean isEmptyString(String s) {
		return (s == null || s.trim().length() == 0);
	}

	public static Date getDate(XMLGregorianCalendar date) {
		if (date == null) {
			return null;
		}
		return date.toGregorianCalendar().getTime();
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		if (date == null) {
			return null;
		}
		GregorianCalendar dateCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		dateCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateCalendar);
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		return df.format(date);
	}

	public static <T> T findBean(String managedBeanName, Class<T> beanClass) {
		FacesContext context = FacesContext.getCurrentInstance();
		return beanClass.cast(context.getApplication().evaluateExpressionGet(context, "#{" + managedBeanName + "}", beanClass));
	}

	public static String parseErrorParameter(String errorCode, List<String[]> parameters) {
		String desc = MessageConstants.ERROR_COMPOSITE_CODE + errorCode + MessageConstants.ERROR_COMPOSITE_DESC;
		String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, desc);
		if (isNull(mex)) {
			mex = errorCode;
		} else if (errorCode.equals(ApplicationConstants.CODE_MANDATORY_FIELD_MISSING)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_VALUE_NOT_ALLOWED)) {
			mex = String.format(mex, parameters.get(0)[0]);
		} else if (errorCode.equals(ApplicationConstants.CODE_DUPLICATE_ENTRY)) {
			mex = String.format(mex, parameters.get(0)[0]);
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
		} else if (errorCode.equals(ApplicationConstants.CODE_CONSTRAINT_VIOLATED)) {
			mex = String.format(mex, parameters.get(0)[0]);
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
		}
		return 0;
	}

	public static Holder<String> getTenantName() {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
		return new Holder<String>(infoSessionBean.getTenantName());
	}

	public static boolean isValidNow(Date startDate, Date endDate) {
		Date now = new Date();
		if (endDate != null && endDate.before(now)) {
			return false;
		}
		if (startDate != null && startDate.after(now)) {
			return false;
		}
		return true;
	}

	public static boolean isStatusActive(String statusName) {
		return ApplicationConstants.STATUS_ACTIVE.equals(statusName);
	}

}