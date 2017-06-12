package com.accenture.sdp.csmcc.managers;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;

public abstract class BaseManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortColumn;
	private boolean ascending = true;
	private String paginatorRows = ApplicationConstants.PAGINATOR_DEFAULT_ROWS;
	private boolean updatesFlag = false;
	private String changeStatusValue;
	
	
	
	public BaseManager() {
		super();
		
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public boolean isAscending() {
		return ascending;
	}
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	
	public boolean isUpdatesFlag() {
		return updatesFlag;
	}

	public void setUpdatesFlag(boolean updatesFlag) {
		this.updatesFlag = updatesFlag;
	}
	
	
	public String getPaginatorRows() {
		return paginatorRows;
	}
	
	public void setPaginatorRows(String paginatorRows) {
		this.paginatorRows = paginatorRows;
	}
	
	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}
	
	public void paginatorListener(ValueChangeEvent event) {
		paginatorRows = (String) event.getNewValue();
	}
	
	
	
	public String getChangeStatusValue() {
		return changeStatusValue;
	}
	public void setChangeStatusValue(String changeStatusValue) {
		this.changeStatusValue = changeStatusValue;
	}
	
		
	public abstract void refreshTable();
	
	public abstract void askChangeStatus(ActionEvent event);
	
	public abstract void changeStatusOfSelectedBean();
	
}
