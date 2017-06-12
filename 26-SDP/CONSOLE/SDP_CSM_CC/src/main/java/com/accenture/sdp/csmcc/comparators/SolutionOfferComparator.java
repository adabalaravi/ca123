package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class SolutionOfferComparator implements Comparator<SolutionOfferBean>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn = "offerName";
	private boolean ascending = true;

	public SolutionOfferComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}

	public int compare(SolutionOfferBean c1, SolutionOfferBean c2) {
		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_NAME_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getSolutionOfferName(), c2.getSolutionOfferName()) : Utilities.compareStringIgnoreCase(
					c2.getSolutionOfferName(), c1.getSolutionOfferName());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_DESC_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getSolutionOfferDesc(), c2.getSolutionOfferDesc()) : Utilities.compareStringIgnoreCase(
					c2.getSolutionOfferDesc(), c1.getSolutionOfferDesc());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_NAME_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getSolutionName(), c2.getSolutionName()) : Utilities.compareStringIgnoreCase(
					c2.getSolutionName(), c1.getSolutionName());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_STATUS_FIELD)) {
			return ascending ? Utilities.compareStringIgnoreCase(c1.getSolutionOfferStatus(), c2.getSolutionOfferStatus()) : Utilities.compareStringIgnoreCase(
					c2.getSolutionOfferStatus(), c1.getSolutionOfferStatus());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_START_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionOfferStartDate(), c2.getSolutionOfferStartDate()) : Utilities.compareDate(
					c2.getSolutionOfferStartDate(), c1.getSolutionOfferStartDate());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_END_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionOfferEndDate(), c2.getSolutionOfferEndDate()) : Utilities.compareDate(
					c2.getSolutionOfferEndDate(), c1.getSolutionOfferEndDate());
		}
		if (sortColumn.equals(SolutionOfferBean.SOLUTION_OFFER_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionOfferCreationDate(), c2.getSolutionOfferCreationDate()) : Utilities.compareDate(
					c2.getSolutionOfferCreationDate(), c1.getSolutionOfferCreationDate());
		} else {
			return 0;
		}
	}
}