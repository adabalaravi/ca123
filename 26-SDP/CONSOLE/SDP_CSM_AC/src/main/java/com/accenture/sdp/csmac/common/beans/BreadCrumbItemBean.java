package com.accenture.sdp.csmac.common.beans;

import java.io.Serializable;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.utils.Utilities;

public class BreadCrumbItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String link;

	public BreadCrumbItemBean() {
		super();
	}

	public BreadCrumbItemBean(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}

	public BreadCrumbItemBean(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, name);
	}

	public String getDefaultTitle() {
		return Utilities.getDefaultMessage(ApplicationConstants.MESSAGE_BUNDLE, name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BreadCrumbItemBean other = (BreadCrumbItemBean) obj;
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
