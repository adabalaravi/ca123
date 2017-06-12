package com.accenture.sdp.csmcc.managers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmcc.common.beans.Param;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.controllers.MenuController;



@ManagedBean(name = ApplicationConstants.PROFILE_MANAGER)
@SessionScoped
public class ProfileManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Param> items;
	private List<SelectItem> typeList;
	private String action;
	private Param lastParam;
	private int count;
	private boolean updatesFlag;
	private boolean offerFlag;
	private boolean nextFlag;
	
	
	private String name;
	private String value;
	private int index;
	

	public ProfileManager() {
		super();
		count = 0;
		updatesFlag = false;
		nextFlag = false;
		offerFlag = false;
	
	}
	
	
	public void initialize(String profile) {
			count = items.size();
	}
	
	public boolean isUpdatesFlag() {
		return updatesFlag;
	}

	public void setUpdatesFlag(boolean updatesFlag) {
		this.updatesFlag = updatesFlag;
	}
	
	public List<Param> getItems() {
		return items;
	}
	public void setItems(List<Param> items) {
		this.items = items;
	}

	public void addItem(){
		Param newItem = new Param();
		items.add(newItem);
	}

	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public List<SelectItem> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<SelectItem> typeList) {
		this.typeList = typeList;
	}


	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

	/* GETTER AND SETTER METHODS END ---------------------------------------------------------------------------------------------*/



	public boolean isNextFlag() {
		return nextFlag;
	}


	public void setNextFlag(boolean nextFlag) {
		this.nextFlag = nextFlag;
	}


	public void gotoBack(ActionEvent event){
		if (PathConstants.ADD_SERVICETEMPLATE.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_SERVICETEMPLATE);
		}
		else if (PathConstants.UPDATE_SERVICETEMPLATE.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_SERVICETEMPLATE);
		}
		else if (PathConstants.ADD_SERVICEVARIANT.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_SERVICEVARIANT);
		}
		else if (PathConstants.UPDATE_SERVICEVARIANT.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_SERVICEVARIANT);
		}
		else if (PathConstants.ADD_OFFER.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_OFFER);
		}
		else if (PathConstants.UPDATE_OFFER.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_OFFER);
		}
		else if (PathConstants.ADD_SOLUTION_STEP1.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_SOLUTION_STEP1);
		}
		else if (PathConstants.UPDATE_SOLUTION.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_SOLUTION);
		}
		else if (PathConstants.ADD_SOLUTION_OFFER_STEP1.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_SOLUTION_OFFER_STEP1);
		}
		else if (PathConstants.UPDATE_SOLUTION_OFFER.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_SOLUTION_OFFER);
		}
		else if (PathConstants.ADD_DISCOUNT_SOLUTION_OFFER_STEP1.equals(action)) {
			MenuController.redirectbyParam(PathConstants.ADD_DISCOUNT_SOLUTION_OFFER_STEP1);
		}
		else if (PathConstants.UPDATE_DISCOUNT_SOLUTION_OFFER.equals(action)) {
			MenuController.redirectbyParam(PathConstants.UPDATE_DISCOUNT_SOLUTION_OFFER);
		}
	}


	public void addNewParam(ActionEvent event){
		if (validProfile()){
			lastParam = new Param();
			lastParam.setIndex(count++);
			items.add(lastParam);
		}
	}

	public void removeParam(ActionEvent event){
		Param selectedBean = ((Param) event.getComponent().getAttributes().get(ApplicationConstants.ITEM));
		items.remove(selectedBean);
		count--;
	}


	private boolean validProfile(){
		boolean isValid = true;
		for(Param param : items){
			HashMap<String,Object> validationMap = new HashMap<String,Object>();
			validationMap.put("mainForm:itemName", param.getName());
			validationMap.put("mainForm:itemValue", param.getValue());
			isValid = ValidationUtilities.validateMandatoryFields(validationMap);
		}
		return isValid;
	}
	
	


	public boolean isOfferFlag() {
		return offerFlag;
	}


	public void setOfferFlag(boolean offerFlag) {
		this.offerFlag = offerFlag;
	}



}
