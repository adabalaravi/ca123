package com.accenture.sdp.csmac.beans.avs;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmac.managers.AvsPartyManager;

public class CrmAccountDeviceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String crmAccountDeviceId;
	private int crmAccountDeviceIdType;
	private String channel;

	// questa roba e' finita qui per caricamento dinamico del form
	private List<SelectItem> crmAccountDeviceIdTypeList;

	public List<SelectItem> getCrmAccountDeviceIdTypeList() {
		if (crmAccountDeviceIdTypeList == null) {
			AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
			crmAccountDeviceIdTypeList = pm.getCrmAccountDeviceIdTypeList(pm.getChannelList().get(0).getValue().toString());
		}
		return crmAccountDeviceIdTypeList;
	}

	public void setCrmAccountDeviceIdTypeList(List<SelectItem> crmAccountDeviceIdTypeList) {
		this.crmAccountDeviceIdTypeList = crmAccountDeviceIdTypeList;
	}

	public void channelChangeListener(AjaxBehaviorEvent event) {
		AvsPartyManager pm = Utilities.findBean(ApplicationConstants.PARTY_MANAGER, AvsPartyManager.class);
		crmAccountDeviceIdTypeList = pm.getCrmAccountDeviceIdTypeList(channel);
	}

	// fine caricamento dinamico

	public String getCrmAccountDeviceId() {
		return crmAccountDeviceId;
	}

	public void setCrmAccountDeviceId(String crmAccountDeviceId) {
		this.crmAccountDeviceId = crmAccountDeviceId;
	}

	public int getCrmAccountDeviceIdType() {
		return crmAccountDeviceIdType;
	}

	public void setCrmAccountDeviceIdType(int crmAccountDeviceIdType) {
		this.crmAccountDeviceIdType = crmAccountDeviceIdType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
