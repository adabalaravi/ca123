package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.PartyGroupBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class PartyGroupComparator implements Comparator<PartyGroupBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="platformName";
	private boolean ascending=true;
	
	

	public PartyGroupComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(PartyGroupBean c1, PartyGroupBean c2) {  	
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(PartyGroupBean.PARTY_GROUP_NAME)) {
			return ascending ? c1.getPartyGroupName().compareToIgnoreCase(c2.getPartyGroupName()) :
				c2.getPartyGroupName().compareToIgnoreCase(c1.getPartyGroupName());
		} else if (sortColumn.equals(PartyGroupBean.PARTY_GROUP_DESCRIPTION)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getPartyGroupDescription(), c2.getPartyGroupDescription()) :
				Utilities.compareStringIgnoreCase(c2.getPartyGroupDescription(), c1.getPartyGroupDescription());
		}  else if (sortColumn.equals(PartyGroupBean.CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getCreationDate(), c2.getCreationDate()) :
				Utilities.compareDate(c2.getCreationDate(), c1.getCreationDate());
		} else {
			return 0;
		}
	}
}