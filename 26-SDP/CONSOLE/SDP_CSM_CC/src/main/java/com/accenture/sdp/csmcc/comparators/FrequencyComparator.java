package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class FrequencyComparator implements Comparator<FrequencyBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="FrequencyName";
	private boolean ascending=true;



	public FrequencyComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(FrequencyBean c1, FrequencyBean c2) {  	
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(FrequencyBean.FREQUENCY_NAME_FIELD)) {

			return ascending ? c1.getFrequencyName().compareToIgnoreCase(c2.getFrequencyName()) :
				c2.getFrequencyName().compareToIgnoreCase(c1.getFrequencyName());
		} else if (sortColumn.equals(FrequencyBean.FREQUENCY_IN_DAYS)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getFrequencyDays().toString(), c2.getFrequencyDays().toString()) :
				Utilities.compareStringIgnoreCase(c2.getFrequencyDays().toString(), c1.getFrequencyDays().toString());
		} else if (sortColumn.equals(FrequencyBean.FREQUENCY_DESC_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getFrequencyDesc(), c2.getFrequencyDesc()) :
				Utilities.compareStringIgnoreCase(c2.getFrequencyDesc(), c1.getFrequencyDesc());
		}  else if (sortColumn.equals(FrequencyBean.FREQUENCY_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getFrequencyCreationDate(), c2.getFrequencyCreationDate()) :
				Utilities.compareDate(c2.getFrequencyCreationDate(), c1.getFrequencyCreationDate());
		} else {
			return 0;
		}
	}
}