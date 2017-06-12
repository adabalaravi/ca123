package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.PriceBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class PriceComparator implements Comparator<PriceBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="Price";
	private boolean ascending=true;
	
	

	public PriceComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(PriceBean c1, PriceBean c2) {  	
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(PriceBean.PRICE_FIELD)) {	
			return ascending ? c1.getPrice().compareTo(c2.getPrice()) :
				c2.getPrice().compareTo(c1.getPrice());
		} else if (sortColumn.equals(PriceBean.PRICE_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getpriceCreationDate(), c2.getpriceCreationDate()) :
				Utilities.compareDate(c2.getpriceCreationDate(), c1.getpriceCreationDate());
		
		} else {
			return 0;
		}
	}
}