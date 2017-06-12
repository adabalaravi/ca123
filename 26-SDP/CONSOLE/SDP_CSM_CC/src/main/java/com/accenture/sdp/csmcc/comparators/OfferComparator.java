package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.OfferBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class OfferComparator implements Comparator<OfferBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="offerName";
	private boolean ascending=true;
	
	

	public OfferComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(OfferBean c1, OfferBean c2) {  	
		
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(OfferBean.OFFER_NAME_FIELD)) {
			return ascending ? c1.getOfferName().compareToIgnoreCase(c2.getOfferName()) :
				c2.getOfferName().compareToIgnoreCase(c1.getOfferName());
		} else if (sortColumn.equals(OfferBean.OFFER_DESC_FIELD)) {
			return ascending ? c1.getOfferDesc().compareToIgnoreCase(c2.getOfferDesc()) :
				c2.getOfferDesc().compareToIgnoreCase(c1.getOfferDesc());
		} else if (sortColumn.equals(OfferBean.OFFER_STATUS_FIELD)) {
			return ascending ? c1.getOfferStatus().compareToIgnoreCase(c2.getOfferStatus()) :
				c2.getOfferStatus().compareToIgnoreCase(c1.getOfferStatus());
		} else if (sortColumn.equals(OfferBean.OFFER_SERVICE_VARIANT_NAME_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getServiceVariantName(), c2.getServiceVariantName()) :
				Utilities.compareStringIgnoreCase(c2.getServiceVariantName(), c1.getServiceVariantName());
		} else if (sortColumn.equals(OfferBean.OFFER_EXT_ID_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getOfferExtId(), c2.getOfferExtId()) :
				Utilities.compareStringIgnoreCase(c2.getOfferExtId(), c1.getOfferExtId());
		} else if (sortColumn.equals(OfferBean.OFFER_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getOfferCreationDate(), c2.getOfferCreationDate()) :
				Utilities.compareDate(c2.getOfferCreationDate(), c1.getOfferCreationDate());
		} else if (sortColumn.equals(OfferBean.OFFER_RENT_PRICE)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getRentPriceAVS(), c2.getRentPriceAVS()) :
				Utilities.compareStringIgnoreCase(c2.getRentPriceAVS(), c1.getRentPriceAVS());
		} else if (sortColumn.equals(OfferBean.OFFER_PRICE)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getPriceAVS(), c2.getPriceAVS()) :
				Utilities.compareStringIgnoreCase(c2.getPriceAVS(), c1.getPriceAVS());
		}
		
		else {
			return 0;
		}
	}
	
}