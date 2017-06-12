package com.accenture.sdp.csmac.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmac.beans.avs.AvsCountryLangCodeBean;
import com.accenture.sdp.csmac.beans.avs.AvsDeviceIdTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPaymentTypeBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcExtendedRatingBean;
import com.accenture.sdp.csmac.beans.avs.AvsPcLevelBean;
import com.accenture.sdp.csmac.beans.avs.AvsPlatformBean;
import com.accenture.sdp.csmac.beans.avs.AvsRetailerDomainBean;
import com.accenture.sdp.csmac.beans.avs.CrmAccountDeviceBean;
import com.accenture.sdp.csmac.beans.device.DevicePolicyBean;
import com.accenture.sdp.csmac.beans.party.AvsPartyBean;
import com.accenture.sdp.csmac.business.AvsBusiness;
import com.accenture.sdp.csmac.business.AvsPartyBusiness;
import com.accenture.sdp.csmac.common.PropertyManager;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.AvsConstants;
import com.accenture.sdp.csmac.common.constants.MessageConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.MenuController;
import com.accenture.sdp.csmac.controllers.PopupController;
import com.accenture.sdp.csmac.controllers.SessionController;

@ManagedBean(name = ApplicationConstants.PARTY_MANAGER)
@SessionScoped
public class AvsPartyManager extends PartyManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6856320239716288137L;

	// search results
	private List<AvsPartyBean> parties;
	private AvsPartyBean selectedBean;
	private AvsPartyBean storedBean;

	// AVS DATA
	private List<SelectItem> pcLevelList;
	private List<SelectItem> userPcExtendedRatingsList;
	private List<SelectItem> paymentMethodList;
	private List<SelectItem> crmAccountDeviceIdTypeList;
	private List<SelectItem> channelList;
	private Map<String, List<SelectItem>> channelMap;
	private List<SelectItem> userStatusList;
	private List<SelectItem> countryLangCodeList;
	private List<SelectItem> retailerDomainList;
	private List<SelectItem> devicePolicyList;
	private boolean blackListDisabled;
	private boolean whiteListDisabled;


	public AvsPartyManager() throws ServiceErrorException {
		// caricamento liste AVS, gestito statico per tutta sessione
		loadUserStatusList();
		loadPcLevelList();
		loadUserPcExtendedRatingsList();
		loadPaymentMethodList();
		loadCrmAccountDeviceIdTypeList();
		loadChannelList();
		loadCountryLangCodeList();
		loadRetailerDomainList();
		loadDevicePolicyList();
	}

	private void loadPcLevelList() throws ServiceErrorException {
		List<AvsPcLevelBean> list = AvsBusiness.searchAllAvsPcLevels();
		pcLevelList = new ArrayList<SelectItem>();
		for (AvsPcLevelBean info : list) {
			pcLevelList.add(new SelectItem(info.getValue(), info.getDescription()));
		}
	}

	private void loadUserPcExtendedRatingsList() throws ServiceErrorException {
		List<AvsPcExtendedRatingBean> list = AvsBusiness.searchAllAvsPcExtendedRatings();
		userPcExtendedRatingsList = new ArrayList<SelectItem>();
		for (AvsPcExtendedRatingBean info : list) {
			userPcExtendedRatingsList.add(new SelectItem(info.getValue(), info.getDescription()));
		}
	}

	private void loadPaymentMethodList() throws ServiceErrorException {
		if (paymentMethodList == null) {
			List<AvsPaymentTypeBean> list = AvsBusiness.searchAllAvsPaymentTypes();
			paymentMethodList = new ArrayList<SelectItem>();
			for (AvsPaymentTypeBean info : list) {
				paymentMethodList.add(new SelectItem(info.getPaymentTypeId(), info.getPaymentMethod()));
			}
		}
	}

	private void loadCrmAccountDeviceIdTypeList() throws ServiceErrorException {
		if (crmAccountDeviceIdTypeList == null) {
			List<AvsDeviceIdTypeBean> list = AvsBusiness.searchAllAvsDeviceIdTypes();
			crmAccountDeviceIdTypeList = new ArrayList<SelectItem>();
			for (AvsDeviceIdTypeBean info : list) {
				crmAccountDeviceIdTypeList.add(new SelectItem(info.getTypeId(), info.getValue()));
			}
		}
	}

	private void loadUserStatusList() {
		if (userStatusList == null) {
			PropertyManager pm = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
			userStatusList = new ArrayList<SelectItem>();
			userStatusList.add(new SelectItem(pm.getProperty(AvsConstants.AVS_USER_ACTIVE), Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
					MessageConstants.AVS_USER_ACTIVE)));
			userStatusList.add(new SelectItem(pm.getProperty(AvsConstants.AVS_USER_INACTIVE), Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
					MessageConstants.AVS_USER_INACTIVE)));
			userStatusList.add(new SelectItem(pm.getProperty(AvsConstants.AVS_USER_CEASED), Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,
					MessageConstants.AVS_USER_CEASED)));
		}
	}

	private void loadChannelList() {
		if (channelList == null) {
			try {
				List<AvsPlatformBean> list = AvsBusiness.searchAllAvsPlatforms();
				channelList = new ArrayList<SelectItem>();
				channelMap = new HashMap<String, List<SelectItem>>();
				for (AvsPlatformBean info : list) {
					channelList.add(new SelectItem(info.getPlatformName()));
					List<SelectItem> devices = new ArrayList<SelectItem>();
					for (AvsDeviceIdTypeBean i : info.getDeviceTypes()) {
						devices.add(new SelectItem(i.getTypeId(), i.getValue()));
					}
					channelMap.put(info.getPlatformName(), devices);
				}
			} catch (ServiceErrorException e) {
				PopupController.handleServiceErrorException(e);
			}
		}
	}

	private void loadCountryLangCodeList() throws ServiceErrorException {
		if (countryLangCodeList == null) {
			List<AvsCountryLangCodeBean> list = AvsBusiness.searchAllAvsCountryLangCodes();
			countryLangCodeList = new ArrayList<SelectItem>();
			for (AvsCountryLangCodeBean info : list) {
				countryLangCodeList.add(new SelectItem(info.getCountryCode(), info.getCountry()));
			}
		}
	}

	private void loadRetailerDomainList() throws ServiceErrorException {
		if (retailerDomainList == null) {
			List<AvsRetailerDomainBean> list = AvsBusiness.searchAllAvsRetailerDomains();
			retailerDomainList = new ArrayList<SelectItem>();
			for (AvsRetailerDomainBean info : list) {
				retailerDomainList.add(new SelectItem(info.getRetailerId(), info.getHostDomain()));
			}
		}
	}

	private void loadDevicePolicyList() throws ServiceErrorException {
		if (devicePolicyList == null) {
			List<DevicePolicyBean> list = AvsBusiness.searchAllDevicePolicies();
			devicePolicyList = new ArrayList<SelectItem>();
			for (DevicePolicyBean info : list) {
				devicePolicyList.add(new SelectItem(info.getPolicyId(),info.getDevicePolicyName()));
			}
		}
	}






	public AvsPartyBean getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(AvsPartyBean selectedBean) {
		this.selectedBean = selectedBean;
	}

	public List<AvsPartyBean> getParties() {
		return parties;
	}

	public void setParties(List<AvsPartyBean> parties) {
		this.parties = parties;
	}

	public List<SelectItem> getPcLevelList() throws ServiceErrorException {
		return pcLevelList;
	}

	public void setPcLevelList(List<SelectItem> pcLevelList) {
		this.pcLevelList = pcLevelList;
	}

	public List<SelectItem> getUserPcExtendedRatingsList() throws ServiceErrorException {
		return userPcExtendedRatingsList;
	}

	public void setUserPcExtendedRatingsList(List<SelectItem> userPcExtendedRatingsList) {
		this.userPcExtendedRatingsList = userPcExtendedRatingsList;
	}

	public List<SelectItem> getPaymentMethodList() throws ServiceErrorException {
		return paymentMethodList;
	}

	public void setPaymentMethodList(List<SelectItem> paymentMethodList) {
		this.paymentMethodList = paymentMethodList;
	}

	public List<SelectItem> getCrmAccountDeviceIdTypeList() throws ServiceErrorException {
		return crmAccountDeviceIdTypeList;
	}

	public void setCrmAccountDeviceIdTypeList(List<SelectItem> crmAccountDeviceIdTypeList) {
		this.crmAccountDeviceIdTypeList = crmAccountDeviceIdTypeList;
	}

	public List<SelectItem> getUserStatusList() {
		return userStatusList;
	}

	public void setUserStatusList(List<SelectItem> userStatusList) {
		this.userStatusList = userStatusList;
	}

	public List<SelectItem> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<SelectItem> channelList) {
		this.channelList = channelList;
	}

	public List<SelectItem> getCountryLangCodeList() throws ServiceErrorException {
		return countryLangCodeList;
	}

	public void setCountryLangCodeList(List<SelectItem> countryLangCodeList) {
		this.countryLangCodeList = countryLangCodeList;
	}

	public List<SelectItem> getRetailerDomainList() throws ServiceErrorException {
		return retailerDomainList;
	}

	public void setRetailerDomainList(List<SelectItem> retailerDomainList) {
		this.retailerDomainList = retailerDomainList;
	}


	public boolean isBlackListDisabled() {
		return blackListDisabled;
	}

	public void setBlackListDisabled(boolean blackListDisabled) {
		this.blackListDisabled = blackListDisabled;
	}

	public boolean isWhiteListDisabled() {
		return whiteListDisabled;
	}

	public void setWhiteListDisabled(boolean whiteListDisabled) {
		this.whiteListDisabled = whiteListDisabled;
	}

	/********** END GETTER AND SETTER METHODS ***************************/

	public void refreshTable() {
		try {
			parties = null;

			switch (searchBy) {
			case ApplicationConstants.FIRST_LAST_NAME_SEARCH: {
				parties = AvsPartyBusiness.searchPartiesByName(firstName, lastName);
				break;
			}
			case ApplicationConstants.CREDENTIAL_SEARCH: {
				parties = AvsPartyBusiness.searchPartiesByCredential(credential);
				break;
			}
			case ApplicationConstants.CHILD_PARTY_NAME_SEARCH: {
				parties = AvsPartyBusiness.searchPartiesByPartyName(partyName);
				break;
			}
			default:
				break;
			}

			new MenuController().redirectbyParam(PathConstants.PARTY_VIEW);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void goToDetailPage(ActionEvent event) {
		selectedBean = getItemParameter(AvsPartyBean.class, event);
		// ADD INFO AS PER 2 STEPS LOADING
		try {
			AvsPartyBusiness.prepareAvsPartyStep2(selectedBean);
			// load party groups for subscriptions
			loadPartyGroups(selectedBean);
			// load subscriptions
			SubscriptionManager sm = Utilities.findBean(ApplicationConstants.SUBSCRIPTION_MANAGER, SubscriptionManager.class);
			sm.refreshTable();
			setTabIndex(0);
			new MenuController().redirectbyParam(PathConstants.PARTY_DETAILS_VIEW);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void createChildParty(ActionEvent event) {
		try {
			AvsPartyBusiness.createAVSUser(selectedBean);

			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.ADD_OPERATOR_MESSAGE), selectedBean.getCrmAccountId());
			PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
			msgBean.openPopup(mex);
			// FIXME bypassata l'associazione dei partyGroup
			// goToManageClusters()
			// new MenuBean().redirectbyParam(PathConstants.USER_CLUSTER_UPDATE)
			SessionController session = Utilities.findBean(ApplicationConstants.CONTROLLER_SESSION_NAME, SessionController.class);
			session.refreshCustomersCounter();
			new MenuController().redirectbyParam(PathConstants.PARTY_CREATE);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void updateChildParty(ActionEvent event) {
		try {
			AvsPartyBusiness.updateAVSUser(selectedBean);

			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_DETAILS_MESSAGE), selectedBean.getCrmAccountId());
			PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
			msgBean.openPopup(mex);
			setEditable(false);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public void goToUpdateClusters(ActionEvent event) {
		selectedBean = getItemParameter(AvsPartyBean.class, event);
		goToUpdateClusters(selectedBean);
	}

	public void updateClusters(ActionEvent event) {
		super.updateClusters(selectedBean);
	}

	public List<SelectItem> getCrmAccountDeviceIdTypeList(String channel) {
		if (channelMap == null) {
			loadChannelList();
		}
		return channelMap.get(channel);
	}

	public void storeValues() {
		try {
			storedBean = (AvsPartyBean) selectedBean.clone();
		} catch (CloneNotSupportedException e) {
			storedBean = null;
		}
	}

	public void resetDetails() {
		if (storedBean != null) {
			selectedBean = storedBean;
		}
	}

	public void addDevice(ActionEvent event) {
		if (selectedBean.getCrmAccountDeviceList() == null) {
			selectedBean.setCrmAccountDeviceList(new ArrayList<CrmAccountDeviceBean>());
		}
		selectedBean.getCrmAccountDeviceList().add(new CrmAccountDeviceBean());
	}

	public void removeDevice(ActionEvent event) {
		CrmAccountDeviceBean device = getItemParameter(CrmAccountDeviceBean.class, event);
		selectedBean.getCrmAccountDeviceList().remove(device);
	}

	public void userPinPurchaseChanged() {
		selectedBean.setUserPinParentalControl(selectedBean.getUserPinPurchase());
	}

	public void blackListedChange()  {
		if (selectedBean.getBlackListed()) {
			this.whiteListDisabled= true;
		}else {
			this.whiteListDisabled=false;
		}
	}
	
	public void whiteListedChange()  {
	if (selectedBean.getWhiteListed()) {
		this.blackListDisabled= true;
	}else {
		this.blackListDisabled=false;
	}
}
	public List<SelectItem> getDevicePolicyList() {
		return devicePolicyList;
	}

	public void setDevicePolicyList(List<SelectItem> devicePolicyList) {
		this.devicePolicyList = devicePolicyList;
	}

}
