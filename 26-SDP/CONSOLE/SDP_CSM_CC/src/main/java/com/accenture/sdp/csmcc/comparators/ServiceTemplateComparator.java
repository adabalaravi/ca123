package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class ServiceTemplateComparator implements Comparator<ServiceTemplateBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="serviceTemplateName";
	private boolean ascending=true;
	
	

	public ServiceTemplateComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(ServiceTemplateBean c1, ServiceTemplateBean c2) {  	
		
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(ServiceTemplateBean.SERVICETEMPLATE_NAME_FIELD)) {
			
			return ascending ? c1.getServiceTemplateName().compareToIgnoreCase(c2.getServiceTemplateName()) :
				c2.getServiceTemplateName().compareToIgnoreCase(c1.getServiceTemplateName());
		} else if (sortColumn.equals(ServiceTemplateBean.SERVICETEMPLATE_DESC_FIELD)) {
			return ascending ? c1.getServiceTemplateDesc().compareToIgnoreCase(c2.getServiceTemplateDesc()) :
				c2.getServiceTemplateDesc().compareToIgnoreCase(c1.getServiceTemplateDesc());
		} else if (sortColumn.equals(ServiceTemplateBean.PLATFORM_NAME_FIELD)) {
			return ascending ? c1.getPlatformName().compareToIgnoreCase(c2.getPlatformName()) :
				c2.getPlatformName().compareToIgnoreCase(c1.getPlatformName());
		} else if (sortColumn.equals(ServiceTemplateBean.SERVICETEMPLATE_STATUS_FIELD)) {
			return ascending ? c1.getServiceTemplateStatus().compareToIgnoreCase(c2.getServiceTemplateStatus()) :
				c2.getServiceTemplateStatus().compareToIgnoreCase(c1.getServiceTemplateStatus());
		} else if (sortColumn.equals(ServiceTemplateBean.SERVICETEMPLATE_EXTID_FIELD)) {
			if (c1.getServiceTemplateExtId() == null && c2.getServiceTemplateExtId() != null) {
				return ascending ? "".compareTo(c2.getServiceTemplateExtId()) : c2.getServiceTemplateExtId().compareTo("");
			} else if (c2.getServiceTemplateExtId() == null && c1.getServiceTemplateExtId() != null) {
				return ascending ? c1.getServiceTemplateExtId().compareTo("") : "".compareTo(c1.getServiceTemplateExtId());
			} else if (c1.getServiceTemplateExtId() == null && c2.getServiceTemplateExtId() == null) {
				return 0;
			}
			  else { 
				 return ascending ? c1.getServiceTemplateExtId().compareToIgnoreCase(c2.getServiceTemplateExtId()) :
					c2.getServiceTemplateExtId().compareToIgnoreCase(c1.getServiceTemplateExtId());
			  }
		} else if (sortColumn.equals(ServiceTemplateBean.SERVICETEMPLATE_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.getDateFromShortString(c1.getServiceTemplateCreationDate()).compareTo(Utilities.getDateFromShortString(c2.getServiceTemplateCreationDate())) :
				Utilities.getDateFromShortString(c2.getServiceTemplateCreationDate()).compareTo(Utilities.getDateFromShortString(c1.getServiceTemplateCreationDate()));
		} else {
			return 0;
		}
	}
}