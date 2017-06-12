package com.accenture.sdp.csmcc.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.beans.SolutionBean;
import com.accenture.sdp.csmcc.beans.SolutionTypeBean;
import com.accenture.sdp.csmcc.business.SolutionBusiness;
import com.accenture.sdp.csmcc.business.SolutionTypeBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.comparators.SolutionComparator;
import com.accenture.sdp.csmcc.controllers.MenuController;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.popups.ConfirmPopupBean;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.SolutionOfferService;
import com.accenture.sdp.csmcc.services.SolutionService;

@ManagedBean(name = ApplicationConstants.SOLUTION_TABLE_BEAN_NAME)
@SessionScoped
public class SolutionManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	private SolutionBean selectedBean;
	private List<SolutionBean> notFilteredSolutions;
	private List<SolutionBean> solutions;
	private String nameFilter;
	private String extIdFilter;
	private String platformNameFilter;
	private List<String> platformNameFilterList = new ArrayList<String>();
	private List<SelectItem> notFilteredSolutionTypeNameList = new ArrayList<SelectItem>();
	private List<SelectItem> solutionTypeNameList = new ArrayList<SelectItem>();
	

	public SolutionManager() {
		nameFilter = "";
		extIdFilter = "";
		platformNameFilter = "";
	}

	public List<String> getPlatformNameFilterList() {
		return platformNameFilterList;
	}


	public void setPlatformNameFilterList(List<String> platformNameFilterList) {
		this.platformNameFilterList = platformNameFilterList;
	}


	public String getPlatformNameFilter() {
		return platformNameFilter;
	}


	public void setPlatformNameFilter(String platformNameFilter) {
		this.platformNameFilter = platformNameFilter;
	}

	public List<SolutionBean> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<SolutionBean> solutions) {
		this.solutions = solutions;
	}

	public SolutionBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(SolutionBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getExtIdFilter() {
		return extIdFilter;
	}

	public void setExtIdFilter(String extIdFilter) {
		this.extIdFilter = extIdFilter;
	}
	
	public void goToStep2(ActionEvent event) {
			MenuController.redirectbyParam(PathConstants.ADD_SOLUTION_STEP2);
		
	}


	public void addSolution(ActionEvent event) {
		selectedBean = new SolutionBean();
		List<SelectItem> sList=new ArrayList<SelectItem>();
		try {
			List<SolutionTypeBean> list = SolutionTypeBusiness.searchAllSolutionTypes();
			for(SolutionTypeBean b:list){
				sList.add(new SelectItem(String.valueOf(b.getSolutionTypeId()),b.getSolutionTypeName()));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}
		this.setSolutionTypeNameList(sList);
		MenuController.redirectbyParam(PathConstants.ADD_SOLUTION_STEP1);
	}

	public void backToStep1(ActionEvent event) {
		MenuController.redirectbyParam(PathConstants.ADD_SOLUTION_STEP1);
	}

	public void relatedSolutionOffers(ActionEvent event) {
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		SolutionOfferService relatedService = Utilities.findBean(
				ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN_NAME,
				SolutionOfferService.class);
		SolutionBean solution=((SolutionBean) event
				.getComponent().getAttributes().get("item"));

		try {
			// load just to check
			relatedService.searchSolutionOffersBySolution(solution.getSolutionName(), 0L, 1L, Utilities.getTenantName());
			infoSessionBean.setSolutionBean(solution);
			MenuController.redirectbyParam(PathConstants.SOLUTION_OFFER);
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}
	}


	public void updateSolution(ActionEvent event) {
		// logging
		selectedBean = ((SolutionBean) event.getComponent().getAttributes()
				.get("item"));
		selectedBean.storeValues();
		List<SelectItem> sList=new ArrayList<SelectItem>();

		try {
			List<SolutionTypeBean> list = SolutionTypeBusiness.searchAllSolutionTypes();
			for(SolutionTypeBean b:list){
				sList.add(new SelectItem(String.valueOf(b.getSolutionTypeId()),b.getSolutionTypeName()));
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}
		this.setSolutionTypeNameList(sList);
		MenuController.redirectbyParam(PathConstants.UPDATE_SOLUTION);

	}

	public void nameFilterListener(ValueChangeEvent event) {
		nameFilter = event.getNewValue().toString();
		filterResult();
	}

	public void extIdFilterListener(ValueChangeEvent event) {
		extIdFilter = event.getNewValue().toString();
		filterResult();
	}

	public void platformNameFilterListener(ValueChangeEvent event) {
		platformNameFilter = event.getNewValue().toString();
		filterResult();
	}

	public void filterResult(){
		if (nameFilter.equals("") && extIdFilter.equals("")) {
			refreshTable();
		} else {
			// Valorized filters
			solutions = new ArrayList<SolutionBean>();
			try {
				notFilteredSolutions = SolutionBusiness.getBufferedData(0, 0);
				for (SolutionBean p : notFilteredSolutions) {
					boolean nameFilterFlag  = nameFilter.equals("") || p.getSolutionName().toUpperCase().indexOf(nameFilter.toUpperCase()) == 0 ;
					boolean extIdFilterFlag = extIdFilter.equals("") || p.getSolutionExtId() != null && p.getSolutionExtId().toUpperCase().indexOf(extIdFilter.toUpperCase()) == 0;
					if (nameFilterFlag && extIdFilterFlag) {
						solutions.add(p);
					}
				}
			} catch (ServiceErrorException e) {
				PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
				msgBean.openPopup(e.getMessage());
				
				LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
				log.logEndFeature(e.getCode(),e.getMessage());
			}
		}
	}


	public void sortTable(ActionEvent event) {
		SolutionComparator comparator = new SolutionComparator(getSortColumn(), isAscending());
		Collections.sort(solutions, comparator);
	}


	public void refreshTable() {
		solutions = new ArrayList<SolutionBean>();
		try {
			notFilteredSolutions = SolutionBusiness.getBufferedData(0, 0);
			for (SolutionBean solution : notFilteredSolutions) {
				solutions.add(solution);
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			
			LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}
		setSortColumn(SolutionBean.SOLUTION_CREATION_DATE_FIELD);
		setAscending(false);
		sortTable(null);
		nameFilter="";
		extIdFilter="";
		platformNameFilter="";

	}


	@Override
	public void askChangeStatus(ActionEvent event) {
		selectedBean = (SolutionBean) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);	
		String status = (String) event.getComponent().getAttributes().get(ApplicationConstants.STATUS_PARAMETER);	
		setChangeStatusValue(status);
		ConfirmPopupBean confirm = Utilities.findBean(ApplicationConstants.CONFIRM_POPUP_BEAN, ConfirmPopupBean.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION);
		String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.POPUP_CONFIRM_MESSAGE + status);
		confirm.setTableBean(this);
		confirm.openPopup(String.format(message, entityName));
	}


	@Override
	public void changeStatusOfSelectedBean() {
		LoggerWrapper log = new LoggerWrapper(SolutionManager.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		SolutionService service = Utilities.findBean(ApplicationConstants.SOLUTION_SERVICE_BEAN_NAME, SolutionService.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SOLUTION);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + getChangeStatusValue());
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			if (StatusConstants.DELETED.equals(getChangeStatusValue())){
				service.deleteSolution(selectedBean.getSolutionId(), Utilities.getTenantName());
			} else {
				service.changeStatus(selectedBean.getSolutionId(), getChangeStatusValue(), Utilities.getTenantName());	
			}
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			mex = String.format(message, operation + " " + entityName, selectedBean.getSolutionName());
			code = ApplicationConstants.CODE_OK;
			selectedBean.setSolutionStatus(getChangeStatusValue());
			
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);
		//logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString()+"->"+operation, code,mex);
	}
	
	public List<SelectItem> getNotFilteredSolutionTypeNameList() {
		return notFilteredSolutionTypeNameList;
	}

	public void setNotFilteredSolutionTypeNameList(List<SelectItem> notFilteredSolutionTypeNameList) {
		this.notFilteredSolutionTypeNameList = notFilteredSolutionTypeNameList;
	}

	public List<SelectItem> getSolutionTypeNameList() {
		return solutionTypeNameList;
	}

	public void setSolutionTypeNameList(List<SelectItem> solutionTypeNameList) {
		this.solutionTypeNameList = solutionTypeNameList;
		this.notFilteredSolutionTypeNameList.clear();
		for (SelectItem item : (List<SelectItem>) solutionTypeNameList) {
			this.notFilteredSolutionTypeNameList.add(item);
		}
	}

}
