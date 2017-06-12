package com.accenture.sdp.csmac.managers;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;

public abstract class BaseManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5593281374912842145L;

	public abstract void refreshTable();

	protected <T> T getItemParameter(Class<T> clazz, ActionEvent event) {
		return clazz.cast(event.getComponent().getAttributes().get(ApplicationConstants.ATTRIBUTE_OBJECT_NAME));
	}

}
