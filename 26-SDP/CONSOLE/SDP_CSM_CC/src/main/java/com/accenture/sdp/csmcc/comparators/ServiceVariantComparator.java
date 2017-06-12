package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class ServiceVariantComparator implements Comparator<ServiceVariantBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="serviceVariantName";
	private boolean ascending=true;
	
	

	public ServiceVariantComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(ServiceVariantBean c1, ServiceVariantBean c2) {  	
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(ServiceVariantBean.SERVICEVARIANT_NAME_FIELD)) {
			return ascending ? c1.getServiceVariantName().compareToIgnoreCase(c2.getServiceVariantName()) :
				c2.getServiceVariantName().compareToIgnoreCase(c1.getServiceVariantName());
		} else if (sortColumn.equals(ServiceVariantBean.SERVICEVARIANT_DESC_FIELD)) {
			return ascending ? c1.getServiceVariantDesc().compareToIgnoreCase(c2.getServiceVariantDesc()) :
				c2.getServiceVariantDesc().compareToIgnoreCase(c1.getServiceVariantDesc());
		} else if (sortColumn.equals(ServiceVariantBean.TEMPLATE_NAME_FIELD)) {
			return ascending ? c1.getTemplateName().compareToIgnoreCase(c2.getTemplateName()) :
				c2.getTemplateName().compareToIgnoreCase(c1.getTemplateName());
		} else if (sortColumn.equals(ServiceVariantBean.SERVICEVARIANT_STATUS_FIELD)) {
			return ascending ? c1.getServiceVariantStatus().compareToIgnoreCase(c2.getServiceVariantStatus()) :
				c2.getServiceVariantStatus().compareToIgnoreCase(c1.getServiceVariantStatus());
		} else if (sortColumn.equals(ServiceVariantBean.SERVICEVARIANT_EXTID_FIELD)) {
			if (c1.getServiceVariantExtId() == null && c2.getServiceVariantExtId() != null) {
				return ascending ? "".compareTo(c2.getServiceVariantExtId()) : c2.getServiceVariantExtId().compareTo("");
			} else if (c2.getServiceVariantExtId() == null && c1.getServiceVariantExtId() != null) {
				return ascending ? c1.getServiceVariantExtId().compareTo("") : "".compareTo(c1.getServiceVariantExtId());
			} else if (c1.getServiceVariantExtId() == null && c2.getServiceVariantExtId() == null) {
				return 0;
			} else {
				 return ascending ? c1.getServiceVariantExtId().compareToIgnoreCase(c2.getServiceVariantExtId()) :
					c2.getServiceVariantExtId().compareToIgnoreCase(c1.getServiceVariantExtId());
			}
		} else if (sortColumn.equals(ServiceVariantBean.SERVICEVARIANT_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.getDateFromShortString(c1.getServiceVariantCreationDate()).compareTo(Utilities.getDateFromShortString(c2.getServiceVariantCreationDate())) :
				Utilities.getDateFromShortString(c2.getServiceVariantCreationDate()).compareTo(Utilities.getDateFromShortString(c1.getServiceVariantCreationDate()));
		} else {
			return 0;
		}
	}
}