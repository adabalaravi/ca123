package com.accenture.sdp.csmcc.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.sdp.csmcc.beans.SolutionBean;
import com.accenture.sdp.csmcc.common.utils.Utilities;

public class SolutionComparator implements Comparator<SolutionBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn="solutionName";
	private boolean ascending=true;



	public SolutionComparator(String sortColumn, boolean ascending) {
		super();
		this.sortColumn = sortColumn;
		this.ascending = ascending;
	}



	public int compare(SolutionBean c1, SolutionBean c2) {  	

		if (sortColumn == null) {
			return 0;
		}
		if (sortColumn.equals(SolutionBean.SOLUTION_NAME_FIELD)) {

			return ascending ? c1.getSolutionName().compareToIgnoreCase(c2.getSolutionName()) :
				c2.getSolutionName().compareToIgnoreCase(c1.getSolutionName());
		} else if (sortColumn.equals(SolutionBean.SOLUTION_DESC_FIELD)) {
			return ascending ? c1.getSolutionDesc().compareToIgnoreCase(c2.getSolutionDesc()) :
				c2.getSolutionDesc().compareToIgnoreCase(c1.getSolutionDesc());
		}else if (sortColumn.equals(SolutionBean.SOLUTION_STATUS_FIELD)) {
			return ascending ? c1.getSolutionStatus().compareToIgnoreCase(c2.getSolutionStatus()) :
				c2.getSolutionStatus().compareToIgnoreCase(c1.getSolutionStatus());
		} else if (sortColumn.equals(SolutionBean.SOLUTION_EXTID_FIELD)) {
			if (c1.getSolutionExtId() == null && c2.getSolutionExtId() != null) {
				return ascending ? "".compareTo(c2.getSolutionExtId()) : c2.getSolutionExtId().compareTo("");
			} else if (c2.getSolutionExtId() == null && c1.getSolutionExtId() != null) {
				return ascending ? c1.getSolutionExtId().compareTo("") : "".compareTo(c1.getSolutionExtId());
			} else if (c1.getSolutionExtId() == null && c2.getSolutionExtId() == null){
				return 0;
			}
			else {
				return ascending ? c1.getSolutionExtId().compareToIgnoreCase(c2.getSolutionExtId()) :
					c2.getSolutionExtId().compareToIgnoreCase(c1.getSolutionExtId());
			}
		} else if (sortColumn.equals(SolutionBean.SOLUTION_CREATION_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionCreationDate(), c2.getSolutionCreationDate()) :
				Utilities.compareDate(c2.getSolutionCreationDate(), c1.getSolutionCreationDate());
		} else if (sortColumn.equals(SolutionBean.SOLUTION_START_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionStartDate(), c2.getSolutionStartDate()) :
				Utilities.compareDate(c2.getSolutionStartDate(), c1.getSolutionStartDate());
		} else if (sortColumn.equals(SolutionBean.SOLUTION_END_DATE_FIELD)) {
			return ascending ? Utilities.compareDate(c1.getSolutionEndDate(), c2.getSolutionEndDate()) :
				Utilities.compareDate(c2.getSolutionEndDate(), c1.getSolutionEndDate());
		} else {
			return 0;
		}
	}
}