package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class PlatformComparator implements Comparator<PlatformBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="platformName";
	private boolean ascending=true;



	public PlatformComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(PlatformBean c1, PlatformBean c2) {  	
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(PlatformBean.PLATFORM_NAME_FIELD)) {
			return ascending ? c1.getPlatformName().compareToIgnoreCase(c2.getPlatformName()) :
				c2.getPlatformName().compareToIgnoreCase(c1.getPlatformName());
		} else if (sortColumn.equals(PlatformBean.PLATFORM_DESC_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getPlatformDesc(), c2.getPlatformDesc()) :
				Utilities.compareStringIgnoreCase(c2.getPlatformDesc(), c1.getPlatformDesc());
		} else if (sortColumn.equals(PlatformBean.PLATFORM_STATUS_FIELD)) {
			return ascending ? c1.getPlatformStatus().compareToIgnoreCase(c2.getPlatformStatus()) :
				c2.getPlatformStatus().compareToIgnoreCase(c1.getPlatformStatus());
		} else if (sortColumn.equals(PlatformBean.PLATFORM_EXTID_FIELD)) {
			if (c1.getPlatformExtId() == null && c2.getPlatformExtId() != null) {
				return ascending ? "".compareTo(c2.getPlatformExtId()) : c2.getPlatformExtId().compareTo("");
			} else if (c2.getPlatformExtId() == null && c1.getPlatformExtId() != null) {
				return ascending ? c1.getPlatformExtId().compareTo("") : "".compareTo(c1.getPlatformExtId());
			} else if (c1.getPlatformExtId() == null && c2.getPlatformExtId() == null) {
				return 0;
			}
			else {
				return ascending ? c1.getPlatformExtId().compareToIgnoreCase(c2.getPlatformExtId()) :
					c2.getPlatformExtId().compareToIgnoreCase(c1.getPlatformExtId());
			}

		} else if (sortColumn.equals(PlatformBean.PLATFORM_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.getDateFromShortString(c1.getPlatformCreationDate()).compareTo(Utilities.getDateFromShortString(c2.getPlatformCreationDate())) :
				Utilities.getDateFromShortString(c2.getPlatformCreationDate()).compareTo(Utilities.getDateFromShortString(c1.getPlatformCreationDate()));
		} else {
			return 0;
		}
	}
}