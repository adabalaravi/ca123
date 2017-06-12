package com.accenture.sdp.csmac.managers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.accenture.sdp.csmac.beans.device.DeviceBean;
import com.accenture.sdp.csmac.beans.device.DeviceRulesBean;
import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.constants.PathConstants;
import com.accenture.sdp.csmac.controllers.MenuController;

@ManagedBean(name = ApplicationConstants.DEVICE_MANAGER)
@SessionScoped
public class DeviceManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static LoggerWrapper log = new LoggerWrapper(DeviceManager.class);
	
	private List<DeviceBean> devices;
	private List<DeviceRulesBean> deviceRules;
	private List<DeviceBean> filteredDevices;
	private List<DeviceBean> selectedDevices;
	private String brand;
	private DeviceBean selectedDevice;
	private SelectItem[] booleanList;
	
	public DeviceManager(){
		booleanList = createBooleanList();
	}
	
	private SelectItem [] createBooleanList(){
		SelectItem[] items = new SelectItem[3];
		items[0] = new SelectItem("", "");
		items[1] = new SelectItem(true, "true");
		items[2] = new SelectItem(false, "false");
		return items;
	}
	

	public List<DeviceRulesBean> getDeviceRules() {
		deviceRules =  new Vector<DeviceRulesBean>();
		DeviceRulesBean rule1 = new DeviceRulesBean();
		rule1.setMaxNumber(3);
		rule1.setType("PCTV");
		rule1.setAlreadyRegistered(1);
		DeviceRulesBean rule2 = new DeviceRulesBean();
		rule2.setMaxNumber(5);
		rule2.setType("iPad");
		rule2.setAlreadyRegistered(0);
		DeviceRulesBean rule3 = new DeviceRulesBean();
		rule3.setMaxNumber(2);
		rule3.setType("OTTSTB");
		rule3.setAlreadyRegistered(0);
		DeviceRulesBean rule4 = new DeviceRulesBean();
		rule4.setMaxNumber(1);
		rule4.setType("ANDROID");
		rule4.setAlreadyRegistered(0);
		DeviceRulesBean rule5 = new DeviceRulesBean();
		rule5.setMaxNumber(10);
		rule5.setType("CONNECTED");
		rule5.setAlreadyRegistered(8);
		DeviceRulesBean rule6 = new DeviceRulesBean();
		rule6.setMaxNumber(0);
		rule6.setType("XBOX");
		rule6.setAlreadyRegistered(0);
		
		deviceRules.add(rule1);
		deviceRules.add(rule2);
		deviceRules.add(rule3);
		deviceRules.add(rule4);
		deviceRules.add(rule5);
		deviceRules.add(rule6);
		return deviceRules;
	}

	public void setDeviceRules(Vector<DeviceRulesBean> deviceRules) {
		this.deviceRules = deviceRules;
	}

	public void associate(ActionEvent event){
		System.out.println("DENTRO");
		DeviceBean selectedDevice = (DeviceBean) (event.getComponent().getAttributes().get("device"));
		selectedDevice.setAssociated(true);
	}
	
	public void disassociate(ActionEvent event){
		DeviceBean selectedDevice = (DeviceBean) (event.getComponent().getAttributes().get("device"));
		selectedDevice.setAssociated(false);
	}

	public List<DeviceBean> getDevices() {
		devices  = new Vector<DeviceBean>();
		for(int i = 0 ; i < 5 ; i++){
			DeviceBean device = new DeviceBean();
			device.setAlias("alias"+i);
			device.setBlackList(i%2==0);
			device.setBrand("brand"+i);
			device.setId("id"+i);
			device.setLastUseDate(new Date());
			device.setRecordingDate(new Date());
			device.setModel("model"+i);
			device.setPaired(i%3==0);
			device.setStatus(i%4==0?"Active":"Inactive");
			device.setType("type"+i);
			device.setWhiteList(i==4);
			device.setAssociated(i%3==0);
			devices.add(device);
		}
		return devices;
	}
	
	public void goToDetailPage(ActionEvent event) {
		System.out.println("in gotToDetailPage");
		selectedDevice = (DeviceBean) event.getComponent().getAttributes().get("device");
		try {
			System.out.println("device = " + selectedDevice);
			new MenuController().redirectbyParam(PathConstants.PARTY_DEVICE_DETAILS_VIEW);
		} catch (Exception e) {
			log.logError(e);
		}
	}

	public void setDevices(List<DeviceBean> devices) {
		this.devices = devices;
	}
	
	public void updateDeviceDatas(ActionEvent event){
		this.editable = true;
	}
	
	public void discard(ActionEvent event){
		this.editable = false;
	}
	
	public void edit(ActionEvent event){
		this.editable = false;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<DeviceBean> getSelectedDevices() {
		return selectedDevices;
	}

	public void setSelectedDevices(List<DeviceBean> selectedDevices) {
		this.selectedDevices = selectedDevices;
	}

	public List<DeviceBean> getFilteredDevices() {
		return filteredDevices;
	}

	public void setFilteredDevices(List<DeviceBean> filteredDevices) {
		this.filteredDevices = filteredDevices;
	}

	private boolean editable;
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public DeviceBean getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(DeviceBean selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
	
	public SelectItem[] getBooleanList() {
		return booleanList;
	}

	public void setBooleanList(SelectItem[] booleanList) {
		this.booleanList = booleanList;
	}
}
