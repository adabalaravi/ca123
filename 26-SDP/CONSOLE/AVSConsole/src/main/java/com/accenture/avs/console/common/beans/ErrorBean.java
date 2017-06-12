package com.accenture.avs.console.common.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.accenture.avs.console.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.ERROR_BEAN_NAME)
@SessionScoped
public class ErrorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238451863544051161L;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void loadError(Throwable e) {
		StringBuffer buffer = new StringBuffer();
		Throwable i = e;
		while (i != null) {
			buffer.append("<span style=\"color:red\"><b>");
			buffer.append(i.getMessage());
			buffer.append("</b></span><br/>");
			buffer.append(stacca(i, 10));
			i = i.getCause();
		}
		e.getCause();
		error = buffer.toString();
	}

	private String stacca(Throwable e, int n) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < n && i < e.getStackTrace().length; i++) {
			buffer.append(e.getStackTrace()[i].toString());
			buffer.append("<br/>");
		}
		return buffer.toString();
	}

}
