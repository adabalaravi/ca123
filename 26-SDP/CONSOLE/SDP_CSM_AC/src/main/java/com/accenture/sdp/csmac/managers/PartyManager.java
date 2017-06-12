package com.accenture.sdp.csmac.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import com.accenture.sdp.csmac.beans.party.PartyBean;
import com.accenture.sdp.csmac.beans.party.PartyGroupBean;
import com.accenture.sdp.csmac.business.PartyGroupBusiness;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.MessageConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.controllers.MenuController;
import com.accenture.sdp.csmac.controllers.PopupController;
import com.accenture.sdp.csmac.services.PartyService;

public abstract class PartyManager extends BaseManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// search fields
	protected String partyName;
	protected String lastName;
	protected String firstName;
	protected String credential;
	protected int searchBy = ApplicationConstants.ORGANIZZATION_NAME_SEARCH;

	// partyPage handling
	private boolean editable;
	private int tabIndex;
	private String tabInnerPage;

	// partyGroups
	private Map<String, Long> partyGroupsMap;
	private List<String> partyGroupListSelected;
	private List<String> partyGroupListSelectedAll;
	private List<String> partyGroupListUnselected;
	private List<String> partyGroupListUnselectedAll;

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public int getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(int searchBy) {
		this.searchBy = searchBy;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getTabInnerPage() {
		return tabInnerPage;
	}

	public void setTabInnerPage(String tabInnerPage) {
		this.tabInnerPage = tabInnerPage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void searchChilPartyByPartyName() {
		searchBy = ApplicationConstants.CHILD_PARTY_NAME_SEARCH;
		refreshTable();
	}

	public void searchPartyByFirstAndLastName() {
		searchBy = ApplicationConstants.FIRST_LAST_NAME_SEARCH;
		refreshTable();
	}

	public void searchPartyByCredential() {
		searchBy = ApplicationConstants.CREDENTIAL_SEARCH;
		refreshTable();
	}

	public void clearFields() {
		partyName = null;
		lastName = null;
		firstName = null;
		credential = null;
	}

	public Map<String, Long> getPartyGroupsMap() {
		return partyGroupsMap;
	}

	public void setPartyGroupsMap(Map<String, Long> partyGroupsMap) {
		this.partyGroupsMap = partyGroupsMap;
	}

	public List<String> getPartyGroupListSelected() {
		return partyGroupListSelected;
	}

	public void setPartyGroupListSelected(List<String> partyGroupListSelected) {
		this.partyGroupListSelected = partyGroupListSelected;
	}

	public List<String> getPartyGroupListSelectedAll() {
		return partyGroupListSelectedAll;
	}

	public void setPartyGroupListSelectedAll(List<String> partyGroupListSelectedAll) {
		this.partyGroupListSelectedAll = partyGroupListSelectedAll;
	}

	public List<String> getPartyGroupListUnselected() {
		return partyGroupListUnselected;
	}

	public void setPartyGroupListUnselected(List<String> partyGroupListUnselected) {
		this.partyGroupListUnselected = partyGroupListUnselected;
	}

	public List<String> getPartyGroupListUnselectedAll() {
		return partyGroupListUnselectedAll;
	}

	public void setPartyGroupListUnselectedAll(List<String> partyGroupListUnselectedAll) {
		this.partyGroupListUnselectedAll = partyGroupListUnselectedAll;
	}

	public void toRight() {
		partyGroupListSelectedAll.addAll(partyGroupListUnselected);
		Collections.sort(partyGroupListSelectedAll);
		partyGroupListUnselectedAll.removeAll(partyGroupListUnselected);
	}

	public void toLeft() {
		partyGroupListUnselectedAll.addAll(partyGroupListSelected);
		Collections.sort(partyGroupListUnselectedAll);
		partyGroupListSelectedAll.removeAll(partyGroupListSelected);
	}

	public void toRightAll() {
		partyGroupListSelectedAll.addAll(partyGroupListUnselectedAll);
		// sort probabilmente superfluo
		Collections.sort(partyGroupListSelectedAll);
		partyGroupListUnselectedAll.clear();
	}

	public void toLeftAll() {
		partyGroupListUnselectedAll.addAll(partyGroupListSelectedAll);
		// sort probabilmente superfluo
		Collections.sort(partyGroupListUnselectedAll);
		partyGroupListSelectedAll.clear();
	}

	public abstract void goToUpdateClusters(ActionEvent event);

	protected void loadPartyGroups(PartyBean info) {
		try {
			info.setPartyGroups(PartyGroupBusiness.searchPartyGroupsByPartyId(info.getId()));
		} catch (ServiceErrorException e) {
			// do nothing
		}
	}

	protected void goToUpdateClusters(PartyBean info) {
		loadPartyGroups(info);
		partyGroupListSelected = new ArrayList<String>();
		partyGroupListSelectedAll = new ArrayList<String>();
		partyGroupListUnselected = new ArrayList<String>();
		partyGroupListUnselectedAll = new ArrayList<String>();
		try {
			List<PartyGroupBean> list = PartyGroupBusiness.searchAllPartyGroups();
			partyGroupsMap = new HashMap<String, Long>();
			for (PartyGroupBean group : list) {
				partyGroupsMap.put(group.getName(), group.getId());
			}
			// imposto lista completa sui nuovi
			partyGroupListUnselectedAll.addAll(partyGroupsMap.keySet());
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}

		if (info.getPartyGroups() != null) {
			// setto quelli vecchi
			for (PartyGroupBean group : info.getPartyGroups()) {
				partyGroupListSelectedAll.add(group.getName());
			}
			Collections.sort(partyGroupListSelectedAll);
		}
		// differenza completa-vecchi
		partyGroupListUnselectedAll.removeAll(partyGroupListSelectedAll);
		Collections.sort(partyGroupListUnselectedAll);
		new MenuController().redirectbyParam(PathConstants.PARTY_CLUSTER_UPDATE);
	}

	public abstract void updateClusters(ActionEvent event);

	protected void updateClusters(PartyBean info) {
		PartyService service = Utilities.findBean(ApplicationConstants.PARTY_SERVICE_BEAN, PartyService.class);
		PopupController msgBean = Utilities.findBean(ApplicationConstants.CONTROLLER_POPUP_NAME, PopupController.class);
		try {
			List<Long> removeGroups = new ArrayList<Long>();
			List<Long> addGroups = new ArrayList<Long>();
			for (String g : partyGroupListUnselectedAll) {
				for (PartyGroupBean i : info.getPartyGroups()) {
					if (i.getName().equals(g)) {
						removeGroups.add(i.getId());
						break;
					}
				}
			}
			for (String g : partyGroupListSelectedAll) {
				boolean found = false;
				for (PartyGroupBean i : info.getPartyGroups()) {
					if (i.getName().equals(g)) {
						found = true;
						break;
					}
				}
				if (!found) {
					addGroups.add(partyGroupsMap.get(g));
				}
			}
			if (!removeGroups.isEmpty() || !addGroups.isEmpty()) {
				service.modifyPartyCluster(info.getId(), removeGroups, addGroups, Utilities.getTenantName());
			}
			String mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.UPDATE_DETAILS_MESSAGE), info.getName());
			msgBean.openPopup(mex);

			new MenuController().redirectbyParam(PathConstants.PARTY_VIEW);
		} catch (ServiceErrorException e) {
			PopupController.handleServiceErrorException(e);
		}
	}

	public abstract void createChildParty(ActionEvent event);

	public abstract void updateChildParty(ActionEvent event);

	public void goToUpdateChildParty(ActionEvent event) {
		// store values
		storeValues();
		new MenuController().redirectbyParam(PathConstants.PARTY_DETAILS_UPDATE);
	}

	protected abstract void resetDetails();

	protected abstract void storeValues();

	public void editMode(ActionEvent event) {
		if (this.editable) {
			resetDetails();
		} else {
			storeValues();
		}
		this.editable = !this.editable;
	}

	public void tabChanged(TabChangeEvent event) {
		// resetta form di modifica utente
		if (this.editable) {
			resetDetails();
			this.editable = false;
		}
		// setta l'indice del tab
		// fondamentale per il tabview fuori dal form
		TabView tv = (TabView) event.getComponent();
		this.tabIndex = tv.getChildren().indexOf(event.getTab());

		// resetta l'inner page
		this.tabInnerPage = null;
	}

}
